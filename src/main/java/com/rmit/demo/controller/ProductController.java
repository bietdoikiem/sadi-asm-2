package com.rmit.demo.controller;
import com.rmit.demo.model.Product;
import com.rmit.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
public class ProductController {
    private ProductService productService;
    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @RequestMapping(path="/products", method=RequestMethod.GET)
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @RequestMapping(path="/products/converts", method=RequestMethod.GET)
    public List<Product> getConverts() {
        return productService.getConverts();
    }
    @RequestMapping(path="/products", method=RequestMethod.POST)
    public int addProduct(@RequestBody Product product) {
        return productService.saveProduct(product);
    }
}
