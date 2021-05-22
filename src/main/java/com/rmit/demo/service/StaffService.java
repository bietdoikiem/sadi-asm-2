package com.rmit.demo.service;

import com.rmit.demo.model.Staff;
import com.rmit.demo.repository.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import java.util.ArrayList;

@Transactional
@Service
public class StaffService {

    @Autowired
    private StaffRepository staffRepository;

    // Save one staff
    public Staff saveStaff(Staff staff) {
        return staffRepository.save(staff);
    }

    // Update one staff
    public Staff updateStaff(int id, Staff staff) {
        Staff existingStaff = staffRepository.findById(id).orElse(null);
        if(existingStaff != null) {
            existingStaff.setId(id);
            existingStaff.setAddress(staff.getAddress());
            existingStaff.setEmail(staff.getEmail());
            existingStaff.setName(staff.getName());
            existingStaff.setPhone(staff.getPhone());
            staffRepository.save(existingStaff);
        }
        return null;
    }

    // Delete one staff
    public void deleteStaff(int id) {
        staffRepository.deleteById(id);
    }

    // Get all staffs
    public ArrayList<Staff> getAllStaffs() {
        return new ArrayList<>(staffRepository.findAll());
    }

    // Get all staffs by name
    public ArrayList<Staff> getAllStaffsByName(String name) {
        ArrayList<Staff> staffs = new ArrayList<>(staffRepository.findAllByName(name));
        return staffs.size() == 0 ? null : staffs;
    }

    // Get all staff by address
    public ArrayList<Staff> getAllStaffsByAddress(String address) {
        ArrayList<Staff> staffs = new ArrayList<>(staffRepository.findAllByAddress(address));
        return staffs.size() == 0 ? null : staffs;
    }

    // Get all staff by phone number
    public ArrayList<Staff> getAllStaffsByPhone(String phone) {
        ArrayList<Staff> staffs = new ArrayList<>(staffRepository.findAllByPhone(phone));
        return staffs.size() == 0 ? null : staffs;
    }

    // Get one staff by id
    public Staff getOne(int id) {
        return staffRepository.findById(id).orElseThrow(NullPointerException::new);
    }

    // Get all staffs by pagination
    public ArrayList<Staff> getAllStaffs(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Staff> allStaffs = staffRepository.findAll(pageable);
        if(allStaffs.hasContent())
            return new ArrayList<>(allStaffs.getContent());
        return new ArrayList<>();
    }

}