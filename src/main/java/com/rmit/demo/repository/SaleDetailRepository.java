package com.rmit.demo.repository;

import com.rmit.demo.model.SaleDetail;
import com.rmit.demo.service.SaleDetailService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface SaleDetailRepository extends JpaRepository<SaleDetail, Integer>, SaleDetailRepositoryCustom {

}
