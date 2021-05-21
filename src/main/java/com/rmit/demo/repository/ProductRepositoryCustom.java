package com.rmit.demo.repository;

import com.rmit.demo.model.Product;

import java.util.List;

public interface ProductRepositoryCustom {
    List<Product> brandFind(String brand);
    Product saveAndReset(Product product);
}
