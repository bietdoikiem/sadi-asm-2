package com.rmit.demo.repository.impl;

import com.rmit.demo.model.Order;
import com.rmit.demo.repository.OrderRepository;
import com.rmit.demo.repository.OrderRepositoryCustom;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class OrderRepositoryImpl implements OrderRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public Order saveAndReset(Order order) {
        // SAVE
        Order saved = orderRepository.saveAndFlush(order);
        // RESET
        em.clear();
        // RETURN
        return orderRepository.findById(saved.getId()).orElse(null);
    }
}
