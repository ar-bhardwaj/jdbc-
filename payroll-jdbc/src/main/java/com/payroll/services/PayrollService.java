package com.payroll.services;

import com.payroll.dtos.EmployeePayrollDto;
import com.payroll.dtos.PayrollAnalysisDto;
import com.payroll.entities.Employee;
import com.payroll.entities.Payroll;
import com.payroll.exceptions.PayrollServiceException;
import com.payroll.mappings.ToEmployeePayrollDto;
import com.payroll.mappings.ToPayrollAnalysisDto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PayrollService {
    public static List<EmployeePayrollDto> getEmployeePayrolls() throws PayrollServiceException {
        List<EmployeePayrollDto> employeePayrolls = new ArrayList<>();

        String query = """
            SELECT *
            FROM employee e
            JOIN department d ON e.dept_id = d.dept_id
            LEFT JOIN contact c ON e.id = c.employee_id
            LEFT JOIN payroll p ON e.id = p.employee_id
        """;

        try (Connection conn = DbService.getInstance().getConnection()){
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                employeePayrolls.add(ToEmployeePayrollDto.map(rs));
            }
        }
        catch (Exception e) {
            throw new PayrollServiceException(e.getMessage());
        }

        return employeePayrolls;
    }

    public static EmployeePayrollDto getEmployeePayroll(int employee_id) throws PayrollServiceException {
        EmployeePayrollDto employeePayroll = null;

        String query = """
            SELECT *
            FROM employee e
            JOIN department d ON e.dept_id = d.dept_id
            LEFT JOIN contact c ON e.id = c.employee_id
            LEFT JOIN payroll p ON e.id = p.employee_id
            WHERE e.id = ?
        """;

        try (Connection conn = DbService.getInstance().getConnection()){
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, employee_id);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                employeePayroll = ToEmployeePayrollDto.map(rs);
                break;
            }
        }
        catch (Exception e) {
            throw new PayrollServiceException(e.getMessage());
        }

        return employeePayroll;
    }


    public static void updateEmployeeSalary(String name, double salary) throws PayrollServiceException {
        String query = "UPDATE payroll SET salary=? WHERE payroll_id=(SELECT id FROM employee WHERE name=?)";

        try (Connection conn = DbService.getInstance().getConnection()){
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setDouble(1, salary);
            stmt.setString(2, name);

            stmt.executeUpdate();
            System.out.println("Employee Salary Updated");
        }
        catch (Exception e) {
            throw new PayrollServiceException(e.getMessage());
        }
    }


    public static List<EmployeePayrollDto> getEmployeesByDateRange(Date start, Date end) throws PayrollServiceException {
        List<EmployeePayrollDto> employeePayrolls = new ArrayList<>();

        String query = """
        SELECT * FROM employee e
        JOIN department d ON e.dept_id = d.dept_id
        LEFT JOIN contact c ON e.id = c.employee_id
        LEFT JOIN payroll p ON e.id = p.employee_id
        WHERE e.start_date BETWEEN ? AND ?
    """;

        try (Connection conn = DbService.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setDate(1, start);
            stmt.setDate(2, end);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    employeePayrolls.add(ToEmployeePayrollDto.map(rs));
                }
            }

        } catch (Exception e) {
            throw new PayrollServiceException("Error retrieving employees by date range: " + e.getMessage());
        }

        return employeePayrolls;
    }


    public static List<PayrollAnalysisDto> getPayrollAnalysisByGender() throws PayrollServiceException {
        List<PayrollAnalysisDto> analysisList = new ArrayList<>();

        String query = """
        SELECT gender,
               SUM(salary) AS total_salary,
               AVG(salary) AS average_salary,
               MIN(salary) AS min_salary,
               MAX(salary) AS max_salary,
               COUNT(*) AS employee_count
        FROM employee e
        JOIN payroll p ON e.id = p.employee_id
        GROUP BY gender
    """;

        try (Connection conn = DbService.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                analysisList.add(ToPayrollAnalysisDto.map(rs));
            }

        } catch (SQLException e) {
            throw new PayrollServiceException("Error fetching payroll analysis: " + e.getMessage());
        }

        return analysisList;
    }

    public static void addEmployee(Employee employee) throws PayrollServiceException {
        String query = "INSERT INTO employee VALUES(?, ?, ?, ?, ?)";

        try (Connection conn = DbService.getInstance().getConnection();
        PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, employee.getId());
            stmt.setString(2, employee.getName());
            stmt.setString(3, employee.getGender());
            stmt.setDate(4, employee.getStart_date());
            stmt.setInt(5, employee.getDept_id());

            stmt.executeUpdate();

            System.out.println("Employee Added");
        }
        catch (Exception e) {
            throw new PayrollServiceException(e.getMessage());
        }
    }

    public static void addEmployeeWithPayroll(Employee employee, double salary) throws PayrollServiceException {
        String insertEmployee = "INSERT INTO employee (name, gender, start_date, dept_id) VALUES (?, ?, ?, ?)";
        String insertPayroll = "INSERT INTO payroll (basic_pay, salary, deductions, taxable_pay, income_tax, net_pay, employee_id) VALUES (?, ?, ?, ?, ?, ?, ?)";

        Connection conn = null;
        try{
            conn = DbService.getInstance().getConnection();
            conn.setAutoCommit(false);
            PreparedStatement empStmt = conn.prepareStatement(insertEmployee, Statement.RETURN_GENERATED_KEYS);
            empStmt.setString(1, employee.getName());
            empStmt.setString(2, employee.getGender());
            empStmt.setDate(3, employee.getStart_date());
            empStmt.setInt(4, employee.getDept_id());
            empStmt.executeUpdate();


            ResultSet rs = empStmt.getGeneratedKeys();
            if (!rs.next()) throw new PayrollServiceException("Failed to retrieve employee ID.");
            int employeeId = rs.getInt(1);

            double basicPay = salary * 0.5;
            double deductions = salary * 0.20;
            double taxablePay = salary - deductions;
            double incomeTax = taxablePay * 0.10;
            double netPay = salary - incomeTax;

            PreparedStatement payStmt = conn.prepareStatement(insertPayroll);
            payStmt.setDouble(1, basicPay);
            payStmt.setDouble(2, salary);
            payStmt.setDouble(3, deductions);
            payStmt.setDouble(4, taxablePay);
            payStmt.setDouble(5, incomeTax);
            payStmt.setDouble(6, netPay);
            payStmt.setInt(7, employeeId);
            payStmt.executeUpdate();

            conn.commit();
            System.out.println("Employee and payroll details added successfully.");
        }
        catch (Exception e) {
            try {
                if (conn != null) conn.rollback();
            } catch (SQLException se) {
                System.err.println("Rollback failed: " + se.getMessage());
            }
            throw new PayrollServiceException("Failed to add employee and payroll: " + e.getMessage());
        }
        finally {
            try {
                if (conn != null) conn.setAutoCommit(true);
            } catch (SQLException se) {
                System.err.println("Auto-commit reset failed: " + se.getMessage());
            }
        }
    }

    public static void removeEmployee(int employeeId) throws PayrollServiceException {
        String query = "UPDATE employee SET is_active = FALSE WHERE id = ?";
        try (Connection conn = DbService.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, employeeId);
            stmt.executeUpdate();
        } catch (Exception e) {
            throw new PayrollServiceException(e.getMessage());
        }
    }

}
