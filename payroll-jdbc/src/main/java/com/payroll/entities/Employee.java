package com.payroll.entities;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;


@Getter
@Setter
public class Employee {
    private int id;
    private String name;
    private String gender;
    private Date start_date;
    private int dept_id;

    public Employee(int id, String name, String gender, Date start_date, int dept_id) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.start_date = start_date;
        this.dept_id = dept_id;
    }

    @Override
    public String toString() {
        return "[ Employee ID: " + id + " Employee Name: " + name + "]";
    }
}
