package com.rmit.demo.controller;

import com.rmit.demo.model.Category;
import com.rmit.demo.service.CategoryService;
import com.rmit.demo.utils.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(path = "/categories")
public class CategoryController implements CrudController<Category> {

    private CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    // READ All Categories
    public ResponseEntity<Object> getAll() {
        List<Category> categoryList = categoryService.getAll();
        return ResponseHandler.generateResponse(HttpStatus.OK, true, "/categories",
                "All Categories fetched successfully.", categoryList);
    }

    // READ ALL Categories by Page and Size
    public ResponseEntity<Object> getAll(int page, int size) {
        List<Category> categoryList = categoryService.getAll(page, size);
        return ResponseHandler.generateResponse(HttpStatus.OK, true,
                String.format("/categories?page=%d&size=%d", page, size),
                String.format("All Category (page %d - size %d) fetched successfully.", page, size),
                categoryList);
    }


    // READ One Category
    public ResponseEntity<Object> getOne(@PathVariable int id) {
        Category category = categoryService.getOne(id);
        return ResponseHandler.generateResponse(HttpStatus.OK, true,
                "/categories/" + category.getId(),
                String.format("Category %d fetched successfully.", category.getId()),
                category);
    }

    // CREATE One Category
    public ResponseEntity<Object> saveOne(@RequestBody Category category) {
        Category savedOne = categoryService.saveOne(category);
        return ResponseHandler.generateResponse(HttpStatus.CREATED, true,
                "/categories/" + savedOne.getId(),
                String.format("Category %d created successfully.", savedOne.getId()),
                savedOne);
    }

    // UPDATE One Category
    public ResponseEntity<Object> updateOne(@PathVariable int id, @RequestBody Category category) {
        Category updatedOne = categoryService.updateOne(id, category);
        return ResponseHandler.generateResponse(HttpStatus.OK, true,
                "/categories/" + updatedOne.getId(),
                String.format("Category %d updated successfully.", updatedOne.getId()),
                updatedOne);
    }

    // DELETE One Category
    public ResponseEntity<Object> deleteOne(@PathVariable int id) {
        int result = categoryService.deleteOne(id);
        return ResponseHandler.generateResponse(HttpStatus.ACCEPTED, true,
                "/categories/" + result,
                String.format("Category %d deleted successfully", id),
                null);
    }
}
