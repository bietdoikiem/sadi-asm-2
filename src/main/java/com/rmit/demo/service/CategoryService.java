package com.rmit.demo.service;
import com.rmit.demo.model.Category;
import com.rmit.demo.model.Product;
import com.rmit.demo.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    // READ All Categories
    public List<Category> getAllCategories() {
        var it = categoryRepository.findAll();
        var categories = new ArrayList<Category>();

        it.forEach(categories::add);

        return categories;
    }
    // READ One Category
    public Category getOneCategory(int id) {
        return categoryRepository.findById(id).orElse(null);
    }

    // CREATE One Category
    public int saveCategory(Category category) {
        categoryRepository.save(category);
        return category.getId();
    }

    // UPDATE One Category
    public int updateCategory(int categoryId, Category category) {
        Category c = categoryRepository.findById(categoryId).orElse(null);
        if (c != null) {
            c.setAll(category);
            categoryRepository.save(c);
            return categoryId;
        }
        return -1;
    }

    // DELETE One Category
    public int deleteCategory(int categoryId) {
         Category c = categoryRepository.findById(categoryId).orElse(null);
        if (c != null) {
            for (Product product: c.getProducts()) {
                product.setCategory(null);
            }
            categoryRepository.delete(c);
            return categoryId;
        }
        return -1;
    }
}
