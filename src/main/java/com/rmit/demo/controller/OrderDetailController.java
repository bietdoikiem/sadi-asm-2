package com.rmit.demo.controller;

import com.rmit.demo.model.OrderDetail;
import com.rmit.demo.service.OrderDetailService;
import com.rmit.demo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/order-details")
public class OrderDetailController {

    private OrderDetailService orderDetailService;

    @Autowired
    public OrderDetailController(OrderDetailService orderDetailService) {
        this.orderDetailService = orderDetailService;
    }

    // READ ALL
    @RequestMapping(path = "", method = RequestMethod.GET)
    public ResponseEntity<List<OrderDetail>> getAllOrderDetails() {
        return new ResponseEntity<>(orderDetailService.getAllOrderDetails(), HttpStatus.OK);
    }

    // READ ONE
    @RequestMapping(path = "{id}", method = RequestMethod.GET)
    public ResponseEntity<OrderDetail> getOrderDetailById(@PathVariable int id) {
        return new ResponseEntity<>(orderDetailService.getOrderDetailById(id), HttpStatus.OK);
    }

    // CREATE ONE
    @RequestMapping(path = "", method = RequestMethod.POST)
    public ResponseEntity<OrderDetail> saveOrderDetail(@RequestBody OrderDetail orderDetail) {
        return new ResponseEntity<>(orderDetailService.saveOrderDetail(orderDetail), HttpStatus.OK);
    }

    // Do UPDATE Here


    // Do DELETE Here

}
