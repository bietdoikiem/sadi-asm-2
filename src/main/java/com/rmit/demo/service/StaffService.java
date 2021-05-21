package com.rmit.demo.service;
import com.rmit.demo.model.Staff;
import com.rmit.demo.repository.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;

@Transactional
@Service
public class StaffService {

    @Autowired
    private StaffRepository staffRepository;

    public int saveStaff(Staff staff) {
        staffRepository.save(staff);
        return staff.getId();
    }

    public String updateStaff(Staff staff) {
        staffRepository.save(staff);
        return "Updated successfully!";
    }

    public String deleteStaff(Staff staff) {
        staffRepository.delete(staff);
        return "Delete successfully!";
    }

    public ArrayList<Staff> getAllStaffs() {
        ArrayList<Staff> staffs = new ArrayList<>();
        staffRepository.findAll().forEach(staffs::add);;
        return staffs;
    }

    public Staff getOne(int id) {
        return staffRepository.findById(id).orElseThrow(NullPointerException::new);
    }
}
