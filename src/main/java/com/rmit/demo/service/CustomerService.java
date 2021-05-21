package com.rmit.demo.service;
import com.rmit.demo.model.Customer;
import com.rmit.demo.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;

@Transactional
@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public int saveCustomer(Customer customer) {
        customerRepository.save(customer);
        return customer.getId();
    }

    public String updateCustomer(Customer customer) {
        customerRepository.save(customer);
        return "Updated successfully!";
    }

    public String deleteCustomer(Customer customer) {
        customerRepository.delete(customer);
        return "Delete successfully!";
    }

    public ArrayList<Customer> getAllCustomers() {
        ArrayList<Customer> customers = new ArrayList<>();
        customerRepository.findAll().forEach(customers::add);;
        return customers;
    }

    public Customer getOne(int id) {
        return customerRepository.findById(id).orElseThrow(NullPointerException::new);
    }
}