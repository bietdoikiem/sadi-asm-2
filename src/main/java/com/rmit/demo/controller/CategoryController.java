package com.rmit.demo.controller;
import com.rmit.demo.model.Category;
import com.rmit.demo.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping(path="/categories")
public class CategoryController {

    private CategoryService categoryService;
    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    // READ All Categories
    @RequestMapping(path="", method=RequestMethod.GET)
    public List<Category> getAllCategories() {
        return categoryService.getAllCategories();
    }

    // READ One Category
    @RequestMapping(path="{id}", method=RequestMethod.GET)
    public Category getOneCategory(@PathVariable int id) {
        return categoryService.getOneCategory(id);
    }

    // CREATE One Category
    @RequestMapping(path="", method=RequestMethod.POST)
    public int saveCategory(@RequestBody Category category) {
        return categoryService.saveCategory(category);
    }

    // UPDATE One Category
    @RequestMapping(path="{id}", method=RequestMethod.PUT)
    public int updateCategory(@PathVariable int id, @RequestBody Category category) {
        return categoryService.updateCategory(id, category);
    }

    // DELETE One Category
    @RequestMapping(path="{id}", method=RequestMethod.DELETE)
    public int deleteCategory(@PathVariable int id) {
        return categoryService.deleteCategory(id);
    }
}
