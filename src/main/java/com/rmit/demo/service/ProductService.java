package com.rmit.demo.service;
import com.rmit.demo.model.Product;
import com.rmit.demo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    // READ ALL Products
    public List<Product> getAllProducts() {
        var it = productRepository.findAll();
        var products = new ArrayList<Product>();
        it.forEach(products::add);

        return products;

    }
    // READ one Product
    public Product getOneProduct(int id) {
        return productRepository.findById(id).orElse(null);
    }

    // Read All Convert Products
    public List<Product> getConverts() {
        return productRepository.brandFind("Convert");
    }



    // Create Product
    public int saveProduct(Product product) {
        productRepository.save(product);
        return product.getId();
    }

    // Update a Product
    public int updateProduct(int productId, Product product) {
        Product foundProduct = productRepository.findById(productId).orElse(null);
        if (foundProduct != null) {
            foundProduct.setAll(product);
            productRepository.save(foundProduct);
            return productId;
        }
        return -1;
    }

    // Delete a Product
    public int deleteProduct(int productId) {
        Product product = productRepository.findById(productId).orElse(null);
        if (product != null) {
            productRepository.delete(product);
            return productId;
        }
        return -1;
    }
}
