package com.rmit.demo.repository.impl;

import com.rmit.demo.model.Staff;
import com.rmit.demo.repository.StaffRepositoryCustom;
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
