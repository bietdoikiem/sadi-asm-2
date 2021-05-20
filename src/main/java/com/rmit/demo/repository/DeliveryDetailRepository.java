package com.rmit.demo.repository;

import com.rmit.demo.composite.DeliveryDetailId;
import com.rmit.demo.model.DeliveryDetail;
import com.rmit.demo.model.DeliveryNote;
import com.rmit.demo.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface DeliveryDetailRepository extends JpaRepository<DeliveryDetail, DeliveryDetailId>, DeliverDetailRepositoryCustom {
    DeliveryDetail saveAndReset(DeliveryDetail deliveryDetail);
    DeliveryDetail findDeliveryDetailByDeliveryNoteAndProduct(DeliveryNote deliveryNote, Product product);
}
