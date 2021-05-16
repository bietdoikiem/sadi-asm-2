package com.rmit.demo.controller;

import com.rmit.demo.model.Order;
import com.rmit.demo.service.OrderService;
import com.rmit.demo.utils.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/orders")
public class OrderController {

    private OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @RequestMapping(path = "", method = RequestMethod.GET)
    public ResponseEntity<Object> getAllOrders() {
        return ResponseHandler.generateResponse(HttpStatus.OK, true, "/orders", "All Orders fetched successfully.", orderService.getAllOrders());
    }

    @RequestMapping(path = "{id}", method = RequestMethod.GET)
    public ResponseEntity<Object> getOrderById(@PathVariable int id) {
        try {
            Order order = orderService.getOrderById(id);
            return ResponseHandler.generateResponse(HttpStatus.OK, true, "/orders/" + order.getId(), String.format("Order %d fetched successfully.", order.getId()), order);
        } catch (NullPointerException e) {
            return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, false, "/orders/" + id, String.format("Order %d not found.", id), new HashMap());
        }
    }


    @RequestMapping(path = "", method = RequestMethod.POST)
    public ResponseEntity<Object> addOrder(@RequestBody Order order) {
        Order savedOrder = orderService.saveOrder(order);
        return ResponseHandler.generateResponse(HttpStatus.CREATED, true, "/orders/" + savedOrder.getId(), String.format("Order %d created successfully.", savedOrder.getId())
                , savedOrder);
    }

    @RequestMapping(path = "{id}", method = RequestMethod.PUT)
    public ResponseEntity<Object> updateOrder(@PathVariable int id, @RequestBody Order order) {
        Order updatedOrder = orderService.updateOrder(id, order);
        return ResponseHandler.generateResponse(HttpStatus.OK, true, "/orders/" + order.getId(), String.format("Order %d updated successfully.", updatedOrder.getId()), updatedOrder);
    }

    @RequestMapping(path = "{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> deleteOrder(@PathVariable int id) {
        orderService.deleteOrder(id);
        return ResponseHandler.generateResponse(HttpStatus.ACCEPTED, true, "/orders/" + id, String.format("Order %d deleted successfully.", id), null);
    }
}
