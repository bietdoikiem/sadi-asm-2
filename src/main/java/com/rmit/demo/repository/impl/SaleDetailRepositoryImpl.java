package com.rmit.demo.repository.impl;

import com.rmit.demo.model.SaleDetail;
import com.rmit.demo.repository.SaleDetailRepository;
import com.rmit.demo.repository.SaleDetailRepositoryCustom;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class SaleDetailRepositoryImpl implements SaleDetailRepositoryCustom {
    @PersistenceContext
    private EntityManager em;

    @Autowired
    private SaleDetailRepository saleDetailRepository;

    public SaleDetail saveAndReset(SaleDetail saleDetail) {
        // SAVE
        SaleDetail saved = saleDetailRepository.saveAndFlush(saleDetail);
        // RESET
        em.clear();
        // RETURN
        return saleDetailRepository.findById(saved.getId()).orElse(null);
    }

}
