package com.rmit.demo.controller;
import com.rmit.demo.model.Product;
import com.rmit.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping(path="/products")
public class CustomerController {
    private ProductService productService;
    @Autowired
    public CustomerController(ProductService productService) {
        this.productService = productService;
    }

    @RequestMapping(path="/", method=RequestMethod.GET)
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @RequestMapping(path="converts/find", method=RequestMethod.GET)
    public List<Product> getConverts() {
        return productService.getConverts();
    }
    @RequestMapping(path="", method=RequestMethod.POST)
    public int addProduct(@RequestBody Product product) {
        return productService.saveProduct(product);
    }
}
