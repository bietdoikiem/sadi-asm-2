package com.rmit.demo.controller;
import com.rmit.demo.model.Staff;
import com.rmit.demo.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping(path="/staffs")
public class StaffController {
    private final StaffService staffService;
    @Autowired
    public StaffController(StaffService staffService) {
        this.staffService = staffService;
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
    public int addProduct(@RequestBody Staff staff) {
        return staffService.saveStaff(staff);
    }
}
