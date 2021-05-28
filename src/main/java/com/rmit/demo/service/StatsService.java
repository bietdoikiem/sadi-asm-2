package com.rmit.demo.service;

import com.rmit.demo.model.*;
import com.rmit.demo.reponses.NoteStatsResponse;
import com.rmit.demo.repository.SaleDetailRepository;
import com.rmit.demo.repository.SaleInvoiceRepository;
import com.rmit.demo.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.ParseException;
import java.util.*;

@Service
@Transactional
public class StatsService {

    @Autowired
    private SaleInvoiceService saleInvoiceService;

    @Autowired
    private SaleInvoiceRepository saleInvoiceRepository;

    // RETRIEVE REVENUE by period
    public double getRevenueByPeriod(Date startDate, Date endDate) {
        List<SaleInvoice> saleInvoiceList = saleInvoiceService.filterByPeriod(startDate, endDate);
        double totalRevenue = 0;
        for (SaleInvoice saleInvoice: saleInvoiceList) {
            // Total value of invoice = all product quantities * price
            // Iterate through list of Sale details
            for (SaleDetail saleDetail: saleInvoice.getSaleDetailList()) {
                totalRevenue += saleDetail.getProduct().getPrice() * saleDetail.getQuantity();
            }
        }
        return totalRevenue;
    }

    // RETRIEVE REVENUE By Customer in a period
    public double getRevenueByCustomerAndPeriod(int id, Date startDate, Date endDate) {
        List<SaleInvoice> saleInvoiceList = saleInvoiceService.getAllSaleInvoicesByCustomerAndPeriod(id, startDate, endDate);
        double totalRevenue = 0;
        for (SaleInvoice saleInvoice: saleInvoiceList) {
            // Total value of invoice = all product quantities * price
            // Iterate through list of Sale details
            for (SaleDetail saleDetail: saleInvoice.getSaleDetailList()) {
                totalRevenue += saleDetail.getProduct().getPrice() * saleDetail.getQuantity();
            }
        }
        return totalRevenue;
    }

    // RETRIEVE REVENUE By Staff in a period
    public double getRevenueByStaffAndPeriod(int id, Date startDate, Date endDate) {
        List<SaleInvoice> saleInvoiceList = saleInvoiceService.getAllSaleInvoicesByStaffAndPeriod(id, startDate, endDate);
        double totalRevenue = 0;
        for (SaleInvoice saleInvoice: saleInvoiceList) {
            // Total value of invoice = all product quantities * price
            // Iterate through list of Sale details
            for (SaleDetail saleDetail: saleInvoice.getSaleDetailList()) {
                totalRevenue += saleDetail.getProduct().getPrice() * saleDetail.getQuantity();
            }
        }
        return totalRevenue;
    }

    // List of Products filtered by DeliveryNote and ReceivingNote during a period of time
    public List<NoteStatsResponse> getProductsByDeliverNoteAndReceivingNoteAndPeriod(Date startDate, Date endDate) {
        List<NoteStatsResponse> statsResponseList = saleInvoiceRepository.findProductsReceivedAndDeliveredBetween(startDate, endDate);
        if (statsResponseList.size() > 0) {
            return statsResponseList;
        }
        return new ArrayList<>();
    }

}
