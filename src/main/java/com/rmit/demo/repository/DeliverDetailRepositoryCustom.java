package com.rmit.demo.repository;

import com.rmit.demo.model.DeliveryDetail;

public interface DeliverDetailRepositoryCustom {
    DeliveryDetail saveAndReset(DeliveryDetail deliveryDetail);
}
