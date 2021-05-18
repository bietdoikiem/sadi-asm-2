package com.rmit.demo.repository;

import com.rmit.demo.model.DeliveryDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface DeliveryDetailRepository extends CrudRepository<DeliveryDetail, Integer>, JpaRepository<DeliveryDetail, Integer>, DeliverDetailRepositoryCustom {
    DeliveryDetail saveAndReset(DeliveryDetail deliveryDetail);
}
