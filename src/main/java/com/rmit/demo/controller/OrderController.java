package com.rmit.demo.controller;

import com.rmit.demo.model.Order;
import com.rmit.demo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="/orders")
public class OrderController {

    private OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @RequestMapping(path="", method= RequestMethod.GET)
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

    @RequestMapping(path="", method= RequestMethod.POST)
    public int addOrder(@RequestBody Order order) {
        return orderService.saveOrder(order);
    }

    @RequestMapping(path= "", method = RequestMethod.PUT)
    public Order updateOrder(@RequestBody Order order) {
        return orderService.updateOrder(order);
    }

    @RequestMapping(path= "/{id}", method = RequestMethod.DELETE)
    public String deleteOrder(@PathVariable int id) {
        return orderService.deleteOrder(id);
    }
}
