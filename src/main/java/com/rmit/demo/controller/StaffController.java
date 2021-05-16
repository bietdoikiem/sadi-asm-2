package com.rmit.demo.controller;
import com.rmit.demo.model.Staff;
import com.rmit.demo.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping(path="/staffs")
public class StaffController {
    private final StaffService staffService;
    @Autowired
    public StaffController(StaffService staffService) {
        this.staffService = staffService;
    }

    // Get all staffs
    @RequestMapping(path="", method=RequestMethod.GET)
    public ArrayList<Staff> getAllStaffs() {
        return staffService.getAllStaffs();
    }

    // Get one staff by id
    @RequestMapping(value="/search/id={id}", method = RequestMethod.GET)
    public Staff getOne(@PathVariable("id") int id){
        return staffService.getOne(id);
    }

    // Update one staff by id
    @RequestMapping(value = "/id={id}", method = RequestMethod.PUT)
    public String updateStaff(@PathVariable int id, @RequestBody Staff customer) {
        customer.setId(id);
        return staffService.updateStaff(customer);
    }

    // Delete one staff by id
    @RequestMapping(value = "/id={id}", method = RequestMethod.DELETE)
    public String deleteStaff(@PathVariable int id) {
        return staffService.deleteStaff(getOne(id));
    }


    @RequestMapping(path="", method=RequestMethod.POST)
    public int addStaff(@RequestBody Staff staff) {
        return staffService.saveStaff(staff);
    }
}
