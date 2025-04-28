package com.payroll.entities;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class Contact {
    private int contact_id;
    private String phone_number;
    private String email;
    private String address;
    private int employee_id;

    public Contact(int contact_id, String phone_number, String email, String address, int employee_id) {
        this.contact_id = contact_id;
        this.phone_number = phone_number;
        this.email = email;
        this.address = address;
        this.employee_id = employee_id;
    }

    @Override
    public String toString() {
        return " [Contact ID: " + contact_id + "] [Phone Number: " + phone_number + "]";
    }
}
