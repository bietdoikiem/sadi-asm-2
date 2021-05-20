package com.rmit.demo.model;


import com.rmit.demo.composite.SaleDetailId;

import javax.persistence.*;

@Entity
@Table(name = "sale_detail")
public class SaleDetail {

    @EmbeddedId
    private SaleDetailId id;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "sale_invoice_id")
    @MapsId("saleInvoiceId")
    private SaleInvoice saleInvoice;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    @MapsId("productId")
    private Product product;

    @Column
    private int quantity;

    @Column
    private int price;

    public SaleDetail() {
    }

    ;

    public SaleDetail(SaleDetailId id, SaleInvoice saleInvoice, Product product, int quantity, int price) {
        this.id = id;
        this.saleInvoice = saleInvoice;
        this.product = product;
        this.quantity = quantity;
        this.price = price;
    }

    public SaleDetailId getId() {
        return id;
    }

    public void setId(SaleDetailId id) {
        this.id = id;
    }

    public SaleInvoice getSaleInvoice() {
        return saleInvoice;
    }

    public void setSaleInvoice(SaleInvoice saleInvoice) {
        this.saleInvoice = saleInvoice;
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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }


}
