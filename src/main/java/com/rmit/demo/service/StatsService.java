package com.rmit.demo.service;

import com.rmit.demo.model.SaleDetail;
import com.rmit.demo.model.SaleInvoice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class StatsService {

    @Autowired
    private SaleInvoiceService saleInvoiceService;


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
}
