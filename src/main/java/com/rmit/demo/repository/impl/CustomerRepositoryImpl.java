package com.rmit.demo.repository.impl;

import com.rmit.demo.model.Customer;
import com.rmit.demo.repository.CustomerRepository;
import com.rmit.demo.repository.CustomerRepositoryCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Service
public class CustomerRepositoryImpl implements CustomerRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private CustomerRepository customerRepository;

    public Customer saveAndReset(Customer customer) {
        // SAVE
        Customer saved = customerRepository.saveAndFlush(customer);
        // RESET
        em.clear();
        // RETURN
        return customerRepository.findById(saved.getId()).orElse(null);
    }
}