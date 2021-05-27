package com.rmit.demo.repository.impl;

import com.rmit.demo.model.Staff;
import com.rmit.demo.repository.StaffRepository;
import com.rmit.demo.repository.StaffRepositoryCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Service
public class StaffRepositoryImpl implements StaffRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private StaffRepository staffRepository;

    public Staff saveAndReset(Staff staff) {
        // SAVE
        Staff saved = staffRepository.saveAndFlush(staff);
        // RESET
        em.clear();
        // RETURN
        return staffRepository.findById(saved.getId()).orElse(null);
    }
}
