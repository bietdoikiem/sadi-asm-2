package com.rmit.demo.controller;

import com.rmit.demo.model.OrderDetail;
import com.rmit.demo.model.Product;
import com.rmit.demo.model.ReceiveDetail;
import com.rmit.demo.service.OrderDetailService;
import com.rmit.demo.service.ReceiveDetailService;
import com.rmit.demo.utils.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/receive-details")
public class ReceiveDetailController implements CrudController<ReceiveDetail>{

    private ReceiveDetailService receiveDetailService;

    @Autowired
    public ReceiveDetailController(ReceiveDetailService receiveDetailService) {
        this.receiveDetailService = receiveDetailService;
    }

    @Override
    public ResponseEntity<Object> getAll() {
        return new ResponseEntity<>(receiveDetailService.getAllReceiveDetails(), HttpStatus.OK);
    }

    // Read all receive detail by pagination
    @Override
    public ResponseEntity<Object> getAll(int page, int size) {
        List<ReceiveDetail> listOfReceiveDetails = receiveDetailService.getAll(page, size);
        return ResponseHandler.generateResponse(HttpStatus.OK, true, "/receive-details", String.format("Receive details (page %d - size %d) fetched successfully.", page, size), listOfReceiveDetails);
    }

    @Override
    public ResponseEntity<Object> getOne(int id) {
        return new ResponseEntity<>(receiveDetailService.getReceiveDetailById(id), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> saveOne(ReceiveDetail receiveDetail) {
        return new ResponseEntity<>(receiveDetailService.saveReceiveDetail(receiveDetail), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> updateOne(int id, ReceiveDetail receiveDetail) {
        ReceiveDetail updatedReceiveDetail = receiveDetailService.updateReceiveDetail(id, receiveDetail);
        return ResponseHandler.generateResponse(HttpStatus.OK, true, "/receive-details/" + receiveDetail.getId(), String.format("Receive detail %d updated successfully.", updatedReceiveDetail.getId()), updatedReceiveDetail);
    }

    // Delete
    @Override
    public ResponseEntity<Object> deleteOne(int id) {
        receiveDetailService.deleteReceiveDetail(id);
        return ResponseHandler.generateResponse(HttpStatus.ACCEPTED, true, "/receive-details/" + id, String.format("Receive detail %d deleted successfully.", id), null);
    }
}
