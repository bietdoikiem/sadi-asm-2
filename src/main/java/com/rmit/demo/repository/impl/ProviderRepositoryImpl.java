package com.rmit.demo.repository.impl;

import com.rmit.demo.model.Provider;
import com.rmit.demo.repository.ProviderRepositoryCustom;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@Service
public class ProviderRepositoryImpl implements ProviderRepositoryCustom {
    @PersistenceContext
    private EntityManager em;
}
