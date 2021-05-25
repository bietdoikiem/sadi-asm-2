package com.rmit.demo.model;



import javax.persistence.*;

@Entity
@Table(name="delivery_detail")
public class DeliveryDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="delivery_note_id")
    private DeliveryNote deliveryNote;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="product_id")
    private Product product;

    @Column
    private int quantity;

    public DeliveryDetail() {};

    public DeliveryDetail(int id, DeliveryNote deliveryNote, Product product, int quantity) {
        super();
        this.id = id;
        this.deliveryNote = deliveryNote;
        this.product = product;
        this.quantity = quantity;
    }
    public DeliveryDetail(Product product, int quantity) {
        super();
        this.product = product;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
