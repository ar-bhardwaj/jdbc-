package com.payroll;

import com.payroll.dtos.EmployeePayrollDto;
import com.payroll.dtos.PayrollAnalysisDto;
import com.payroll.entities.Employee;
import com.payroll.exceptions.PayrollServiceException;
import com.payroll.services.PayrollService;

import java.sql.Date;
import java.util.List;

public class Main {
//    UC 1
    public static void main(String[] args) {
        try{
//            // UC 2
//            List<EmployeePayrollDto> employeePayroll =  PayrollService.getEmployeePayrolls();
//            for (EmployeePayrollDto employeePayrollDto : employeePayroll) {
//                System.out.println(employeePayrollDto);
//            }
//
//            // UC 3-4
//            PayrollService.updateEmployeeSalary("Bob Smith", 5000.0);
//
//            // UC 5
//            List<EmployeePayrollDto> employees = PayrollService.getEmployeesByDateRange(Date.valueOf("2021-01-01"), Date.valueOf("2023-01-01"));
//            for (EmployeePayrollDto employee : employees) {
//                System.out.println(employee);
//            }
//
//            // UC 6
//            List<PayrollAnalysisDto> payrollAnalysis = PayrollService.getPayrollAnalysisByGender();
//            for (PayrollAnalysisDto payrollAnalysisDto : payrollAnalysis) {
//                System.out.println(payrollAnalysisDto);
//            }
//
//            // UC 7
//            PayrollService.addEmployee(new Employee(5, "Anmol", "M", Date.valueOf("2025-01-01"), 1));
//
//            // UC 8-10
//            PayrollService.addEmployeeWithPayroll(new Employee(6, "Rishav", "M", Date.valueOf("2025-10-31"), 2), 8920.0);

            // UC 11-12
            PayrollService.removeEmployee(3);
        }
        catch (PayrollServiceException e){
            System.err.println(e.getMessage());
        }
    }
}