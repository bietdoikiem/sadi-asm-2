package com.rmit.demo.model;

import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;

@Entity
@Table(name="person")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE) //Least normalisation strategy
public abstract class Person {

    @Id
    @Column
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    @Column
    private String name;
    @Column
    private String address;
    @Column
    private String phone;
    @Column
    private String email;

    public Person() {}

    public Person(int id, String name, String address, String phone, String email) {
        super();
        this.id = id;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
