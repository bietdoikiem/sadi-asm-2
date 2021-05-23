package com.rmit.demo.controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.rmit.demo.model.Order;
import com.rmit.demo.model.Product;
import com.rmit.demo.model.Provider;
import com.rmit.demo.service.OrderService;
import com.rmit.demo.utils.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.*;

@RestController
@RequestMapping(path = "/orders")
public class OrderController implements CrudController<Order>{

    private OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // Filter by Date
    @RequestMapping(value="/filter", method = RequestMethod.GET)
    public ArrayList<Order> filterByStartAndEndDate(@RequestParam String startDate, @RequestParam String endDate) throws ParseException {
        return orderService.getOrdersByStartDateAndEndDate(startDate, endDate);
    }

    // Get all order
    @Override
    public ResponseEntity<Object> getAll() {
        return ResponseHandler.generateResponse(HttpStatus.OK, true, "/orders", "All Orders fetched successfully.", orderService.getAllOrders());
    }

    // Get all order by pagination
    @Override
    public ResponseEntity<Object> getAll(@RequestParam int page, @RequestParam int size) {
        List<Order> listOfOrders = orderService.getAll(page, size);
        return ResponseHandler.generateResponse(HttpStatus.OK, true, "/orders", String.format("Orders (page %d - size %d) fetched successfully.", page, size), listOfOrders);
    }

    // Get one order by id
    @Override
    public ResponseEntity<Object> getOne(int id) {
        Order order = orderService.getOrderById(id);
        return ResponseHandler.generateResponse(HttpStatus.OK, true, "/orders/" + order.getId(), String.format("Order %d fetched successfully.", order.getId()), order);
    }

    // Add an order
    @Override
    public ResponseEntity<Object> saveOne(Order order) {
        Order savedOrder = orderService.saveOrder(order);
        return ResponseHandler.generateResponse(HttpStatus.CREATED, true, "/orders/" + savedOrder.getId(), String.format("Order %d created successfully.", savedOrder.getId())
                , savedOrder);
    }

    // Update
    @Override
    public ResponseEntity<Object> updateOne(int id, Order order) {
        Order updatedOrder = orderService.updateOrder(id, order);
        return ResponseHandler.generateResponse(HttpStatus.OK, true, "/orders/" + order.getId(), String.format("Order %d updated successfully.", updatedOrder.getId()), updatedOrder);
    }

    // Delete order
    @Override
    public ResponseEntity<Object> deleteOne(int id) {
        orderService.deleteOrder(id);
        return ResponseHandler.generateResponse(HttpStatus.ACCEPTED, true, "/orders/" + id, String.format("Order %d deleted successfully.", id), null);
    }
}
