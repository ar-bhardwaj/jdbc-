package com.payroll.dtos;

import com.payroll.entities.Contact;
import com.payroll.entities.Department;
import com.payroll.entities.Employee;
import com.payroll.entities.Payroll;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeePayrollDto {
    private Employee employee;
    private Payroll payroll;
    private Contact contact;
    private Department department;

    public EmployeePayrollDto(Employee employee, Payroll payroll, Contact contact, Department department) {
        this.employee = employee;
        this.payroll = payroll;
        this.contact = contact;
        this.department = department;
    }

    @Override
    public String toString() {
        return "EmployeePayrollDto [employee=" + employee + ", payroll=" + payroll + ", contact=" + contact + ", department=" + department + "]";
    }
}
