package com.rmit.demo.repository;

import com.rmit.demo.model.Category;

public interface CategoryRepositoryCustom {
    Category saveAndReset(Category category);
}
