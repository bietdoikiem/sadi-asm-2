package com.rmit.demo.service;

import com.rmit.demo.model.Category;
import com.rmit.demo.model.Product;
import com.rmit.demo.repository.CategoryRepository;
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
public class CategoryService implements CrudService<Category> {

    private CategoryRepository categoryRepository;
    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public CategoryService() {}


    // READ All Categories
    public List<Category> getAll() {
        var it = categoryRepository.findAll();

        return new ArrayList<Category>(it);
    }

    // READ ALL Category by Pagination
    public List<Category> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Category> allCategories = categoryRepository.findAll(pageable);
        if (allCategories.hasContent()) {
            return allCategories.getContent();
        }
        return new ArrayList<Category>();
    }

    // READ One Category
    public Category getOne(int id) {
        System.out.println("Save method Called");
        return categoryRepository.findById(id).orElseThrow(NullPointerException::new);
    }

    // CREATE One Category
    public Category saveOne(Category category) {
        return categoryRepository.saveAndReset(category);
    }

    // UPDATE One Category
    public Category updateOne(int categoryId, Category category) {
        Category c = this.getOne(categoryId);
        c.setName(category.getName());
        categoryRepository.saveAndReset(c);
        return c;
    }

    // DELETE One Category
    public int deleteOne(int categoryId) {
        Category c = this.getOne(categoryId);
        categoryRepository.delete(c);
        for (Product product: c.getProducts()) {
            product.setCategory(null);
        }
        return c.getId();
    }
}
