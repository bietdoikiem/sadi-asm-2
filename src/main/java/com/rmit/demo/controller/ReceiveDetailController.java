package com.rmit.demo.controller;

import com.rmit.demo.model.OrderDetail;
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
public class ReceiveDetailController {

    private ReceiveDetailService receiveDetailService;

    @Autowired
    public ReceiveDetailController(ReceiveDetailService receiveDetailService) {
        this.receiveDetailService = receiveDetailService;
    }

    // READ ALL
    @RequestMapping(path = "", method = RequestMethod.GET)
    public ResponseEntity<List<ReceiveDetail>> getAllReceiveDetails() {
        return new ResponseEntity<>(receiveDetailService.getAllReceiveDetails(), HttpStatus.OK);
    }

    // READ ONE
    @RequestMapping(path = "{id}", method = RequestMethod.GET)
    public ResponseEntity<ReceiveDetail> getReceiveDetailById(@PathVariable int id) {
        return new ResponseEntity<>(receiveDetailService.getReceiveDetailById(id), HttpStatus.OK);
    }

    // CREATE ONE
    @RequestMapping(path = "", method = RequestMethod.POST)
    public ResponseEntity<ReceiveDetail> saveReceiveDetail(@RequestBody ReceiveDetail receiveDetail) {
        return new ResponseEntity<>(receiveDetailService.saveReceiveDetail(receiveDetail), HttpStatus.OK);
    }

    // Do UPDATE Here
    @RequestMapping(path = "{id}", method = RequestMethod.PUT)
    public ResponseEntity<Object> updateReceiveDetail(@PathVariable int id, @RequestBody ReceiveDetail receiveDetail) {
        ReceiveDetail updatedReceiveDetail = receiveDetailService.updateReceiveDetail(id, receiveDetail);
        return ResponseHandler.generateResponse(HttpStatus.OK, true, "/receive-details/" + receiveDetail.getId(), String.format("Receive detail %d updated successfully.", updatedReceiveDetail.getId()), updatedReceiveDetail);
    }

    // Do DELETE Here
    @RequestMapping(path = "{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> deleteReceiveDetail(@PathVariable int id) {
        receiveDetailService.deleteReceiveDetail(id);
        return ResponseHandler.generateResponse(HttpStatus.ACCEPTED, true, "/receive-details/" + id, String.format("Receive detail %d deleted successfully.", id), null);
    }


}
