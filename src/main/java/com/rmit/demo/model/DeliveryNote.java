package com.rmit.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "delivery_note")
public class DeliveryNote extends InventoryNote {

    @OneToMany(mappedBy = "deliveryNote", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<DeliveryDetail> deliveryDetailList;

    public DeliveryNote() {
    }

    public DeliveryNote(int id, Date date, Staff staff) {
        super(id, date, staff);
    }

    public List<DeliveryDetail> getDeliveryDetailList() {
        return deliveryDetailList;
    }

    public void setDeliveryDetailList(List<DeliveryDetail> deliveryDetailList) {
        this.deliveryDetailList = deliveryDetailList;
    }

}
