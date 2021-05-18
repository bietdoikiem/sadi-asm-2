package com.rmit.demo.repository;

import com.rmit.demo.model.SaleDetail;

public interface SaleDetailRepositoryCustom {
    SaleDetail saveAndReset(SaleDetail saleDetail);
}
