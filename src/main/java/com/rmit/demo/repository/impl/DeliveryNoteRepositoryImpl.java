package com.rmit.demo.repository.impl;

import com.rmit.demo.model.DeliveryNote;
import com.rmit.demo.repository.DeliveryNoteRepository;
import com.rmit.demo.repository.DeliveryNoteRepositoryCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Service
public class DeliveryNoteRepositoryImpl implements DeliveryNoteRepositoryCustom {
    @PersistenceContext
    private EntityManager em;

    @Autowired
    private DeliveryNoteRepository deliveryNoteRepository;

    @Transactional
    public DeliveryNote saveAndReset(DeliveryNote deliveryNote) {
        // Save
        DeliveryNote updatedDeliveryNote = deliveryNoteRepository.saveAndFlush(deliveryNote);
        // Reset
        em.clear();
        // Return new one
        return deliveryNoteRepository.findById(updatedDeliveryNote.getId()).orElse(null);
    }
}
