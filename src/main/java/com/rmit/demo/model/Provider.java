package com.rmit.demo.model;

import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;

@Entity
@Table(name="provider")
public abstract class Provider extends Person {
    @Column
    private String fax;
    @Column
    private String contactPerson;

    public Provider() {}

    public Provider(int id, String name, String address, String phone, String email, String fax, String contactPerson) {
        super(id, name, address, phone, email);
        this.fax = fax;
        this.contactPerson = contactPerson;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }
}
