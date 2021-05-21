package com.rmit.demo.repository.impl;

import com.rmit.demo.model.SaleInvoice;
import com.rmit.demo.repository.SaleInvoiceRepository;
import com.rmit.demo.repository.SaleInvoiceRepositoryCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Service
public class SaleInvoiceRepositoryImpl implements SaleInvoiceRepositoryCustom {
    @PersistenceContext
    private EntityManager em;

    @Autowired
    private SaleInvoiceRepository saleInvoiceRepository;

    public SaleInvoice saveAndReset(SaleInvoice saleInvoice) {
        // Save
        SaleInvoice sInvoice = saleInvoiceRepository.saveAndFlush(saleInvoice);
        // Reset
        em.clear();
        // Return
        return saleInvoiceRepository.findById(sInvoice.getId()).orElse(null);
    }


}
