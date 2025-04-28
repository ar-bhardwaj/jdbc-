package com.payroll.entities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Payroll {
    private int payroll_id;
    private Double basic_pay;
    private Double deductions;
    private Double taxable_pay;
    private Double income_tax;
    private Double net_pay;
    private Double salary;
    private int employee_id;

    public Payroll(int payroll_id, Double basic_pay, Double deductions, Double taxable_pay, Double income_tax, Double net_pay, Double salary, int employee_id) {
        this.payroll_id = payroll_id;
        this.basic_pay = basic_pay;
        this.deductions = deductions;
        this.taxable_pay = taxable_pay;
        this.income_tax = income_tax;
        this.net_pay = net_pay;
        this.salary = salary;
        this.employee_id = employee_id;
    }

    @Override
    public String toString() {
        return "[ Payroll ID: " + payroll_id + " Salary: " + salary + "]";
    }
}
