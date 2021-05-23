package com.rmit.demo.controller;

import com.rmit.demo.model.Order;
import com.rmit.demo.model.OrderDetail;
import com.rmit.demo.model.Product;
import com.rmit.demo.service.OrderDetailService;
import com.rmit.demo.service.OrderService;
import com.rmit.demo.utils.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/order-details")
public class OrderDetailController implements CrudController<OrderDetail>{

    private OrderDetailService orderDetailService;

    @Autowired
    public OrderDetailController(OrderDetailService orderDetailService) {
        this.orderDetailService = orderDetailService;
    }

    // Get all order details
    @Override
    public ResponseEntity<Object> getAll() {
        return new ResponseEntity<>(orderDetailService.getAll(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> getAll(int page, int size) {
        List<OrderDetail> listOfOrderDetails = orderDetailService.getAll(page, size);
        return ResponseHandler.generateResponse(HttpStatus.OK, true, "/order-details", String.format("Order details (page %d - size %d) fetched successfully.", page, size), listOfOrderDetails);
    }

    // Get one order detail by id
    @Override
    public ResponseEntity<Object> getOne(int id) {
        return new ResponseEntity<>(orderDetailService.getOne(id), HttpStatus.OK);
    }

    // Create an order detail
    @Override
    public ResponseEntity<Object> saveOne(OrderDetail orderDetail) {
        return new ResponseEntity<>(orderDetailService.saveOne(orderDetail), HttpStatus.OK);
    }

    // Update order detail
    @Override
    public ResponseEntity<Object> updateOne(int id, OrderDetail orderDetail) {
        OrderDetail updatedOrderDetail = orderDetailService.updateOne(id, orderDetail);
        return ResponseHandler.generateResponse(HttpStatus.OK, true, "/order-details/" + orderDetail.getId(), String.format("Order detail %d updated successfully.", updatedOrderDetail.getId()), updatedOrderDetail);
    }

    // Delete order detail
    @Override
    public ResponseEntity<Object> deleteOne(int id) {
        orderDetailService.deleteOne(id);
        return ResponseHandler.generateResponse(HttpStatus.ACCEPTED, true, "/order-details/" + id, String.format("Order detail %d deleted successfully.", id), null);
    }
}
