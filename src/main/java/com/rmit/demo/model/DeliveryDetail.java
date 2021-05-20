package com.rmit.demo.model;


import com.rmit.demo.composite.DeliveryDetailId;

import javax.persistence.*;

@Entity
@Table(name="delivery_detail")
public class DeliveryDetail {

    @EmbeddedId
    private DeliveryDetailId id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="delivery_note_id")
    @MapsId("deliveryNoteId")
    private DeliveryNote deliveryNote;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="product_id")
    @MapsId("productId")
    private Product product;

    @Column
    private int quantity;

    public DeliveryDetail() {};

    public DeliveryDetail(DeliveryDetailId id, DeliveryNote deliveryNote, Product product, int quantity) {
        this.id = id;
        this.deliveryNote = deliveryNote;
        this.product = product;
        this.quantity = quantity;
    }

    public DeliveryDetailId getId() {
        return id;
    }

    public void setId(DeliveryDetailId id) {
        this.id = id;
    }

    public DeliveryNote getDeliveryNote() {
        return deliveryNote;
    }

    public void setDeliveryNote(DeliveryNote deliveryNote) {
        this.deliveryNote = deliveryNote;
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
