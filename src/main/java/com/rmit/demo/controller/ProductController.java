package com.rmit.demo.controller;

import com.rmit.demo.model.Product;
import com.rmit.demo.service.ProductService;
import com.rmit.demo.utils.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping(path = "/products")
public class ProductController implements CrudController<Product> {

    private ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // READ all products
    public ResponseEntity<Object> getAll() {
        List<Product> listOfProducts = productService.getAll();
        return ResponseHandler.generateResponse(HttpStatus.OK, true, "/products", "All Products fetched successfully.", listOfProducts);
    }

    // READ all products by Pagination
    public ResponseEntity<Object> getAll(@RequestParam int page, @RequestParam int size) {
        List<Product> listOfProducts = productService.getAll(page, size);
        return ResponseHandler.generateResponse(HttpStatus.OK, true, "/products", String.format("Products (page %d - size %d) fetched successfully.", page, size), listOfProducts);
    }

    // READ one Product
    public ResponseEntity<Object> getOne(@PathVariable int id) {
        Product product = productService.getOne(id);
        return ResponseHandler.generateResponse(HttpStatus.OK, true, "/products/" + product.getId(),
                String.format("Product %d fetch successfully.", product.getId()), product);
    }

    // CREATE a product
    public ResponseEntity<Object> saveOne(@RequestBody Product product) {
        Product savedProduct = productService.saveOne(product);
        return ResponseHandler.generateResponse(HttpStatus.CREATED, true, "/products",
                String.format("Product %d created successfully", savedProduct.getId()), savedProduct);
    }

    // UPDATE a product
    public ResponseEntity<Object> updateOne(@PathVariable int id, @RequestBody Product productBody) {
//        return productService.updateProduct(id, productBody);
        Product updatedProduct = productService.updateOne(id, productBody);
        return ResponseHandler.generateResponse(HttpStatus.OK, true, "/products/" + updatedProduct.getId(),
                String.format("Product %d updated successfully.", updatedProduct.getId()), updatedProduct);
    }

    // DELETE a product
    public ResponseEntity<Object> deleteOne(@PathVariable int id) {
        int deletedId = productService.deleteOne(id);
        return ResponseHandler.generateResponse(HttpStatus.ACCEPTED, true, "/products/" + deletedId,
                String.format("Product %d deleted successfully", deletedId), null);
    }

}
