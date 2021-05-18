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
    @JoinColumn(name = "receiving_note_id", referencedColumnName = "id")
    private ReceivingNote receivingNote;

    @Column
    private int quantity;

    public ReceiveDetail() {}

    public ReceiveDetail(Product product, int quantity) {
        this.product = product;
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

}
