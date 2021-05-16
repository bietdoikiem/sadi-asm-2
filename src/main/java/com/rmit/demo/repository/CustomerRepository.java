package com.rmit.demo.repository;

import com.rmit.demo.model.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface CustomerRepository extends CrudRepository<Customer, Integer>, CustomerRepositoryCustom {
    Customer findByName(String name);
    Customer findById(int id);
    Customer findByAddress(String address);
    Customer findByPhone(String phone);
}