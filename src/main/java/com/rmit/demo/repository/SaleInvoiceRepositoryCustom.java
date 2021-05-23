package com.rmit.demo.repository;

import com.rmit.demo.model.DeliveryNote;
import com.rmit.demo.model.SaleInvoice;
import com.rmit.demo.reponses.NoteStatsResponse;

import java.util.Date;
import java.util.List;

public interface SaleInvoiceRepositoryCustom {
    SaleInvoice saveAndReset(SaleInvoice saleInvoice);
}
