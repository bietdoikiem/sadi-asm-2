package com.rmit.demo.controller;

import com.rmit.demo.model.SaleInvoice;
import com.rmit.demo.repository.SaleInvoiceRepository;
import com.rmit.demo.service.SaleInvoiceService;
import com.rmit.demo.utils.DateUtils;
import com.rmit.demo.utils.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    @RequestMapping(path = "filter", method = RequestMethod.GET, params = {"startDate", "endDate"})
    public ResponseEntity<Object> filterByDate(@RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") Date startDate, @RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") Date endDate) {
        List<SaleInvoice> saleInvoiceList = saleInvoiceService.filterByDate(startDate, endDate);
        String startDateStr = DateUtils.dateToString(startDate);
        String endDateStr = DateUtils.dateToString(endDate);
        return ResponseHandler.generateResponse(HttpStatus.OK, true, String.format("/sale-invoices/search?startDate=%s&endDate=%s", startDateStr, endDateStr),
                String.format("All SaleInvoices between %s and %s fetched successfully.", startDateStr, endDateStr),saleInvoiceList);
    }
}
