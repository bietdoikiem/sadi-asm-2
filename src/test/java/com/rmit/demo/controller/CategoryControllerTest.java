package com.rmit.demo.controller;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rmit.demo.model.Category;
import com.rmit.demo.repository.CategoryRepository;
import com.rmit.demo.service.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(CategoryController.class)
@AutoConfigureMockMvc
class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CategoryService categoryService;

    @MockBean
    private CategoryRepository categoryRepository;

    @Test
    @DisplayName("Test GET all Category as a list")
    void testGetAll() throws Exception {
        List<Category> categoryList = new ArrayList<>();
        categoryList.add(new Category(1, "Classic Sneaker"));
        categoryList.add(new Category(2, "Modern Sneaker"));

        Mockito.when(categoryService.getAll()).thenReturn(categoryList);

        String url = "/categories";
        mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", hasSize(2)))
                .andExpect(jsonPath("$.data[0].id", is(1)))
                .andExpect(jsonPath("$.data[0].name", is("Classic Sneaker")))
                .andExpect(jsonPath("$.data[1].id", is(2)))
                .andExpect(jsonPath("$.data[1].name", is("Modern Sneaker")))
                .andReturn();
    }

    @Test
    @DisplayName("Test GET empty Category list")
    void testGetAllEmpty() throws Exception {
        List<Category> categoryList = new ArrayList<>();

        Mockito.when(categoryService.getAll()).thenReturn(categoryList);

        String url = "/categories";
        mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", hasSize(0)));
    }

    @Test
    @DisplayName("Test GET one Category by Id")
    void testGetOne() throws Exception {
        Category category = new Category(1, "Classic Sneaker");

        Mockito.when(categoryService.getOne(1)).thenReturn(category);

        String url = "/categories/1";
        mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id", is(1)))
                .andExpect(jsonPath("$.data.name", is("Classic Sneaker")));
    }

    @Test
    @DisplayName("Test GET Null Resource by Unknown ID")
    void testGetOneEmpty() throws Exception {
        Mockito.when(categoryService.getOne(2222)).thenReturn(null);
        String url ="/categories/2222";
        mockMvc.perform(get(url))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Test ADD One Category")
    void testSaveOne() throws Exception {
        Map<String, Object> body = new HashMap<>();
        body.put("name", "Classic Sneaker Dope");


        String url = "/categories";
        Mockito.when(categoryService.saveOne(any(Category.class))).
                thenReturn(new Category(1, "Dope Classic Sneaker"));

        mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Test Bad Request with wrong response format (redundant ID)")
    void testSaveOneError() throws Exception {
        Map<String, Object> body = new HashMap<>();
        // Redundant Id to request body for creating new Category
        body.put("id", 1);
        body.put("name", "Dope Classic Sneaker");

        Mockito.when(categoryService.saveOne(any(Category.class)))
                .thenReturn(new Category(1, "Dope Classic Sneaker"));

        String url ="/categories";
        mockMvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().isBadRequest());
    }


}