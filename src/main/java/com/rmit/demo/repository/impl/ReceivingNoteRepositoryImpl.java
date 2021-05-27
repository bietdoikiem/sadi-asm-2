package com.rmit.demo.repository.impl;

import com.rmit.demo.model.ReceivingNote;
import com.rmit.demo.repository.ReceivingNoteRepository;
import com.rmit.demo.repository.ReceivingNoteRepositoryCustom;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class ReceivingNoteRepositoryImpl implements ReceivingNoteRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private ReceivingNoteRepository receivingNoteRepository;

    @Override
    public ReceivingNote saveAndReset(ReceivingNote receivingNote) {
        // SAVE
        ReceivingNote saved = receivingNoteRepository.saveAndFlush(receivingNote);
        // RESET
        em.clear();
        // RETURN
        return receivingNoteRepository.findById(saved.getId()).orElse(null);
    }
}
