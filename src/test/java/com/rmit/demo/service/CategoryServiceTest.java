package com.rmit.demo.service;

import com.rmit.demo.model.Category;
import com.rmit.demo.repository.CategoryRepository;
import org.junit.After;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Autowired
    @InjectMocks
    private CategoryService categoryService;
    private List<Category> categoryList;
    private Category category1;
    private Category category2;
    private Category category3;

    @BeforeEach
    void setUp() {
        categoryList = new ArrayList<>();
        category1 = new Category(1, "Modern Sneaker");
        category2 = new Category(2, "Classic Sneaker");
        category3 = new Category(3, "High-Fashion Sneaker");
        categoryList.add(category1);
        categoryList.add(category2);
        categoryList.add(category3);
    }

    @AfterEach
    void tearDown() {
        category1 = category2 = category3 = null;
        categoryList = null;
    }

    @Test
    @DisplayName("Test Get All Categories")
    void testGetAllCategories() {
        Mockito.when(categoryRepository.findAll()).thenReturn(categoryList);
        List<Category> categoryList1 = categoryService.getAll();
        assertEquals(categoryList1, categoryList);
        Mockito.verify(categoryRepository, Mockito.times(1)).findAll();
    }

    @Test
    @DisplayName("Test Get List of Empty Category")
    void testGetEmptyCategories() {
        Mockito.when(categoryRepository.findAll()).thenReturn(new ArrayList<>());
        List<Category> categoryList1 = categoryService.getAll();
        assertEquals(new ArrayList<>(), categoryList1);
        Mockito.verify(categoryRepository, Mockito.times(1)).findAll();
    }

    @Test
    void GetAll() {
    }

    @Test
    void getOne() {
    }

    @Test
    void saveOne() {
    }

    @Test
    void updateOne() {
    }

    @Test
    void deleteOne() {
    }
}