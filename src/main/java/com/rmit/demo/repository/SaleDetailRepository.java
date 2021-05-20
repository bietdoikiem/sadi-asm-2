package com.rmit.demo.repository;

import com.rmit.demo.model.Product;
import com.rmit.demo.model.SaleDetail;
import com.rmit.demo.model.SaleInvoice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SaleDetailRepository extends JpaRepository<SaleDetail, Integer> {
    List<SaleDetail> findSaleDetailsBySaleInvoice(SaleInvoice saleInvoice);
}
