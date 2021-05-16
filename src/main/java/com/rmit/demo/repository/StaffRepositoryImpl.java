package com.rmit.demo.repository;

import com.rmit.demo.model.Staff;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@Service
public class StaffRepositoryImpl implements StaffRepositoryCustom {
    @PersistenceContext
    private EntityManager em;
}
