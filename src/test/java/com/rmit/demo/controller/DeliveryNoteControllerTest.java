package com.rmit.demo.controller;

import com.fasterxml.jackson.annotation.JacksonInject;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rmit.demo.config.RestExceptionHandler;
import com.rmit.demo.model.*;
import com.rmit.demo.repository.CategoryRepository;
import com.rmit.demo.repository.DeliveryDetailRepository;
import com.rmit.demo.repository.DeliveryNoteRepository;
import com.rmit.demo.repository.StaffRepository;
import com.rmit.demo.service.CategoryService;
import com.rmit.demo.service.DeliveryNoteService;
import com.rmit.demo.utils.DateUtils;
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
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.util.*;

import static org.hamcrest.Matchers.in;
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

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class DeliveryNoteControllerTest {

    @MockBean
    protected DeliveryNoteService deliveryNoteService;

    @MockBean
    protected DeliveryNoteRepository deliveryNoteRepository;


    @Autowired
    @InjectMocks
    protected DeliveryNoteController deliveryNoteController;

    protected MockMvc mockMvc;

    // Defined Mock Objects
    protected DeliveryNote deliveryNote1;
    protected DeliveryNote deliveryNote2;
    protected Staff staff1;
    protected Staff staff2;
    protected List<DeliveryNote> deliveryNoteList;

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
        // Initialize MockMVC test handler
        mockMvc = MockMvcBuilders
                .standaloneSetup(deliveryNoteController)
                .setControllerAdvice(new RestExceptionHandler())
                .build();
        Mockito.reset();
        // Define mock objects
        staff1 = new Staff(1, "Minh Ng", "23 Jump Street",
                "0934717924", "s3812649@rmit.edu.vn");
        staff2 = new Staff(2, "Kha Bui", "06 Dance Street",
                "0702599925", "s3812669@rmit.edu.vn");
        Date date1 = DateUtils.parseDate("25-05-2021");
        Date date2 = DateUtils.parseDate("28-05-2021");
        deliveryNote1 = new DeliveryNote(1, date1, staff1);
        deliveryNote2 = new DeliveryNote(2, date2, staff2);
    }

    @AfterEach
    void tearDown() {
        staff1 = staff2 = null;
        deliveryNote1 = deliveryNote2 = null;
    }

    @Test
    @DisplayName("Test Success GET All DeliveryNote list")
    void testGetAll() throws Exception {
        // Mocked List
        List<DeliveryNote> deliveryNoteList = new ArrayList<>();
        deliveryNoteList.add(deliveryNote1);
        deliveryNoteList.add(deliveryNote2);
        // Mock service to return mocked objects
        Mockito.when(deliveryNoteService.getAll()).thenReturn(deliveryNoteList);
        // MockMvc Http test
        mockMvc.perform(get("/delivery-notes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isSuccess", is(true)))
                .andExpect(jsonPath("$.data", hasSize(2)))
                .andExpect(jsonPath("$.data[0].id", is(1)))
                .andExpect(jsonPath("$.data[1].id", is(2)))
                .andReturn();
    }
    @Test
    @DisplayName("Test Success GET All Empty List of DeliveryNote")
    void testEmptyGetAll() throws Exception {
        // MockMvc Http test
        mockMvc.perform(get("/delivery-notes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isSuccess", is(true)))
                .andExpect(jsonPath("$.data", hasSize(0)));
    }

    @Test
    @DisplayName("Test Success GET All DeliveryNote by Pagination")
    void testPaginationGetAll() throws Exception {
        // Mocked list
        List<DeliveryNote> deliveryNoteList = new ArrayList<>();
        deliveryNoteList.add(deliveryNote1);
        deliveryNoteList.add(deliveryNote2);
        // Mock service to return mocked objects
        Mockito.when(deliveryNoteService.getAll(0, 1)).thenReturn(deliveryNoteList.subList(0, 1));
        Mockito.when(deliveryNoteService.getAll(1, 1)).thenReturn(deliveryNoteList.subList(1, 2));
        // Mock Mvc Http Test
        // Test retrieve first page (index 0) with size 1
        mockMvc.perform(get("/delivery-notes")
        .contentType(MediaType.APPLICATION_JSON)
        .param("page", "0")
        .param("size", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isSuccess", is(true)))
                .andExpect(jsonPath("$.data", hasSize(1)))
                .andExpect(jsonPath("$.data[0].id", is(1)));

        // Test retrieve second page (index 1) with size 1
        mockMvc.perform(get("/delivery-notes")
                .contentType(MediaType.APPLICATION_JSON)
                .param("page", "1")
                .param("size", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isSuccess", is(true)))
                .andExpect(jsonPath("$.data", hasSize(1)))
                .andExpect(jsonPath("$.data[0].id", is(2)));

    }

    @Test
    @DisplayName("Test Fail GET All DeliveryNote by Pagination due to invalid page and size input")
    void testFailPaginationGetAll() throws Exception {
        int invalidPage = -1;
        int invalidSize = -1;
        // Mocked list
        List<DeliveryNote> deliveryNoteList = new ArrayList<>();
        deliveryNoteList.add(deliveryNote1);
        deliveryNoteList.add(deliveryNote2);
        // Mock service to return mocked objects
        Mockito.when(deliveryNoteService.getAll(-1, -1)).thenThrow(new IllegalArgumentException());
        // Mock Mvc Http Test
        // Test retrieve first page (index 0) with size 1
        mockMvc.perform(get("/delivery-notes").param("page", String.format("%d", invalidPage)).param("size", String.format("%d", invalidSize)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Test Success GET one DeliveryNote by Id")
    void testGetOne() throws Exception{
        int validId = 1;
        // Mock service to return mocked data
        Mockito.when(deliveryNoteService.getOne(validId)).thenReturn(deliveryNote1);
        // MockMvc HTTP Test
        mockMvc.perform(get("/delivery-notes/{id}", validId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id", is(1)));
    }

    @Test
    @DisplayName("Test Failed GET one DeliveryNote by invalid Id")
    void testFailGetOne() throws Exception {
        int invalidId = 99;
        int validId = 1;
        // Mock service to return mocked data
        Mockito.when(deliveryNoteService.getOne(validId)).thenReturn(deliveryNote1);
        Mockito.when(deliveryNoteService.getOne(invalidId)).thenThrow(new NullPointerException());

        // MockMvc HTTP Test
        // Mock valid DeliveryNote assertion
        mockMvc.perform(get("/delivery-notes/{id}", validId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id", is(1)));
        // Mock Not found DeliveryNote assertion
        mockMvc.perform(get("/delivery-notes/{id}", invalidId))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Test Success CREATE One DeliveryNote")
    void testSaveOne() throws Exception {
        // Prepare Mocked data
        Category category = new Category(1, "Classic Sneaker");
        Product product1 = new Product(1, "Nike Air Force 1", "Air Force", "Nike", "Nike Co.", "Best Classic Sneaker", 200, category);
        Product product2 = new Product(2, "Nike Mamba 9X", "Mamba", "Nike", "Nike Co.", "Best Nike Of All Time", 250, category);
        List<DeliveryDetail> deliveryDetailList = new ArrayList<>();
        deliveryDetailList.add(new DeliveryDetail(product1, 2));
        deliveryDetailList.add(new DeliveryDetail(product2, 1));
        // Prepare Json Request
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("date", "25-05-2021");
        requestBody.put("staff", staff1);
        requestBody.put("deliveryDetailList", deliveryDetailList);
        String requestJson = asJsonString(requestBody);
        // Mock service to save mocked data
        Mockito.when(deliveryNoteService.saveOne(isA(DeliveryNote.class))).thenReturn(deliveryNote1);
        // MockMvc HTTP Test
        mockMvc.perform(post("/delivery-notes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.id", is(1)));
    }

    @Test
    @DisplayName("Test Fail CREATE One with Null Staff ID")
    void testFailSaveOne() throws Exception {
        // Prepare Mocked data
        Category category = new Category(1, "Classic Sneaker");
        Product product1 = new Product(1, "Nike Air Force 1", "Air Force", "Nike", "Nike Co.", "Best Classic Sneaker", 200, category);
        Product product2 = new Product(2, "Nike Mamba 9X", "Mamba", "Nike", "Nike Co.", "Best Nike Of All Time", 250, category);
        List<DeliveryDetail> deliveryDetailList = new ArrayList<>();
        deliveryDetailList.add(new DeliveryDetail(product1, 2));
        deliveryDetailList.add(new DeliveryDetail(product2, 1));
        // Prepare Json Request
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("date", "25-06-2001");
        requestBody.put("staff", null);
        requestBody.put("deliveryDetailList", deliveryDetailList);
        String requestJson = asJsonString(requestBody);

        // MockMvc HTTP Test
        mockMvc.perform(post("/delivery-notes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Test Success UPDATE for DeliveryNote (Date)")
    void testUpdateOne() throws Exception {
        int validId = 1;
        // Prepare Json Request
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("date", "20-06-2021");
        requestBody.put("staff", staff1);
        String requestJson = asJsonString(requestBody);
        // Mock data when requesting service
        Mockito.lenient().when(deliveryNoteRepository.findById(validId)).thenReturn(Optional.of(deliveryNote1));
        // Update DeliveryNote1 by Date
        deliveryNote1.setDate(DateUtils.parseDate("20-05-2021"));
        Mockito.lenient().when(deliveryNoteService.updateOne(intThat(id -> id == validId), isA(DeliveryNote.class))).thenReturn(deliveryNote1);
        // MockMvc HTTP Test
        mockMvc.perform(put("/delivery-notes/{id}",validId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Test Fail UPDATE for DeliveryNote (Invalid ID)")
    void testFailUpdateOne() throws Exception {
        int validId = 1;
        int invalidId = 99;
        // Mock Data
        // Prepare Mocked data
        Category category = new Category(1, "Classic Sneaker");
        // Prepare Json Request
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("date", "20-06-2001");
        requestBody.put("staff", staff1);
        String requestJson = asJsonString(requestBody);
        // Mock data when requesting service
        Mockito.lenient().when(deliveryNoteRepository.findById(validId)).thenReturn(Optional.of(deliveryNote1));
        // Update DeliveryNote1 by Date
        deliveryNote1.setDate(DateUtils.parseDate("20-05-2021"));
        Mockito.lenient().when(deliveryNoteService.updateOne(intThat(id -> id == invalidId), isA(DeliveryNote.class))).thenReturn(deliveryNote1);
        // MockMvc HTTP Test
        mockMvc.perform(put("/delivery-notes/{id}",validId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Test Success DELETE Method")
    void testDeleteOne() throws Exception {
        int validId = 1;
        // Mock data when request service and repository
        Mockito.lenient().when(deliveryNoteRepository.findById(validId)).thenReturn(Optional.of(deliveryNote1));
        Mockito.lenient().when(deliveryNoteService.deleteOne(validId)).thenReturn(validId);
        // MockMVC HTTP Test
        mockMvc.perform(delete("/delivery-notes/{id}", validId))
                .andExpect(status().isAccepted());

    }
    @Test
    @DisplayName("Test Fail DELETE Method (Invalid Id)")
    void testFailDeleteOne() throws Exception {
        int validId = 1;
        int invalidId = 99;
        // Mock data when request service and repository
        Mockito.when(deliveryNoteService.deleteOne(validId)).thenReturn(validId);
        Mockito.when(deliveryNoteService.deleteOne(invalidId)).thenThrow(new NullPointerException());

        // MockMVC HTTP Test
        mockMvc.perform(delete("/delivery-notes/{id}", validId))
                .andExpect(status().isAccepted());

        // MockMVC HTTP Fail Test
        mockMvc.perform(delete("/delivery-notes/{id}", invalidId))
                .andExpect(status().isNotFound());

    }

    @Test
    @DisplayName("Test Success GET All Delivery Details by DeliveryNote Id")
    void testGetDeliveryDetailsByDeliveryNoteId() throws Exception {
        int validId = 1;
        // Prepare Mocked data
        Category category = new Category(1, "Classic Sneaker");
        Product product1 = new Product(1, "Nike Air Force 1", "Air Force", "Nike", "Nike Co.", "Best Classic Sneaker", 200, category);
        Product product2 = new Product(2, "Nike Mamba 9X", "Mamba", "Nike", "Nike Co.", "Best Nike Of All Time", 250, category);
        List<DeliveryDetail> deliveryDetailList = new ArrayList<>();
        deliveryDetailList.add(new DeliveryDetail(1, deliveryNote1, product1, 2));
        deliveryDetailList.add(new DeliveryDetail(2, deliveryNote1, product2, 1));
        // Mock Request
        Mockito.when(deliveryNoteService.getAllDeliveryDetailsByDeliveryNoteId(validId)).thenReturn(deliveryDetailList);
        // MockMVC HTTP Test
        mockMvc.perform(get("/delivery-notes/{id}/delivery-details", validId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", hasSize(2)))
                .andExpect(jsonPath("$.data[0].id", is(1)))
                .andExpect(jsonPath("$.data[1].id", is(2)));
    }

    @Test
    @DisplayName("Test Fail GET All Delivery Details by Invalid DeliveryNote Id")
    void testFailGetDeliveryDetailsByDeliveryNoteId() throws Exception {
        int invalidId = 99;
        // Prepare Mocked data
        Category category = new Category(1, "Classic Sneaker");
        Product product1 = new Product(1, "Nike Air Force 1", "Air Force", "Nike", "Nike Co.", "Best Classic Sneaker", 200, category);
        Product product2 = new Product(2, "Nike Mamba 9X", "Mamba", "Nike", "Nike Co.", "Best Nike Of All Time", 250, category);
        List<DeliveryDetail> deliveryDetailList = new ArrayList<>();
        deliveryDetailList.add(new DeliveryDetail(1, deliveryNote1, product1, 2));
        deliveryDetailList.add(new DeliveryDetail(2, deliveryNote1, product2, 1));
        // Mock Request
        Mockito.when(deliveryNoteService.getAllDeliveryDetailsByDeliveryNoteId(invalidId)).thenThrow(new NullPointerException());
        // MockMVC HTTP Test
        mockMvc.perform(get("/delivery-notes/{id}/delivery-details", invalidId))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Test Success GET All DeliveryNote in a period")
    void testGetDeliveryNoteByPeriod() throws Exception {
        // Prepare Mocked Object
        List<DeliveryNote> deliveryNoteList = new ArrayList<>();
        deliveryNoteList.add(deliveryNote1);
        deliveryNoteList.add(deliveryNote2);
        // Prepare Mock Request
        Mockito.when(deliveryNoteService.filterByPeriod(DateUtils.parseDate("25-05-2021"),
                DateUtils.parseDate("28-05-2021"))).thenReturn(deliveryNoteList);
        Mockito.when(deliveryNoteService.filterByPeriod(DateUtils.parseDate("25-05-2021"),
                DateUtils.parseDate("27-05-2021"))).thenReturn(deliveryNoteList.subList(0, 1));
        // MockMVC HTTP Test
        mockMvc.perform(get("/delivery-notes/filter")
                        .param("startDate", "25-05-2021")
                        .param("endDate", "28-05-2021"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", hasSize(2)));
        mockMvc.perform(get("/delivery-notes/filter")
                .param("startDate", "25-05-2021")
                .param("endDate", "27-05-2021"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", hasSize(1)));
    }

    @Test
    @DisplayName("Test Empty GET All DeliveryNote in a period without any item")
    void testEmptyGetDeliveryNoteByPeriod() throws Exception {
        // Prepare Mocked Object
        List<DeliveryNote> deliveryNoteList = new ArrayList<>();
        // Prepare Mock Request
        Mockito.when(deliveryNoteService.filterByPeriod(DateUtils.parseDate("20-03-2021"), DateUtils.parseDate("28-03-2021"))).thenReturn(deliveryNoteList);
        // MockMVC HTTP Test
        mockMvc.perform(get("/delivery-notes/filter")
                .param("startDate", "20-03-2021")
                .param("endDate", "28-03-2021"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", hasSize(0)));
    }

}