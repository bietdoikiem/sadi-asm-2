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
public class ProductController {
    private ProductService productService;


    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // READ all products
    @RequestMapping(path = "", method = RequestMethod.GET)
    public ResponseEntity<Object> getAllProducts() {
        List<Product> listOfProducts = productService.getAllProducts();
        return ResponseHandler.generateResponse(HttpStatus.OK, true, "/products", "All Products fetched successfully.", listOfProducts);
    }
    // READ all products by Pagination
    @RequestMapping(path = "", method = RequestMethod.GET, params={"page", "size"})
    public ResponseEntity<Object> getAllProducts(@RequestParam int page, @RequestParam int size) {
        List<Product> listOfProducts = productService.getAllProducts(page, size);
        return ResponseHandler.generateResponse(HttpStatus.OK, true, "/products", String.format("Products (page %d - size %d) fetched successfully.", page, size), listOfProducts);
    }

    // READ specific products on conditions
    @RequestMapping(path = "converts/find", method = RequestMethod.GET)
    public ResponseEntity<Object> getConverts() {
        List<Product> listOfConverts = productService.getConverts();
        return ResponseHandler.generateResponse(HttpStatus.OK, true, "/products/converts/find", "All Convert Products fetched successfully.", listOfConverts);

//        return productService.getConverts();
    }

    // READ one Product
    @RequestMapping(path = "{id}", method = RequestMethod.GET)
    public ResponseEntity<Object> getOneProduct(@PathVariable int id) {
        try {
            Product product = productService.getOneProduct(id);
            return ResponseHandler.generateResponse(HttpStatus.OK, true, "/products/" + product.getId(),
                    String.format("Product %d fetch successfully.", product.getId()), product);
        } catch (NullPointerException e) {
            return ResponseHandler.generateResponse(HttpStatus.OK, true, "/products/" + id,
                    String.format("Product %d not found.", id), new HashMap());
        }
    }

    // CREATE a product
    @RequestMapping(path = "", method = RequestMethod.POST)
    public ResponseEntity<Object> addProduct(@RequestBody Product product) {
        Product savedProduct = productService.saveProduct(product);
        return ResponseHandler.generateResponse(HttpStatus.CREATED, true, "/products/" + savedProduct.getId(),
                String.format("Product %d created successfully", savedProduct.getId()), savedProduct);
    }

    // UPDATE a product
    @RequestMapping(path = "{id}", method = RequestMethod.PUT)
    public ResponseEntity<Object> updateProduct(@PathVariable int id, @RequestBody Product productBody) {
//        return productService.updateProduct(id, productBody);
        Product updatedProduct = productService.updateProduct(id, productBody);
        return ResponseHandler.generateResponse(HttpStatus.OK, true, "/products/" + updatedProduct.getId(),
                String.format("Product %d updated successfully.", updatedProduct.getId()), updatedProduct);
    }

    // DELETE a product
    @RequestMapping(path = "{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> deleteProduct(@PathVariable int id) {
        int deletedId = productService.deleteProduct(id);
        return ResponseHandler.generateResponse(HttpStatus.ACCEPTED, true, "/products/" + deletedId,
                String.format("Product %d deleted successfully", deletedId), null);
    }
}
