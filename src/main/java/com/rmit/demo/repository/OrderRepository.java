package com.rmit.demo.repository;


import com.rmit.demo.model.Order;
import com.rmit.demo.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface OrderRepository extends CrudRepository<Order, Integer>, JpaRepository<Order, Integer> {
}
