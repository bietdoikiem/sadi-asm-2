package com.rmit.demo.repository;

import com.rmit.demo.model.ReceiveDetail;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface ReceiveDetailRepository extends CrudRepository<ReceiveDetail, Integer> {
}
