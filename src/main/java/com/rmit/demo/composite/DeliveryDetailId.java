package com.rmit.demo.composite;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class DeliveryDetailId implements Serializable {

    @Column(name = "delivery_note_id")
    private int deliveryNoteId;

    @Column(name = "product_id")
    private int productId;

    // Implements equal and hashCode
}
