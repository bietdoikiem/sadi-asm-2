package com.rmit.demo.repository;

import com.rmit.demo.model.Customer;
import com.rmit.demo.model.SaleInvoice;
import com.rmit.demo.model.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Repository
@Transactional
public interface SaleInvoiceRepository extends CrudRepository<SaleInvoice, Integer>, JpaRepository<SaleInvoice, Integer>, SaleInvoiceRepositoryCustom {
    List<SaleInvoice> findAllByDateBetween(Date startDate, Date endDate);
    List<SaleInvoice> findSaleInvoicesByCustomerAndDateBetween(Customer customer, Date startDate, Date endDate);
    List<SaleInvoice> findSaleInvoicesByStaffAndDateBetween(Staff staff, Date startDate, Date endDate);
}
