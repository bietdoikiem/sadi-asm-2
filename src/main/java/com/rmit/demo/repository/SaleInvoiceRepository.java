package com.rmit.demo.repository;

import com.rmit.demo.model.Customer;
import com.rmit.demo.model.SaleInvoice;
import com.rmit.demo.model.Staff;
import com.rmit.demo.reponses.NoteStatsResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
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
    public static final String QUERY_STRING = "SELECT COALESCE(delivery_note.id, receiving_note.id) product_id, COALESCE(delivery_note.name, receiving_note.name) product_name, " +
            "COALESCE(receiving_note.received, 0) received, " +
            "COALESCE(delivery_note.delivery, 0) delivery, " +
            "COALESCE(receiving_note.received, 0) - COALESCE(delivery_note.delivery, 0) balance " +
            "FROM ( " +
            "SELECT product.id id, product.name, SUM(delivery_detail.quantity) delivery " +
            "FROM delivery_note, delivery_detail, product " +
            "WHERE delivery_note.date BETWEEN :startDate AND :endDate " +
            "AND delivery_note.id = delivery_detail.delivery_note_id " +
            "AND delivery_detail.product_id = product.id " +
            "GROUP BY product.id " +
            ") " + "delivery_note FULL OUTER JOIN " + "( " +
            "SELECT product.id id, product.name, SUM(receive_detail.quantity) received " +
            "FROM receiving_note, receive_detail, product " +
            "WHERE receiving_note.date BETWEEN :startDate AND :endDate " +
            "AND receiving_note.id = receive_detail.receiving_note_id " +
            "AND receive_detail.product_id = product.id " +
            "GROUP BY product.id "
            + ") " + "receiving_note " +
            "ON delivery_note.id = receiving_note.id";
    @Query(nativeQuery = true, value = QUERY_STRING)
    List<NoteStatsResponse> findProductsReceivedAndDeliveredBetween(@Param("startDate") Date startDate, @Param("endDate") Date endDate);
}
