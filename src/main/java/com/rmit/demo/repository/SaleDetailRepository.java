package com.rmit.demo.repository;

import com.rmit.demo.model.Product;
import com.rmit.demo.model.ReceiveDetail;
import com.rmit.demo.model.SaleDetail;
import com.rmit.demo.model.SaleInvoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SaleDetailRepository extends JpaRepository<SaleDetail, Integer> {
    List<SaleDetail> findSaleDetailsBySaleInvoice(SaleInvoice saleInvoice);
}
