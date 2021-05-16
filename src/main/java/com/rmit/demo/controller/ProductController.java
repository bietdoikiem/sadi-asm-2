package com.rmit.demo.controller;
import com.rmit.demo.model.Product;
import com.rmit.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping(path="/products")
public class ProductController {
    private ProductService productService;


    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // READ all products
    @RequestMapping(path="", method=RequestMethod.GET)
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    // READ specific products on conditions
    @RequestMapping(path="converts/find", method=RequestMethod.GET)
    public List<Product> getConverts() {
        return productService.getConverts();
    }

    // READ one Product
    @RequestMapping(path="{id}", method=RequestMethod.GET)
    public Product getOneProduct(@PathVariable int id) {
        return productService.getOneProduct(id);
    }

    // CREATE a product
    @RequestMapping(path="", method=RequestMethod.POST)
    public int addProduct(@RequestBody Product product) {
        return productService.saveProduct(product);
    }

    // UPDATE a product
    @RequestMapping(path="{id}", method=RequestMethod.PUT)
    public int updateProduct(@PathVariable int id, @RequestBody Product productBody) {
        return productService.updateProduct(id, productBody);
    }

    // DELETE a product
    @RequestMapping(path="{id}", method=RequestMethod.DELETE)
    public int deleteProduct(@PathVariable int id) {
        return productService.deleteProduct(id);
    }
}
