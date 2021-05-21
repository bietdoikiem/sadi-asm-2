package com.rmit.demo.repository;

import com.rmit.demo.model.DeliveryNote;

public interface DeliveryNoteRepositoryCustom {
    DeliveryNote saveAndReset(DeliveryNote deliveryNote);
}
