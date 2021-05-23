package com.rmit.demo.repository.impl;

import com.rmit.demo.repository.CustomerRepositoryCustom;
import org.springframework.stereotype.Service;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Service
public class CustomerRepositoryImpl implements CustomerRepositoryCustom {
    @PersistenceContext
    private EntityManager em;
    }
