package com.rmit.demo.model;

import javax.persistence.*;

@Entity
@Table(name = "receive_detail")
public class ReceiveDetail {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "receiving_note_id", referencedColumnName = "id", nullable = false)
    private ReceivingNote receivingNote;

    public ReceivingNote getReceivingNote() {
        return receivingNote;
    }

    public void setReceivingNote(ReceivingNote receivingNote) {
        this.receivingNote = receivingNote;
    }

    @Column
    private int quantity;

    public ReceiveDetail() {}

    public ReceiveDetail(int id, Product product, int quantity, ReceivingNote receivingNote) {
        this.id = id;
        this.product = product;
        this.quantity = quantity;
        this.receivingNote = receivingNote;
    }

    public ReceiveDetail(int id, int quantity) {
        this.id = id;
        this.quantity = quantity;
    }


    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setAll (ReceiveDetail receiveDetail) {
        this.id = receiveDetail.getId();
        this.product = receiveDetail.getProduct();
        this.quantity = receiveDetail.getQuantity();
    }

}
