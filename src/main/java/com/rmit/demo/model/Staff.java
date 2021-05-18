package com.rmit.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "staff")
public class Staff extends Person {

    @OneToMany(fetch = FetchType.LAZY)
    @JsonIgnore
    private List<DeliveryNote> deliveryNoteList;

    public Staff() {
    }

    public Staff(int id, String name, String address, String phone, String email) {
        super(id, name, address, phone, email);
    }

    public List<DeliveryNote> getDeliveryNoteList() {
        return deliveryNoteList;
    }

    public void setDeliveryNoteList(List<DeliveryNote> deliveryNoteList) {
        this.deliveryNoteList = deliveryNoteList;
    }
}
