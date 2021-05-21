package com.rmit.demo.repository;

import com.rmit.demo.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface ProductRepository extends CrudRepository<Product, Integer>, ProductRepositoryCustom, JpaRepository<Product, Integer>{
    public Product findByName(String name);

    public Product findByPriceLessThan(double price);

    public List<Product> brandFind(String brand);

    Product saveAndReset(Product product);
}
