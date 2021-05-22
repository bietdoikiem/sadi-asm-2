package com.rmit.demo.repository;

import com.rmit.demo.model.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import javax.transaction.Transactional;
import java.util.ArrayList;

@Repository
@Transactional
public interface StaffRepository extends CrudRepository<Staff, Integer>, JpaRepository<Staff, Integer>,StaffRepositoryCustom {
    ArrayList<Staff> findAllByName(String name);
    ArrayList<Staff> findAllByAddress(String address);
    ArrayList<Staff> findAllByPhone(String phone);
    Staff findByName(String name);
    Staff findByAddress(String address);
    Staff findByPhone(String phone);
}