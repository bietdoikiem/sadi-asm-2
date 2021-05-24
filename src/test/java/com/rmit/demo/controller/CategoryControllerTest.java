package com.rmit.demo.controller;

import com.fasterxml.jackson.annotation.JacksonInject;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rmit.demo.config.RestExceptionHandler;
import com.rmit.demo.model.Category;
import com.rmit.demo.model.Product;
import com.rmit.demo.repository.CategoryRepository;
import com.rmit.demo.service.CategoryService;
import org.checkerframework.checker.units.qual.A;
import org.hibernate.service.spi.InjectService;
import org.junit.After;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityManager;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
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

@ExtendWith(MockitoExtension.class)
class CategoryControllerTest {


    @Mock
    protected CategoryService categoryService;
    protected Category category;
    protected List<Category> categoryList;

    @InjectMocks
    protected CategoryController categoryController;

    @Autowired
    protected MockMvc mockMvc;

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @BeforeEach
    void setUp() {
        category = new Category(1, "Modern Sneaker");
        mockMvc = MockMvcBuilders
                .standaloneSetup(categoryController)
                .setControllerAdvice(new RestExceptionHandler())
                .build();
        Mockito.reset();
    }

    @AfterEach
    void tearDown() {
        category = null;
    }

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
        MvcResult mvcResult = mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", hasSize(0)))
                .andReturn();
    }

    @Test
    @DisplayName("Test GET one Category by Id")
    void testGetOne() throws Exception {
        int id = 1;
        Category category = new Category(id, "Classic Sneaker");

        Mockito.when(categoryService.getOne(id)).thenReturn(category);

        String url = "/categories/{id}";
        mockMvc.perform(get(url, id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id", is(id)))
                .andExpect(jsonPath("$.data.name", is("Classic Sneaker")));
    }

    @Test
    @DisplayName("Test GET Null Resource by Unknown ID")
    void testGetOneEmpty() throws Exception {
        int id = 5;
        Mockito.when(categoryService.getOne(5)).thenReturn(null);
        String url = "/categories/{id}";
        mockMvc.perform(get(url, 5))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Test ADD One Category")
    void testSaveOne() throws Exception {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("name", "Classic Sneaker Dope");
        String requestJson = asJsonString(requestBody);

        String url = "/categories";
        Mockito.when(categoryService.saveOne(any(Category.class))).
                thenReturn(new Category(1, "Classic Sneaker Dope"));

        mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Test Bad Request with wrong response format (redundant ID)")
    void testSaveOneError() throws Exception {
        Map<String, Object> requestBody = new HashMap<>();
        // Redundant Id to Request Body for creating new Category
        requestBody.put("id", 1);
        requestBody.put("name", "Dope Classic Sneaker");

        String requestJson = asJsonString(requestBody);
        Category category = new Category(1, "Dope Classic Sneaker");

        // Request Object

        Mockito.when(categoryService.saveOne(category))
                .thenThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST));

        String url = "/categories";
        mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @DisplayName("Test success UPDATE method for Category")
    void testUpdateOne() throws Exception {
        int id = 1;
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("name", "Future Sneaker");
        String requestJson = asJsonString(requestBody);
        // Mocked Request Object
        Category category = new Category(id, "Modern Sneaker");
        Category updatedCategory = new Category(id, "Future Sneaker");
        // Mocked responses
        Mockito.lenient().when(categoryService.getOne(anyInt())).thenReturn(category);
        Mockito.lenient().when(categoryService.updateOne(anyInt(), any(Category.class))).thenReturn(updatedCategory);
        mockMvc.perform(put("/categories/{id}", category.getId()).contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Test Success Delete Method")
    void testDeleteOne() throws Exception {
        int id = 1;
        Category category = new Category(id, "Classic Sneaker");

        Mockito.lenient().when(categoryService.getOne(anyInt())).thenReturn(category);
        Mockito.lenient().when(categoryService.deleteOne(anyInt())).thenReturn(1);

        mockMvc.perform(delete("/categories/{id}", category.getId()))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.url", is("/categories/" + category.getId())));
    }


}