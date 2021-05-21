package com.rmit.demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rmit.demo.service.CategoryService;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = CategoryController.class)
class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CategoryService categoryService;

    @Test
    void getAll() {
    }

    @Test
    void testGetAll() {
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