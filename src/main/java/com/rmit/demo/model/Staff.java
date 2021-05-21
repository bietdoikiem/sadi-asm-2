package com.rmit.demo.model;

import com.fasterxml.jackson.annotation.*;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "staff")
public class Staff extends Person {

    @OneToMany(fetch = FetchType.LAZY)
    @JsonIgnore
    private List<DeliveryNote> deliveryNoteList;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<SaleInvoice> saleInvoiceList;


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

    public List<SaleInvoice> getSaleInvoiceList() {
        return saleInvoiceList;
    }

    public void setSaleInvoiceList(List<SaleInvoice> saleInvoiceList) {
        this.saleInvoiceList = saleInvoiceList;
    }
}
