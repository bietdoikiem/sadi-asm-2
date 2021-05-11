package com.rmit.demo.service;
import com.rmit.demo.model.Product;
import com.rmit.demo.repository.ProductRepository;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;import org.springframework.context.annotation.Bean;
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
public class ProductService {

    @Autowired
    private ProductRepository productRepository;




    public List<Product> getAllProducts() {
        var it = productRepository.findAll();
        var products = new ArrayList<Product>();
        it.forEach(products::add);

        return products;

    }

    public List<Product> getConverts() {
        return productRepository.brandFind("Convert");
    }

    public int saveProduct(Product product) {
        productRepository.save(product);
        return product.getId();
    }
}
