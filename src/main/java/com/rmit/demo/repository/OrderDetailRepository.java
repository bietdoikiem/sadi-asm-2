package com.rmit.demo.repository;

import com.rmit.demo.model.OrderDetail;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface OrderDetailRepository extends CrudRepository<OrderDetail, Integer> {
}
