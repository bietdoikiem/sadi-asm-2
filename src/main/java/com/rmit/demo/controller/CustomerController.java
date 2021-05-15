package com.rmit.demo.controller;
import com.rmit.demo.model.Customer;
import com.rmit.demo.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping(path="/customers")
public class CustomerController {
    private CustomerService customerService;
    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

//    @RequestMapping(path="/", method=RequestMethod.GET)
//    public List<Customer> getAllProducts() {
//        return customerService.getAllProducts();
//    }
//
//    @RequestMapping(path="converts/find", method=RequestMethod.GET)
//    public List<Customer> getConverts() {
//        return customerService.getConverts();
//    }

    @RequestMapping(path="", method=RequestMethod.POST)
    public int addProduct(@RequestBody Customer customer) {
        return customerService.saveCustomer(customer);
    }
}
