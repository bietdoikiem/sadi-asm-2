package com.rmit.demo.repository.impl;


import com.rmit.demo.model.DeliveryDetail;
import com.rmit.demo.repository.DeliverDetailRepositoryCustom;
import com.rmit.demo.repository.DeliveryDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Service
public class DeliveryDetailRepositoryImpl implements DeliverDetailRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private DeliveryDetailRepository deliveryDetailRepository;

    @Transactional
    public DeliveryDetail saveAndReset(DeliveryDetail deliveryDetail){
        // Save
        DeliveryDetail saveDeliveryDetail = deliveryDetailRepository.saveAndFlush(deliveryDetail);
        // Reset
        em.clear();
        // Fetch new one
        return deliveryDetailRepository.findById(saveDeliveryDetail.getId()).orElse(null);
    }

}
