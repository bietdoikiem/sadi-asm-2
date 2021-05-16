package com.rmit.demo.controller;
import com.rmit.demo.model.Customer;
import com.rmit.demo.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;

@RestController
@RequestMapping(path="/customers")
public class CustomerController {
    private final CustomerService customerService;
    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    // Get all customers
    @RequestMapping(path="", method=RequestMethod.GET)
    public ArrayList<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    // Get one customer by id
    @RequestMapping(value="/search/id={id}", method = RequestMethod.GET)
    public Customer getOne(@PathVariable("id") int id){
        return customerService.getOne(id);
    }

    // Update one customer by id
    @RequestMapping(value = "/id={id}", method = RequestMethod.PUT)
    public String updateCustomer(@PathVariable int id, @RequestBody Customer customer) {
        customer.setId(id);
        return customerService.updateCustomer(customer);
    }

    // Delete one customer by id
    @RequestMapping(value = "/id={id}", method = RequestMethod.DELETE)
    public String deleteCustomer(@PathVariable int id) {
        return customerService.deleteCustomer(getOne(id));
    }

    // Add one customer
    @RequestMapping(path="", method=RequestMethod.POST)
    public int addCustomer(@RequestBody Customer customer) {
        return customerService.saveCustomer(customer);
    }
}