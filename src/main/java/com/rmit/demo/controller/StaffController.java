package com.rmit.demo.controller;

import com.rmit.demo.model.Staff;
import com.rmit.demo.service.StaffService;
import com.rmit.demo.utils.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.HashMap;

@RestController
@RequestMapping(path="/staffs")
public class StaffController implements CrudController<Staff>{
    private final StaffService staffService;

    @Autowired
    public StaffController(StaffService staffService) {
        this.staffService = staffService;
    }

    // Get all staffs
    @Autowired
    public ResponseEntity<Object> getAll() {
        return ResponseHandler.generateResponse(HttpStatus.OK, true, "/staffs", "All Staffs fetched successfully.", staffService.getAllStaffs());
    }

    // Get one staff by id
    @Override
    public ResponseEntity<Object> getOne(@PathVariable("id") int id){
        try {
            Staff staff = staffService.getOne(id);
            return ResponseHandler.generateResponse(HttpStatus.OK, true, "/staffs/" + staff.getId(), String.format("Staff with the id %d fetched successfully.", staff.getId()), staff);
        } catch (NullPointerException e) {
            return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, false, "/staffs/" + id, String.format("Staff with the id %d not found.", id), new HashMap());
        }
    }

    // Update one staff by id
    @Override
    public ResponseEntity<Object> updateOne(@PathVariable int id, @RequestBody Staff staff) {
        Staff updatedStaff = staffService.updateStaff(id, staff);
        return ResponseHandler.generateResponse(HttpStatus.OK, true, "/staffs/" + updatedStaff.getId(), String.format("Staff %d updated successfully.", updatedStaff.getId()), updatedStaff);
    }

    // Delete one staff by id
    @Override
    public ResponseEntity<Object> deleteOne(@PathVariable int id) {
        staffService.deleteStaff(id);
        return ResponseHandler.generateResponse(HttpStatus.ACCEPTED, true, "/staffs/" + id, String.format("Staff %d deleted successfully.", id), null);
    }

    // Add one staff
    @Override
    public ResponseEntity<Object> saveOne(@RequestBody Staff staff) {
        Staff savedStaff = staffService.saveStaff(staff);
        return ResponseHandler.generateResponse(HttpStatus.CREATED, true, "/staffs/" + savedStaff.getId(), String.format("Staff %d created successfully.", savedStaff.getId()), savedStaff);
    }

    // Get all customers by pagination
    @Override
    public ResponseEntity<Object> getAll(int page, int size) {
        ArrayList<Staff> allStaffs = staffService.getAllStaffs(page, size);
        return ResponseHandler.generateResponse(HttpStatus.OK, true, String.format("/staffs?page=%d&size=%d", page, size),
                String.format("All Staffs (page %d - size %d) fetched successfully", page, size), allStaffs);
    }

    // Get All by Name
    @RequestMapping(path="", params = "name")
    public @ResponseBody ResponseEntity<Object> getAllCustomersByName(@RequestParam("name") String name) {
        ArrayList<Staff> staffs = staffService.getAllStaffsByName(name);
        return staffs != null
                ? ResponseHandler.generateResponse(HttpStatus.OK, true, "/staffs?name=" + name, "All Staffs are " + name + " fetched successfully.", staffs)
                : ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, false, "/staffs?name=" + name, String.format("Staffs are %s not found.", name), new HashMap());
    }

    // Get All by Address
    @RequestMapping(path="", params = "address")
    public @ResponseBody ResponseEntity<Object> getAllCustomersByAddress(@RequestParam("address") String address) {
        ArrayList<Staff> staffs = staffService.getAllStaffsByAddress(address);
        return staffs != null
                ? ResponseHandler.generateResponse(HttpStatus.OK, true, "/staffs?address=" + address, "All Staffs are in " + address + " fetched successfully.", staffs)
                : ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, false, "/staffs?address=" + address, String.format("Staffs are in %s not found.", address), new HashMap());
    }

    // Get All by Phone
    @RequestMapping(path="", params = "phone")
    public @ResponseBody ResponseEntity<Object> getAllCustomersByPhone(@RequestParam("phone") String phone) {
        ArrayList<Staff> staffs = staffService.getAllStaffsByPhone(phone);
        return staffs != null
                ? ResponseHandler.generateResponse(HttpStatus.OK, true, "/staffs?phone=" + phone, "All Staffs having the phone number of " + phone + " fetched successfully.", staffs)
                : ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, false, "/staffs?phone=" + phone, String.format("Staffs having the phone number of %s not found.", phone), new HashMap());
    }
}