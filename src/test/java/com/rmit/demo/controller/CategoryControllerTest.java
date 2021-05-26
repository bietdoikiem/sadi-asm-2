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
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.util.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class CategoryControllerTest {


    @MockBean
    protected CategoryService categoryService;
    @MockBean
    protected CategoryRepository categoryRepository;

    @Autowired
    @InjectMocks
    protected CategoryController categoryController;

    protected MockMvc mockMvc;

    // Convert To JSON string func
    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(categoryController)
                .setControllerAdvice(new RestExceptionHandler())
                .build();
        Mockito.reset();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    @DisplayName("Test GET all Category list")
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
    @DisplayName("Test CREATE One Category")
    void testSaveOne() throws Exception {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("name", "Classic Sneaker Dope");
        String requestJson = asJsonString(requestBody);

        String url = "/categories";
        Mockito.when(categoryService.saveOne(isA(Category.class))).
                thenReturn(new Category(1, "Classic Sneaker Dope"));

        mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Test CREATE method Bad Request with wrong response format (wrong fieldname)")
    void testSaveOneError() throws Exception {
        Map<String, Object> requestBody = new HashMap<>();
        // Redundant Id to Request Body for creating new Category
        requestBody.put("id", 1);
        requestBody.put("vipProMUName", "Dope Classic Sneaker");

        String requestJson = asJsonString(requestBody);
        Category category = new Category(1, "Dope Classic Sneaker");

        // Request Object

        Mockito.lenient().when(categoryService.saveOne(category))
                .thenReturn(category);

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
        Mockito.lenient().when(categoryRepository.findById(id)).thenReturn(Optional.of(category));
        Mockito.lenient().when(categoryService.updateOne(intThat(i -> i == id), isA(Category.class))).thenReturn(updatedCategory);
        mockMvc.perform(put("/categories/{id}", category.getId()).contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Test failed UPDATE method for Category when input invalid Category's Id")
    void testFailUpdateOne() throws Exception {
        int validId = 1;
        int invalidId = 2;
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("name", "Future Sneaker");
        String requestJson = asJsonString(requestBody);
        // Mocked Object
        Category category = new Category(1, "Modern Sneaker");
        Category updatedCategory = new Category(1, "Future Sneaker");
        // Mocked response
        Mockito.lenient().when(categoryRepository.findById(validId)).thenReturn(Optional.of(category)); // valid object
        Mockito.lenient().when(categoryService.updateOne(intThat(id -> id == validId), isA(Category.class))).thenReturn(updatedCategory); // mock update request
        Mockito.lenient().when(categoryService.updateOne(intThat(id -> id == invalidId), isA(Category.class))).thenThrow(new NullPointerException()); // mock update request
        // Perform Operation on valid entity successfully
        mockMvc.perform(put("/categories/{id}", validId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson)
        ).andExpect(status().isOk());
        // Update Operation fail if invalid Id is provided
        mockMvc.perform(put("/categories/{id}", invalidId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson)
        ).andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Test Success DELETE Method")
    void testDeleteOne() throws Exception {
        int id = 1;
        Category category = new Category(id, "Classic Sneaker");

        Mockito.lenient().when(categoryRepository.findById(id)).thenReturn(Optional.of(category));
        Mockito.lenient().when(categoryService.deleteOne(id)).thenReturn(id);

        mockMvc.perform(delete("/categories/{id}", category.getId()))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.url", is("/categories/" + category.getId())));
    }

    @Test
    @DisplayName("Test Failed DELETE method for invalid id")
    void testFailDeleteOne() throws Exception {
        int validId = 1;
        int invalidId = 2;
        Category category = new Category(validId, "Classic Sneaker");

        // Return successfully if return valid id
        Mockito.when(categoryService.deleteOne(validId)).thenReturn(validId);
        Mockito.when(categoryService.deleteOne(invalidId)).thenThrow(new NullPointerException());

        // If retrieved valid Id, the operation is successful
        mockMvc.perform(delete("/categories/{id}", validId))
                .andExpect(status().isAccepted());
        // Else fail DELETE operation
        mockMvc.perform(delete("/categories/{id}", invalidId))
                .andExpect(status().isNotFound());
    }


}