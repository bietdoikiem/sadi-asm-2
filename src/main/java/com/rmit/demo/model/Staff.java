package com.rmit.demo.model;

import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;

@Entity
@Table(name="staff")
public class Staff extends Person {
    public Staff() {}

    public Staff(int id, String name, String address, String phone, String email) {
        super(id, name, address, phone, email);
    }
}
