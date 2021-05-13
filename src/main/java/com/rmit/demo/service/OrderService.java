package com.rmit.demo.service;

import com.rmit.demo.model.Order;
import com.rmit.demo.model.Product;
import com.rmit.demo.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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

    public int saveOrder(Order order) {
        orderRepository.save(order);
        return order.getId();
    }

    public String deleteOrder(int id) {
        orderRepository.deleteById(id);
        return"product " + id + " removed!!";
    }

    public Order updateOrder(Order order) {
        Order existingOrder = orderRepository.findById(order.getId()).orElse(null);
        existingOrder.setId(order.getId());
        existingOrder.setStringDate(order.getStringDate());

        return orderRepository.save(existingOrder);
    }
}
