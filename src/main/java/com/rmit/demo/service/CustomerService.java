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
        return customerRepository.save(customer);
    }

    // Update one customer
    public Customer updateCustomer(int id, Customer customer) {
        Customer existingCustomer = customerRepository.findById(id).orElse(null);
        if(existingCustomer != null) {
            existingCustomer.setId(id);
            existingCustomer.setContactPerson(customer.getContactPerson());
            existingCustomer.setPhone(customer.getPhone());
            existingCustomer.setAddress(customer.getAddress());
            existingCustomer.setEmail(customer.getEmail());
            existingCustomer.setFax(customer.getFax());
            existingCustomer.setName(customer.getName());
            return customerRepository.save(existingCustomer);
        }
        return null;
    }

    // Delete one customer
    public void deleteCustomer(int id) {
        customerRepository.deleteById(id);
    }

    // Get all customers
    public ArrayList<Customer> getAllCustomers() {
        ArrayList<Customer> customers = new ArrayList<>();
        customerRepository.findAll().forEach(customers::add);
        return customers;
    }

    // Get all customers by name
    public ArrayList<Customer> getAllCustomersByName(String name) {
        ArrayList<Customer> customers = new ArrayList<>(customerRepository.findAllByName(name));
        return customers.size() == 0 ? null : customers;
    }

    // Get all customers by address
    public ArrayList<Customer> getAllCustomersByAddress(String address) {
        ArrayList<Customer> customers = new ArrayList<>(customerRepository.findAllByAddress(address));
        return customers.size() == 0 ? null : customers;
    }

    // Get all customers by phone number
    public ArrayList<Customer> getAllCustomersByPhone(String phone) {
        ArrayList<Customer> customers = new ArrayList<>(customerRepository.findAllByPhone(phone));
        return customers.size() == 0 ? null : customers;
    }

    // Get one customer by id
    public Customer getOne(int id) {
        return customerRepository.findById(id).orElse(null);
    }

    // Get all customers by pagination
    public ArrayList<Customer> getAllCustomers(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Customer> allCustomers = customerRepository.findAll(pageable);
        if (allCustomers.hasContent()) {
            return new ArrayList<>(allCustomers.getContent());
        }
        return new ArrayList<>();
    }
}