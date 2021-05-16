package com.rmit.demo.service;

import com.rmit.demo.model.Order;
import com.rmit.demo.model.Product;
import com.rmit.demo.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Transactional
@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public List<Order> getAllOrders() {
        var it = orderRepository.findAll();
        var orders = new ArrayList<Order>();
        it.forEach(orders::add);

        return orders;
    }

    public Order getOrderById(int id) {
        return orderRepository.findById(id).orElse(null);
    }

    public Order saveOrder(Order order) {
        return orderRepository.save(order);
    }

    public String deleteOrder(int id) {
        orderRepository.deleteById(id);
        return "product " + id + " removed!!";
    }

    public Order updateOrder(int id, Order order) {
        Order existingOrder = orderRepository.findById(id).orElse(null);
        if (existingOrder != null) {
            existingOrder.setId(order.getId());
            existingOrder.setDate(order.getDate());
            return orderRepository.save(existingOrder);
        }
        return null;

    }
}
