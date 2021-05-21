package com.rmit.demo.repository;

import com.rmit.demo.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer>, CategoryRepositoryCustom {
}
