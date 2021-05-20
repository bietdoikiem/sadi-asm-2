package com.rmit.demo.repository;

import com.rmit.demo.model.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.ArrayList;

@Repository
@Transactional
public interface CustomerRepository extends CrudRepository<Customer, Integer>, CustomerRepositoryCustom {
    ArrayList<Customer> findAllByName(String name);
    ArrayList<Customer> findAllByAddress(String address);
    ArrayList<Customer> findAllByPhone(String phone);
    Customer findByAddress(String address);
    Customer findByPhone(String phone);
}