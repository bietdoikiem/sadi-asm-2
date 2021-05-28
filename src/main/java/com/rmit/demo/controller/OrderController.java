package com.rmit.demo.controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.rmit.demo.model.*;
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
    @RequestMapping(value="/filter", method = RequestMethod.GET, params = {"startDate", "endDate"})
    public ResponseEntity<Object> filterByStartAndEndDate(@RequestParam String startDate, @RequestParam String endDate) throws ParseException {
        ArrayList<Order> orderFilteredByStartDateAndEndDate = orderService.getOrdersByStartDateAndEndDate(startDate, endDate);
        return ResponseHandler.generateResponse(HttpStatus.ACCEPTED, true, String.format("/orders/filter?startDate=%s&endDate=%s", startDate, endDate), String.format("All orders between %s and %s fetched successfully.", startDate, endDate), orderFilteredByStartDateAndEndDate);
    }

    // Get all order
    @Override
    public ResponseEntity<Object> getAll() {
        return ResponseHandler.generateResponse(HttpStatus.OK, true, "/orders", "All Orders fetched successfully.", orderService.getAll());
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
        Order order = orderService.getOne(id);
        return ResponseHandler.generateResponse(HttpStatus.OK, true, "/orders/" + order.getId(), String.format("Order %d fetched successfully.", order.getId()), order);
    }

    // Add an order
    @Override
    public ResponseEntity<Object> saveOne(Order order) {
        Order savedOrder = orderService.saveOne(order);
        return ResponseHandler.generateResponse(HttpStatus.CREATED, true, "/orders/" + savedOrder.getId(), String.format("Order %d created successfully.", savedOrder.getId())
                , savedOrder);
    }

    // Update
    @Override
    public ResponseEntity<Object> updateOne(int id, Order order) {
        Order updatedOrder = orderService.updateOne(id, order);
        return ResponseHandler.generateResponse(HttpStatus.OK, true, "/orders/" + order.getId(), String.format("Order %d updated successfully.", updatedOrder.getId()), updatedOrder);
    }

    // Delete order
    @Override
    public ResponseEntity<Object> deleteOne(int id) {
        orderService.deleteOne(id);
        return ResponseHandler.generateResponse(HttpStatus.ACCEPTED, true, "/orders/" + id, String.format("Order %d deleted successfully.", id), null);
    }

    // READ All Order Details of an Order by its ID
    @RequestMapping(value = "/{id}/order-details", method = RequestMethod.GET)
    public ResponseEntity<Object> getOrderDetailListByOrder(@PathVariable int id) {
        List<OrderDetail> orderDetailList = orderService.getOrderDetailListByOrder(id);
        return ResponseHandler.generateResponse(HttpStatus.OK, true,
                String.format("/orders/%d/order-details", id),
                String.format("All OrderDetail of Order %d fetched successfully.", id),
                orderDetailList);
    }
}
