package com.rmit.demo.repository.impl;


import com.rmit.demo.model.Category;
import com.rmit.demo.repository.CategoryRepository;
import com.rmit.demo.repository.CategoryRepositoryCustom;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class CategoryRepositoryImpl implements CategoryRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private CategoryRepository categoryRepository;

    public Category saveAndReset(Category category) {
        // SAVE
        Category saved = categoryRepository.saveAndFlush(category);
        // RESET
        em.clear();
        // RETURN
        return categoryRepository.findById(saved.getId()).orElse(null);
    }
}
