package com.rmit.demo.repository.impl;

import com.rmit.demo.model.Product;
import com.rmit.demo.repository.ProductRepository;
import com.rmit.demo.repository.ProductRepositoryCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TableGenerator;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.List;

@Service
public class ProductRepositoryImpl implements ProductRepositoryCustom {
    @PersistenceContext
    private EntityManager em;

    @Autowired
    private ProductRepository productRepository;

    public List<Product> brandFind(String brand) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Product> criteriaQuery = criteriaBuilder.createQuery(Product.class);
        Root<Product> productRoot = criteriaQuery.from(Product.class);

        Predicate predicateForConvert = criteriaBuilder.equal(productRoot.get("brand"), brand);
        criteriaQuery.where(predicateForConvert);
        return em.createQuery(criteriaQuery).getResultList();
    }

    @Transactional
    public Product saveAndReset(Product product) {
        // Save
        Product p = productRepository.saveAndFlush(product);
        // Reset
        em.clear();
        // Fetch new One
        return productRepository.findById(p.getId()).orElse(null);
    }
}
