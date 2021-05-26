package com.rmit.demo.service;

import com.rmit.demo.model.Order;
import com.rmit.demo.model.Product;
import com.rmit.demo.model.Provider;
import com.rmit.demo.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Transactional
@Service
public class OrderService implements CrudService<Order> {

    @Autowired
    private OrderRepository orderRepository;

    // Get all orders
    public List<Order> getAll() {
        var it = orderRepository.findAll();
        var orders = new ArrayList<Order>();
        it.forEach(orders::add);

        return orders;
    }

    // READ ALL Order Pagination
    public List<Order> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Order> allOrders = orderRepository.findAll(pageable);

        if (allOrders.hasContent()) {
            return allOrders.getContent();
        }
        return new ArrayList<>();

    }

    // Get a order
    public Order getOne(int id) {
        return orderRepository.findById(id).orElse(null);
    }

    // create order
    public Order saveOne(Order order) {
        return orderRepository.saveAndReset(order);
    }

    // Delete order
    public int deleteOne(int orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(NullPointerException::new);
        orderRepository.delete(order);
        return order.getId();
    }

    // Update order
    public Order updateOne(int id, Order order) {
        Order existingOrder = orderRepository.findById(id).orElseThrow(NullPointerException::new);
        if (existingOrder != null) {
            existingOrder.setId(order.getId());
            existingOrder.setDate(order.getDate());
            existingOrder.setStaff(order.getStaff());
            existingOrder.setProvider(order.getProvider());
            existingOrder.setOrderDetails(order.getOrderDetails());
            return orderRepository.saveAndReset(existingOrder);
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
                filteredOrders.add(order);
            }
        }
        return filteredOrders;
    }
}
