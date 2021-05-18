package com.rmit.demo.service;
import com.rmit.demo.model.Product;
import com.rmit.demo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Service
public class ProductService implements CrudService<Product> {

    @Autowired
    private ProductRepository productRepository;

    // READ ALL Products
    public List<Product> getAll() {
        var it = productRepository.findAll();
        var listOfProducts = new ArrayList<Product>();
        it.forEach(listOfProducts::add);
        return listOfProducts;
    }

    // READ ALL Products Pagination
    public List<Product> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Product> allProducts = productRepository.findAll(pageable);

        if(allProducts.hasContent()) {
            return allProducts.getContent();
        }
        return new ArrayList<>();

    }
    // READ one Product by ID
    public Product getOne(int id) {
        return productRepository.findById(id).orElse(null);
    }

    // Read All Convert Products
    public List<Product> getConverts() {
        return productRepository.brandFind("Convert");
    }


    // CREATE Product
    public Product saveOne(Product product) {
        // Fetch new One
        return productRepository.saveAndReset(product);
    }

    // UPDATE a Product
    public Product updateOne(int productId, Product product) {
        Product foundProduct = productRepository.findById(productId).orElse(null);
        if (foundProduct != null) {
            foundProduct.setName(product.getName());
            foundProduct.setModel(product.getModel());
            foundProduct.setBrand(product.getBrand());
            foundProduct.setCompany(product.getCompany());
            foundProduct.setDescription(product.getDescription());
            foundProduct.setPrice(product.getPrice());
            foundProduct.setCategory(product.getCategory());
            return productRepository.saveAndReset(foundProduct);
        }
        return null;
    }

    // DELETE a Product
    public int deleteOne(int productId) {
        Product product = productRepository.findById(productId).orElse(null);
        if (product != null) {
            productRepository.delete(product);
            return productId;
        }
        return -1;
    }
}
