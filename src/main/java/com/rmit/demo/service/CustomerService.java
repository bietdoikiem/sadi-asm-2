package com.rmit.demo.service;

import com.rmit.demo.model.Customer;
import com.rmit.demo.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import java.util.ArrayList;

@Transactional
@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    // Save one customer
    public Customer saveCustomer(Customer customer) {
        return customerRepository.saveAndReset(customer);
    }

    // Update one customer
    public Customer updateCustomer(int id, Customer customer) {
        Customer existingCustomer = customerRepository.findById(id).orElseThrow(NullPointerException::new);
        existingCustomer.setContactPerson(customer.getContactPerson());
        existingCustomer.setPhone(customer.getPhone());
        existingCustomer.setAddress(customer.getAddress());
        existingCustomer.setEmail(customer.getEmail());
        existingCustomer.setFax(customer.getFax());
        existingCustomer.setName(customer.getName());
        customerRepository.saveAndReset(existingCustomer);
        return existingCustomer;
    }

    // Delete one customer
    public int deleteCustomer(int id) {
        Customer customer = customerRepository.findById(id).orElseThrow(NullPointerException::new);
        customerRepository.delete(customer);
        return customer.getId();
    }

    // Get all customers
    public ArrayList<Customer> getAllCustomers() {
        return new ArrayList<>(customerRepository.findAll());
    }

    // Get all customers by name
    public ArrayList<Customer> getAllCustomersByName(String name) {
        return new ArrayList<>(customerRepository.findAllByName(name));
    }

    // Get all customers by address
    public ArrayList<Customer> getAllCustomersByAddress(String address) {
        return new ArrayList<>(customerRepository.findAllByAddress(address));
    }

    // Get all customers by phone number
    public ArrayList<Customer> getAllCustomersByPhone(String phone) {
        return new ArrayList<>(customerRepository.findAllByPhone(phone));
    }

    // Get one customer by id
    public Customer getOne(int id) {
        return customerRepository.findById(id).orElseThrow(NullPointerException::new);
    }

    // Get all customers by pagination
    public ArrayList<Customer> getAllCustomers(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Customer> allCustomers = customerRepository.findAll(pageable);
        if(allCustomers.hasContent())
            return (ArrayList<Customer>) allCustomers.getContent();
        return new ArrayList<>();
    }
}