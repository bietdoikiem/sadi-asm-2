package com.rmit.demo.model;

import org.springframework.context.annotation.Bean;

import javax.persistence.*;

@Entity
@Table(name = "order_detail")
public class OrderDetail {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    private Order order;

    @Column
    private int quantity;

    @Column
    private double price;

    public OrderDetail() {
    }

    public OrderDetail(Order order, int quantity, double price, Product product) {
        this.order = order;
        this.quantity = quantity;
        this.price = price;
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void setAll(OrderDetail orderDetail) {
        this.id = orderDetail.getId();
        this.product = orderDetail.getProduct();
        this.order = orderDetail.getOrder();
        this.quantity = orderDetail.getQuantity();
        this.price = orderDetail.getPrice();
    }


}
