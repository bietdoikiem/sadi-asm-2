package com.rmit.demo.controller;

import com.rmit.demo.model.SaleDetail;
import com.rmit.demo.model.SaleInvoice;
import com.rmit.demo.repository.SaleInvoiceRepository;
import com.rmit.demo.service.SaleInvoiceService;
import com.rmit.demo.utils.DateUtils;
import com.rmit.demo.utils.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping(path = "/sale-invoices")
public class SaleInvoiceController implements CrudController<SaleInvoice> {

    private SaleInvoiceService saleInvoiceService;

    @Autowired
    public SaleInvoiceController(SaleInvoiceService saleInvoiceService) {
        this.saleInvoiceService = saleInvoiceService;
    }

    @Override
    public ResponseEntity<Object> getAll() {
        List<SaleInvoice> allSaleInvoices = saleInvoiceService.getAll();
        return ResponseHandler.generateResponse(HttpStatus.OK, true, "/sale-invoices", "All SaleInvoice fetched successfully", allSaleInvoices);
    }

    @Override
    public ResponseEntity<Object> getAll(int page, int size) {
        List<SaleInvoice> allSaleInvoices = saleInvoiceService.getAll(page, size);
        return ResponseHandler.generateResponse(HttpStatus.OK, true, String.format("/sale-invoices?page=%d&size=%d", page, size),
                String.format("All SaleInvoice (page %d - size %d fetched successfully", page, size), allSaleInvoices);
    }

    @Override
    public ResponseEntity<Object> getOne(int id) {
        SaleInvoice saleInvoice = saleInvoiceService.getOne(id);
        return ResponseHandler.generateResponse(HttpStatus.OK, true,
                "/sale-invoices/" + saleInvoice.getId(), String.format("SaleInvoice %d fetched successfully.", saleInvoice.getId()), saleInvoice);
    }

    @Override
    public ResponseEntity<Object> saveOne(SaleInvoice object) {
        SaleInvoice savedInvoice = saleInvoiceService.saveOne(object);
        return ResponseHandler.generateResponse(HttpStatus.CREATED, true, "/sale-invoices/" + savedInvoice.getId(),
                String.format("SaleInvoice %d created successfully.", savedInvoice.getId()), savedInvoice);
    }

    @Override
    public ResponseEntity<Object> updateOne(int id, SaleInvoice object) {
        SaleInvoice savedInvoice = saleInvoiceService.updateOne(id, object);
        return ResponseHandler.generateResponse(HttpStatus.OK, true, "/sale-invoices" + savedInvoice.getId(),
                String.format("SaleInvoice %d updated successfully.", savedInvoice.getId()), savedInvoice);
    }

    @Override
    public ResponseEntity<Object> deleteOne(int id) {
        int result = saleInvoiceService.deleteOne(id);
        return ResponseHandler.generateResponse(HttpStatus.ACCEPTED, true, String.format("/sale-invoices/%d", result),
                String.format("SaleInvoice %d deleted successfully.", result), null);
    }

    // READ ALL SaleDetail of a SaleInvoice
    @RequestMapping(path = "/{id}/sale-details", method = RequestMethod.GET)
    public ResponseEntity<Object> getAllSaleDetailsBySaleInvoice(@PathVariable int id) {
        List<SaleDetail> saleDetailList = saleInvoiceService.getAllSaleDetailsBySaleInvoice(id);
        return ResponseHandler.generateResponse(HttpStatus.OK, true, String.format("/sale-invoices/%d/sale-details", id),
                String.format("All SaleDetail of SaleInvoice %d fetched successfully.", id), saleDetailList);
    }

    // FILTER SaleInvoices by startDate and endDate (dd-MM-yyyy)
    @RequestMapping(path = "/filter", method = RequestMethod.GET, params = {"startDate", "endDate"})
    public ResponseEntity<Object> filterByDate(@RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") Date startDate, @RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") Date endDate) {
        List<SaleInvoice> saleInvoiceList = saleInvoiceService.filterByPeriod(startDate, endDate);
        String startDateStr = DateUtils.dateToString(startDate);
        String endDateStr = DateUtils.dateToString(endDate);
        return ResponseHandler.generateResponse(HttpStatus.OK, true, String.format("/sale-invoices/filter?startDate=%s&endDate=%s", startDateStr, endDateStr),
                String.format("All SaleInvoices between %s and %s fetched successfully.", startDateStr, endDateStr), saleInvoiceList);
    }

    // READ ALL SaleInvoice By a Customer in a period
    @RequestMapping(path = "/by-customer/{id}/filter", method = RequestMethod.GET, params = {"startDate", "endDate"})
    public ResponseEntity<Object> getAllSaleInvoicesByCustomerAndPeriod(@PathVariable int id,
                                                                        @RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") Date startDate,
                                                                        @RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") Date endDate) {
        String startDateStr = DateUtils.dateToString(startDate);
        String endDateStr = DateUtils.dateToString(endDate);
        List<SaleInvoice> saleInvoiceList = saleInvoiceService.getAllSaleInvoicesByCustomerAndPeriod(id, startDate, endDate);
        return ResponseHandler.generateResponse(HttpStatus.OK, true,
                String.format("/sale-invoices/by-customer/%d/filter?startDate=%s&endDate=%s", id, startDateStr, endDateStr),
                String.format("All SaleInvoice by Customer %d between %s and %s fetched successfully.", id, startDateStr, endDateStr), saleInvoiceList);
    }

    // READ ALL SaleInvoice By a Staff in a period
    @RequestMapping(path = "/by-staff/{id}/filter", method = RequestMethod.GET, params = {"startDate", "endDate"})
    public ResponseEntity<Object> getAllSaleInvoicesByStaffAndPeriod(@PathVariable int id,
                                                                     @RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") Date startDate,
                                                                     @RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") Date endDate) {
        String startDateStr = DateUtils.dateToString(startDate);
        String endDateStr = DateUtils.dateToString(endDate);
        List<SaleInvoice> saleInvoiceList = saleInvoiceService.getAllSaleInvoicesByStaffAndPeriod(id, startDate, endDate);
        return ResponseHandler.generateResponse(HttpStatus.OK, true,
                String.format("/sale-invoices/by-staff/%d/filter?startDate=%s&endDate=%s", id, startDateStr, endDateStr),
                String.format("All SaleInvoice by Staff %d between %s and %s fetched successfully.", id, startDateStr, endDateStr), saleInvoiceList);
    }


}
