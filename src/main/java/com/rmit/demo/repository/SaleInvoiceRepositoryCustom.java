package com.rmit.demo.repository;

import com.rmit.demo.model.SaleInvoice;

public interface SaleInvoiceRepositoryCustom {
    SaleInvoice saveAndReset(SaleInvoice saleInvoice);
}
