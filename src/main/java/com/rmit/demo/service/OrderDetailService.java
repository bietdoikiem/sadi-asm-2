package com.rmit.demo.service;

import com.rmit.demo.model.OrderDetail;
import com.rmit.demo.model.Product;
import com.rmit.demo.repository.OrderDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderDetailService {
    @Autowired
    private OrderDetailRepository orderDetailRepository;

    // READ All OrderDetail
    public List<OrderDetail> getAllOrderDetails() {
        var it = orderDetailRepository.findAll();
        var orderDetails = new ArrayList<OrderDetail>();
        it.forEach(orderDetails::add);
        return orderDetails;
    }

    // READ One OrderDetail by ID
    public OrderDetail getOrderDetailById(int id) {
        return orderDetailRepository.findById(id).orElse(null);
    }

    // POST New OrderDetail by ID
    public OrderDetail saveOrderDetail(OrderDetail orderDetail) {
        return orderDetailRepository.save(orderDetail);
    }

    // DO UPDATE Here
    public OrderDetail updateOrderDetail(int orderDetailId, OrderDetail orderDetail) {
        OrderDetail foundOrderDetail = orderDetailRepository.findById(orderDetailId).orElse(null);
        if (foundOrderDetail != null) {
            foundOrderDetail.setAll(orderDetail);

            return orderDetailRepository.save(foundOrderDetail);
        }
        return null;
    }

    // Do DELETE Here
    public String deleteOrderDetail(int orderDetailId) {
        OrderDetail orderDetail = orderDetailRepository.findById(orderDetailId).orElse(null);
        if (orderDetail != null) {
            orderDetailRepository.delete(orderDetail);
            return "order detail " + orderDetailId + " removed!!";
        }
        return null;
    }
}
