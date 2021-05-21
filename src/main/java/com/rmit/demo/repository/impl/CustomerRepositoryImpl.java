package com.rmit.demo.repository.impl;

<<<<<<< HEAD:src/main/java/com/rmit/demo/repository/CustomerRepositoryImpl.java
=======
import com.rmit.demo.model.Customer;
import com.rmit.demo.repository.CustomerRepositoryCustom;
>>>>>>> origin/invoice-feat:src/main/java/com/rmit/demo/repository/impl/CustomerRepositoryImpl.java
import org.springframework.stereotype.Service;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Service
public class CustomerRepositoryImpl implements CustomerRepositoryCustom {
    @PersistenceContext
    private EntityManager em;
    }
