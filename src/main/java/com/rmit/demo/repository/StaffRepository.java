package com.rmit.demo.repository;
import com.rmit.demo.model.Staff;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface StaffRepository extends CrudRepository<Staff, Integer>, StaffRepositoryCustom {
    Staff findByName(String name);
    Staff findById(int id);
    Staff findByAddress(String address);
    Staff findByPhone(String phone);
}
