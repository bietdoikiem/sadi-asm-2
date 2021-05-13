package com.rmit.demo.model;

public class ReceiveDetail {
    private Product product;
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
