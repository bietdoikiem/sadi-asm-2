package com.rmit.demo.repository;

import com.rmit.demo.model.Order;

public interface OrderRepositoryCustom {
    Order saveAndReset(Order order);
}
