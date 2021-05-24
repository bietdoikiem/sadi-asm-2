package com.rmit.demo.repository;

import com.rmit.demo.model.DeliveryDetail;
import com.rmit.demo.model.DeliveryNote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeliveryDetailRepository extends JpaRepository<DeliveryDetail, Integer> {
    List<DeliveryDetail> findDeliveryDetailsByDeliveryNote(DeliveryNote deliveryNote);
}
