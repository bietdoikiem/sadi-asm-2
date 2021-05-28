package com.rmit.demo.service;

import com.rmit.demo.model.*;
import com.rmit.demo.repository.OrderDetailRepository;
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

    @Autowired
    private OrderDetailRepository orderDetailRepository;

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
        for (OrderDetail orderDetail: order.getOrderDetailList()) {
            orderDetail.setOrder(order);
        }
        return orderRepository.saveAndReset(order);
    }

    // Delete order
    public int deleteOne(int orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(NullPointerException::new);
        orderRepository.delete(order);
        return order.getId();
    }

    // Update order
    public Order updateOne(int orderId, Order order) {
        Order foundOrder = orderRepository.findById(orderId).orElse(null);
        if (foundOrder != null) {
            foundOrder.setAll(order);
            // Assign new list of order details and remove orphan (if there's ONE)
            if (order.getOrderDetailList() != null) {
                foundOrder.getOrderDetailList().clear();
                for (OrderDetail orderDetail : order.getOrderDetailList()) {
                    orderDetail.setOrder(foundOrder);
                }
                foundOrder.getOrderDetailList().addAll(order.getOrderDetailList());
            }
            return orderRepository.saveAndReset(foundOrder);
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

    public List<OrderDetail> getOrderDetailListByOrder(int orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(NullPointerException::new);
        return orderDetailRepository.findOrderDetailsByOrder(order);
    }
}
