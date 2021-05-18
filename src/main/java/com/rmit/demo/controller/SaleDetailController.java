package com.rmit.demo.controller;


import com.rmit.demo.model.SaleDetail;
import com.rmit.demo.repository.SaleDetailRepository;
import com.rmit.demo.service.SaleDetailService;
import com.rmit.demo.utils.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path="/sale-details")
public class SaleDetailController implements CrudController<SaleDetail> {

    private SaleDetailService saleDetailService;
    @Autowired
    public SaleDetailController(SaleDetailService saleDetailService) {
        this.saleDetailService = saleDetailService;
    }

    @Override
    public ResponseEntity<Object> getAll() {
        List<SaleDetail> saleDetailList = saleDetailService.getAll();
        return ResponseHandler.generateResponse(HttpStatus.OK, true, "/sale-details", "All SaleDetail fetched successfully.", saleDetailList);
    }

    @Override
    public ResponseEntity<Object> getAll(int page, int size) {
        List<SaleDetail> saleDetailList = saleDetailService.getAll(page, size);
        return ResponseHandler.generateResponse(HttpStatus.OK, true, String.format("/sale-details?page=%d&size=%d", page, size),
                String.format("All SaleDetail (page %d - size %d", page, size), saleDetailList);
    }

    @Override
    public ResponseEntity<Object> getOne(int id) {
        SaleDetail saleDetail = saleDetailService.getOne(id);
        return ResponseHandler.generateResponse(HttpStatus.OK, true, String.format("/sale-details/" + saleDetail.getId()),
                String.format("SaleDetail %d fetched successfully.", saleDetail.getId()), saleDetail);
    }

    @Override
    public ResponseEntity<Object> saveOne(SaleDetail object) {
        SaleDetail saved = saleDetailService.saveOne(object);
        return ResponseHandler.generateResponse(HttpStatus.CREATED, true, "/sale-details/" + saved.getId(),
                String.format("SaleDetail %d created successfully", saved.getId()), saved);
    }

    @Override
    public ResponseEntity<Object> updateOne(int id, SaleDetail object) {
        SaleDetail saleDetail = saleDetailService.updateOne(id, object);
        return ResponseHandler.generateResponse(HttpStatus.OK, true, String.format("/sale-details/%d", saleDetail.getId()),
                String.format("SaleDetail %d updated successfully.", saleDetail.getId()), saleDetail);
    }

    @Override
    public ResponseEntity<Object> deleteOne(int id) {
        int result = saleDetailService.deleteOne(id);
        return ResponseHandler.generateResponse(HttpStatus.ACCEPTED, true, String.format("/sale-details/%d",result),
                String.format("SaleDetail %d deleted successfully.", result), null);
    }
}
