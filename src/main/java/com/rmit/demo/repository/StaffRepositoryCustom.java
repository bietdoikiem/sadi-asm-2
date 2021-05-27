package com.rmit.demo.repository;

import com.rmit.demo.model.Staff;

public interface StaffRepositoryCustom {
    Staff saveAndReset(Staff staff);
}
