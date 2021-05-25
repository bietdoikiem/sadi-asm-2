package com.rmit.demo.service;

import com.rmit.demo.model.Category;
import com.rmit.demo.repository.CategoryRepository;
import org.checkerframework.checker.units.qual.C;
import org.junit.After;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


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
    @DisplayName("Test GET All Categories")
    void testGetAllCategories() {
        Mockito.when(categoryRepository.findAll()).thenReturn(categoryList);
        List<Category> categoryList1 = categoryService.getAll();
        assertEquals(categoryList1, categoryList);
        Mockito.verify(categoryRepository, Mockito.times(1)).findAll();
    }

    @Test
    @DisplayName("Test GET List of Empty Category")
    void testGetEmptyCategories() {
        Mockito.when(categoryRepository.findAll()).thenReturn(new ArrayList<>());
        List<Category> categoryList1 = categoryService.getAll();
        assertEquals(new ArrayList<>(), categoryList1);
        Mockito.verify(categoryRepository, Mockito.times(1)).findAll();
    }

    @Test
    @DisplayName("Test GET One Category by Id")
    void testGetOne() {
        int id = 1;
        Mockito.when(categoryRepository.findById(id)).thenReturn(Optional.of(category1));
        Category foundCategory = categoryService.getOne(id);
        assertEquals(foundCategory, category1);
    }

    @Test
    @DisplayName("Test Fail GET One Category by Invalid Id")
    void testFailGetOne() {
        int id = 1;
        int invalidId=99;
        Mockito.when(categoryRepository.findById(invalidId)).thenReturn(null);
        assertThrows(NullPointerException.class, () -> categoryService.getOne(invalidId));
    }

    @Test
    @DisplayName("Test Success CREATE method for Category")
    void testSaveOne() {
        Mockito.when(categoryRepository.saveAndReset(category1)).thenReturn(category1);
        Category savedCategory = categoryService.saveOne(category1);
        assertEquals(category1, savedCategory);
    }

    @Test
    @DisplayName("Test Success UPDATE method for Category")
    void testUpdateOne() {
        // Mocked object
        Category updatedOne = new Category(1, "Updated Sneaker");
        Mockito.when(categoryRepository.findById(updatedOne.getId())).thenReturn(Optional.of(category1));
        Mockito.when(categoryRepository.saveAndReset(category1)).thenReturn(updatedOne);
        Category result = categoryService.updateOne(1, updatedOne);
        assertEquals(result.getName(), updatedOne.getName());
    }

    @Test
    @DisplayName("Test Failed UPDATE method for Category")
    void testFailUpdateOne() {
        // Mocked
        int validId = 1;
        int invalidId = 99;
        Category updatedOne = new Category(invalidId, "Updated Sneaker");
        Mockito.lenient().when(categoryRepository.findById(ArgumentMatchers.eq(validId))).thenReturn(Optional.of(category1));
        Mockito.lenient().when(categoryRepository.saveAndReset(category1)).thenReturn(updatedOne);
        assertThrows(NullPointerException.class, () -> categoryService.updateOne(invalidId, updatedOne));
    }

    @Test
    @DisplayName("Test Success DELETE method for Category")
    void testDeleteOne() {
        int validId = 1;
        doReturn(Optional.of(category1)).when(categoryRepository).findById(validId);
        doAnswer(i -> {return null;}).when(categoryRepository).delete(category1);
        int result = categoryService.deleteOne(validId);
        assertEquals(validId, result);
    }

    @Test
    @DisplayName("Test Fail DELETE method for Category with invalid Id")
    void testFailDeleteOne() {
        int validId = 1;
        int invalidId = 2;
        doReturn(Optional.of(category1)).when(categoryRepository).findById(validId);
        doAnswer(i -> {return null;}).when(categoryRepository).delete(category1);
        int result = categoryService.deleteOne(validId);
        assertEquals(validId, result);
        assertThrows(NullPointerException.class, () -> categoryService.deleteOne(invalidId));
    }
}