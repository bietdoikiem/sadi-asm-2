package com.rmit.demo.composite;

import com.rmit.demo.model.SaleInvoice;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class SaleDetailId implements Serializable {

    @Column(name = "sale_invoice_id")
    private int saleInvoiceId;

    @Column(name = "product_id")
    private int productId;

}
