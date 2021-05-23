package com.rmit.demo.service;

import com.rmit.demo.model.OrderDetail;
import com.rmit.demo.model.Product;
import com.rmit.demo.repository.OrderDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderDetailService implements CrudService<OrderDetail> {
    @Autowired
    private OrderDetailRepository orderDetailRepository;

    // READ All OrderDetail
    public List<OrderDetail> getAll() {
        var it = orderDetailRepository.findAll();
        var orderDetails = new ArrayList<OrderDetail>();
        it.forEach(orderDetails::add);
        return orderDetails;
    }

    // READ ALL Order Details Pagination
    public List<OrderDetail> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<OrderDetail> allOrderDetails = orderDetailRepository.findAll(pageable);

        if (allOrderDetails.hasContent()) {
            return allOrderDetails.getContent();
        }
        return new ArrayList<>();

    }

    // READ One OrderDetail by ID
    public OrderDetail getOne(int id) {
        return orderDetailRepository.findById(id).orElse(null);
    }

    // POST New OrderDetail by ID
    public OrderDetail saveOne(OrderDetail orderDetail) {
        return orderDetailRepository.save(orderDetail);
    }

    // DO UPDATE Here
    public OrderDetail updateOne(int orderDetailId, OrderDetail orderDetail) {
        OrderDetail foundOrderDetail = orderDetailRepository.findById(orderDetailId).orElse(null);
        if (foundOrderDetail != null) {
            foundOrderDetail.setAll(orderDetail);

            return orderDetailRepository.save(foundOrderDetail);
        }
        return null;
    }

    // Do DELETE Here
    public int deleteOne(int orderDetailId) {
        OrderDetail orderDetail = orderDetailRepository.findById(orderDetailId).orElse(null);
        orderDetailRepository.delete(orderDetail);
        return orderDetail.getId();

    }
}
