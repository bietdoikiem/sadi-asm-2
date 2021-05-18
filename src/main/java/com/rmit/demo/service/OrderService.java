package com.rmit.demo.service;

import com.rmit.demo.model.Order;
import com.rmit.demo.model.Product;
import com.rmit.demo.model.Provider;
import com.rmit.demo.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Transactional
@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    // Get all orders
    public List<Order> getAllOrders() {
        var it = orderRepository.findAll();
        var orders = new ArrayList<Order>();
        it.forEach(orders::add);

        return orders;
    }

    // Get a order
    public Order getOrderById(int id) {
        return orderRepository.findById(id).orElse(null);
    }

    // create order
    public Order saveOrder(Order order) {
        return orderRepository.save(order);
    }

    // Delete order
    public String deleteOrder(int id) {
        orderRepository.deleteById(id);
        return "product " + id + " removed!!";
    }

    // Update order
    public Order updateOrder(int id, Order order) {
        Order existingOrder = orderRepository.findById(id).orElse(null);
        if (existingOrder != null) {
            existingOrder.setId(order.getId());
            existingOrder.setDate(order.getDate());
            existingOrder.setStaff(order.getStaff());
            existingOrder.setProvider(order.getProvider());
            existingOrder.setOrderDetails(order.getOrderDetails());
            return orderRepository.save(existingOrder);
        }
        return null;

    }

    public ArrayList<Order> getOrdersByStartDateAndEndDate(String startDate, String endDate) throws ParseException {

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        sdf.setLenient(false);

        Date parsedStart = sdf.parse(startDate);
        Date parsedEnd = sdf.parse(endDate);

        ArrayList<Order> orders = new ArrayList<>();
        orderRepository.findAll().forEach(orders::add);

        ArrayList<Order> filteredOrders = new ArrayList<>();
        for (Order order : orders) {
            if ( parsedStart.getTime() <= order.getDate().getTime() && order.getDate().getTime() <= parsedEnd.getTime()) {
                //orderRepository.findById(order.getId()).forEach(filteredOrders::add);
                filteredOrders.add(order);
            }
        }
        return filteredOrders;
    }
}
