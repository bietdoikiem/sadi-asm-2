package com.rmit.demo.controller;

import com.rmit.demo.model.Customer;
import com.rmit.demo.service.CustomerService;
import com.rmit.demo.utils.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.HashMap;

@RestController
@RequestMapping(path="/customers")
public class CustomerController implements CrudController<Customer>{
    private final CustomerService customerService;
    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    // Get all customers
    @Override
    public ResponseEntity<Object> getAll() {
        return ResponseHandler.generateResponse(HttpStatus.OK, true, "/customers", "All Customers fetched successfully.", customerService.getAllCustomers());
    }

    // Get one customer by id
    @Override
    public ResponseEntity<Object> getOne(@PathVariable int id) {
        try {
            Customer customer = customerService.getOne(id);
            return ResponseHandler.generateResponse(HttpStatus.OK, true, "/customers/" + customer.getId(), String.format("Customer with the id %d fetched successfully.", customer.getId()), customer);
        } catch (NullPointerException e) {
            return ResponseHandler.generateResponse(HttpStatus.NOT_FOUND, false, "/customers/" + id, String.format("Customer with the id %d not found.", id), new HashMap());
        }
    }

    // Update one customer by id
    @Override
    public ResponseEntity<Object> updateOne(@PathVariable int id, @RequestBody Customer customer) {
        Customer updatedCustomer = customerService.updateCustomer(id, customer);
        return ResponseHandler.generateResponse(HttpStatus.OK, true, "/customers/" + updatedCustomer.getId(), String.format("Customer %d updated successfully.", updatedCustomer.getId()), updatedCustomer);
    }

    // Delete one customer by id
    @Override
    public ResponseEntity<Object> deleteOne(@PathVariable int id) {
        customerService.deleteCustomer(id);
        return ResponseHandler.generateResponse(HttpStatus.ACCEPTED, true, "/customers/" + id, String.format("Customer %d deleted successfully.", id), null);
    }

    // Add one customer
    @Override
    public ResponseEntity<Object> saveOne(@RequestBody Customer customer) {
        Customer savedCustomer = customerService.saveCustomer(customer);
        return ResponseHandler.generateResponse(HttpStatus.CREATED, true, "/customers/" + savedCustomer.getId(), String.format("Customer %d created successfully.", savedCustomer.getId()), savedCustomer);
    }

    // Get all customers by pagination
    @Override
    public ResponseEntity<Object> getAll(int page, int size) {
        ArrayList<Customer> allCustomers = customerService.getAllCustomers(page, size);
        return ResponseHandler.generateResponse(HttpStatus.OK, true, String.format("/customers?page=%d&size=%d", page, size),
                String.format("All Customers (page %d - size %d) fetched successfully", page, size), allCustomers);
    }

    // Get All by Name
    @RequestMapping(path="", params = "name")
    public @ResponseBody ResponseEntity<Object> getAllCustomersByName(@RequestParam("name") String name) {
        ArrayList<Customer> customers = customerService.getAllCustomersByName(name);
        return customers != null
                ? ResponseHandler.generateResponse(HttpStatus.OK, true, "/customers?name=" + name, "All Customers are " + name + " fetched successfully.", customers)
                : ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, false, "/customers?name=" + name, String.format("Customers are %s not found.", name), new HashMap());
    }

    // Get All by Address
    @RequestMapping(path="", params = "address")
    public @ResponseBody ResponseEntity<Object> getAllCustomersByAddress(@RequestParam("address") String address) {
        ArrayList<Customer> customers = customerService.getAllCustomersByAddress(address);
        return customers != null
                ? ResponseHandler.generateResponse(HttpStatus.OK, true, "/customers?address=" + address, "All Customers are in " + address + " fetched successfully.", customers)
                : ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, false, "/customers?address=" + address, String.format("Customers are in %s not found.", address), new HashMap());
    }

    // Get All by Phone number
    @RequestMapping(path="", params = "phone")
    public @ResponseBody ResponseEntity<Object> getAllCustomersByPhone(@RequestParam("phone") String phone) {
        ArrayList<Customer> customers = customerService.getAllCustomersByPhone(phone);
        return customers != null
                ? ResponseHandler.generateResponse(HttpStatus.OK, true, "/customers?phone=" + phone, "All Customers having the phone number of " + phone + " fetched successfully.", customers)
                : ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, false, "/customers?phone=" + phone, String.format("Customers having the phone number of %s not found.", phone), new HashMap());
    }
}