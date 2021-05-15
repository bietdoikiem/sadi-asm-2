package com.rmit.demo.service;
import com.rmit.demo.model.Provider;
import com.rmit.demo.repository.ProviderRepository;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.provider.HibernateUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Service
public class ProviderService {

    @Autowired
    private ProviderRepository providerRepository;

    public int saveProvider(Provider provider) {
        providerRepository.save(provider);
        return provider.getId();
    }
}
