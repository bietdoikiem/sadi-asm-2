package com.rmit.demo.controller;

import com.fasterxml.jackson.annotation.JacksonInject;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rmit.demo.config.RestExceptionHandler;
import com.rmit.demo.model.*;
import com.rmit.demo.repository.CategoryRepository;
import com.rmit.demo.repository.SaleDetailRepository;
import com.rmit.demo.repository.SaleInvoiceRepository;
import com.rmit.demo.repository.StaffRepository;
import com.rmit.demo.service.CategoryService;
import com.rmit.demo.service.DeliveryNoteService;
import com.rmit.demo.service.SaleInvoiceService;
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
class SaleInvoiceControllerTest {

    @MockBean
    protected SaleInvoiceService saleInvoiceService;
    @MockBean
    protected SaleInvoiceRepository saleInvoiceRepository;

    @Autowired
    @InjectMocks
    protected SaleInvoiceController saleInvoiceController;

    protected MockMvc mockMvc;

    // Define Mock Data
    protected SaleInvoice saleInvoice1;
    protected SaleInvoice saleInvoice2;
    protected Staff staff1;
    protected Staff staff2;
    protected Customer customer1;
    protected Customer customer2;
    protected Category category;
    protected Product product1;
    protected Product product2;
    protected SaleDetail saleDetail1;
    protected SaleDetail saleDetail2;
    protected SaleDetail saleDetail3;
    protected SaleDetail saleDetail4;

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
                .standaloneSetup(saleInvoiceController)
                .setControllerAdvice(new RestExceptionHandler())
                .build();
        Mockito.reset();
        // Define mock data
        // Mock Staff
        staff1 = new Staff(1, "Minh Ng", "23 Jump Street",
                "0934717924", "s3812649@rmit.edu.vn");
        staff2 = new Staff(2, "Kha Bui", "06 Dance Street",
                "0702599925", "s3812669@rmit.edu.vn");
        // Mock Customer
        customer1 = new Customer(1, "Minh Cus", "702 Nguyen Van Linh Boulevard",
                "080808088", "rmitstaff@rmit.edu.vn", "8400200", "Dr. Duy");
        customer2 = new Customer(2, "Duy Cus", "702 Nguyen Van Linh Boulevard",
                "080808099", "rmitmanager@rmit.edu.vn", "8400200", "Duy");
        // Mock Product
        category = new Category(1, "Classic Sneaker");
        product1 = new Product(1, "Nike Air Force 1", "Air Force", "Nike", "Nike Co.", "Best Classic Sneaker", 200, category);
        product2 = new Product(2, "Nike Mamba 9X", "Mamba", "Nike", "Nike Co.", "Best Nike Of All Time", 250, category);
        // Mock SaleInvoice
        saleInvoice1 = new SaleInvoice(1, DateUtils.parseDate("26-05-2021"), staff1, customer1, 0);
        saleInvoice2 = new SaleInvoice(2, DateUtils.parseDate("31-05-2021"), staff2, customer2, 0);
        // Mock SaleDetail
        saleDetail1 = new SaleDetail(1, saleInvoice1, product1, 1, 200);
        saleDetail2 = new SaleDetail(2, saleInvoice1, product2, 1, 250);
        saleDetail3 = new SaleDetail(3, saleInvoice2, product1, 2, 400);
        saleDetail4 = new SaleDetail(3, saleInvoice2, product2, 3, 750);
    }

    @AfterEach
    void tearDown() {
        staff1 = staff2 = null;
        customer1 = customer2 = null;
        saleInvoice1 = saleInvoice2 = null;
        saleDetail1 = saleDetail2 = saleDetail3 = saleDetail4 = null;
        product1 = product2 = null;
    }


    @Test
    @DisplayName("Test Success GET All SaleInvoice")
    void testGetAll() throws Exception {
        // Mock SaleInvoice list
        List<SaleInvoice> saleInvoiceList = new ArrayList<>();
        saleInvoiceList.add(saleInvoice1);
        saleInvoiceList.add(saleInvoice2);
        // Mock service to return mock data
        Mockito.when(saleInvoiceService.getAll()).thenReturn(saleInvoiceList);
        // MockMvc HTTP Test
        mockMvc.perform(get("/sale-invoices"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isSuccess", is(true)))
                .andExpect(jsonPath("$.data", hasSize(2)))
                .andExpect(jsonPath("$.data[0].id", is(1)))
                .andExpect(jsonPath("$.data[1].id", is(2)))
                .andReturn();
    }

    @Test
    @DisplayName("Test Empty GET ALL SaleInvoice")
    void testEmptyGetAll() throws Exception {
        // Mock Service// MockMvc HTTP Test
        mockMvc.perform(get("/sale-invoices"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isSuccess", is(true)))
                .andExpect(jsonPath("$.data", hasSize(0)));
    }

    @Test
    @DisplayName("Test Success GET ALL SaleInvoice by Pagination")
    void testPaginationGetAll() throws Exception {
        // Mocked List
        List<SaleInvoice> saleInvoiceList = new ArrayList<>();
        saleInvoiceList.add(saleInvoice1);
        saleInvoiceList.add(saleInvoice2);
        // Mock Service
        Mockito.when(saleInvoiceService.getAll(0, 1)).thenReturn(saleInvoiceList.subList(0, 1));
        Mockito.when(saleInvoiceService.getAll(1, 1)).thenReturn(saleInvoiceList.subList(1, 2));
        // MockMVC HTTP Test
        // Test retrieve first page (index 0) with size 1
        mockMvc.perform(get("/sale-invoices")
                .contentType(MediaType.APPLICATION_JSON)
                .param("page", "0")
                .param("size", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isSuccess", is(true)))
                .andExpect(jsonPath("$.data", hasSize(1)))
                .andExpect(jsonPath("$.data[0].id", is(1)));

        // Test retrieve second page (index 1) with size 1
        mockMvc.perform(get("/sale-invoices")
                .contentType(MediaType.APPLICATION_JSON)
                .param("page", "1")
                .param("size", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isSuccess", is(true)))
                .andExpect(jsonPath("$.data", hasSize(1)))
                .andExpect(jsonPath("$.data[0].id", is(2)));

    }

    @Test
    @DisplayName("Test Fail GET All by Pagination due to invalid paging")
    void testFailPaginationGetAll() throws Exception {
        int invalidPage = -1;
        int invalidSize = -1;
        // Mock List
        List<SaleInvoice> saleInvoiceList = new ArrayList<>();
        saleInvoiceList.add(saleInvoice1);
        saleInvoiceList.add(saleInvoice2);
        // Mock service to return mock data
        Mockito.when(saleInvoiceService.getAll(invalidPage, invalidSize)).thenThrow(new IllegalArgumentException());
        // Mock Mvc Http Test
        // Test retrieve first page (index 0) with size 1
        mockMvc.perform(get("/sale-invoices").param("page", String.format("%d", invalidPage)).param("size", String.format("%d", invalidSize)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Test Success GET one SaleInvoice by Id")
    void testGetOne() throws Exception {
        int validId = 1;
        // Mock service to return mock data
        Mockito.when(saleInvoiceService.getOne(validId)).thenReturn(saleInvoice1);
        // MockMVC HTTP Test
        mockMvc.perform(get("/sale-invoices/{id}", validId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id", is(1)));
    }

    @Test
    @DisplayName("Test Fail GET one SaleInvoice by Invalid Id")
    void testFailGetOne() throws Exception {
        int invalidId = 99;
        int validId = 1;
        // Mock service to return mocked data
        Mockito.when(saleInvoiceService.getOne(validId)).thenReturn(saleInvoice1);
        Mockito.when(saleInvoiceService.getOne(invalidId)).thenThrow(new NullPointerException());
        // MockMvc HTTP Test
        // Mock valid DeliveryNote assertion
        mockMvc.perform(get("/sale-invoices/{id}", validId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id", is(1)));
        // Mock Not found DeliveryNote assertion
        mockMvc.perform(get("/sale-invoices/{id}", invalidId))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Test Success CREATE one SaleInvoice")
    void testSaveOne() throws Exception {
        // Prepare Mock Data
        List<SaleDetail> saleDetailList = new ArrayList<>();
        saleDetailList.add(saleDetail1);
        saleDetailList.add(saleDetail2);
        // Prepare JSON request
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("date", "26-06-2001");
        requestBody.put("staff", staff1);
        requestBody.put("customer", customer1);
        requestBody.put("saleDetailList", saleDetailList);
        String requestJson = asJsonString(requestBody);
        // Mock service to save mock data
        Mockito.when(saleInvoiceService.saveOne(isA(SaleInvoice.class))).thenReturn(saleInvoice1);
        // MockMVC HTTP Test
        mockMvc.perform(post("/sale-invoices")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.id", is(1)));
    }

    @Test
    @DisplayName("Test Fail CREATE One with Null")
    void testFailSaveOne() throws Exception {
        // Prepare Mock Data
        List<SaleDetail> saleDetailList = new ArrayList<>();
        saleDetailList.add(saleDetail1);
        saleDetailList.add(saleDetail2);
        // Prepare Json Request
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("date", "26-06-2001");
        requestBody.put("staff", null);
        requestBody.put("customer", null);
        requestBody.put("saleDetailList", saleDetailList);
        String requestJson = asJsonString(requestBody);
        // MockMVC HTTP Fail Test
        // MockMvc HTTP Test
        mockMvc.perform(post("/sale-invoices")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Test Success UPDATE for SaleInvoice (Date, Staff, Customer)")
    void testUpdateOne() throws Exception {
        int validId = 1;
        // Prepare JSON Request
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("date", "27-06-2021");
        requestBody.put("customer", customer2);
        requestBody.put("staff", staff2);
        String requestJson = asJsonString(requestBody);
        // Mock data when request
        // Update SaleInvoice1 by Date, Staff and Customer
        saleInvoice1.setDate(DateUtils.parseDate("27-06-2021"));
        saleInvoice1.setCustomer(customer2);
        saleInvoice1.setStaff(staff2);
        Mockito.when(saleInvoiceService.updateOne(intThat(id -> id == validId), isA(SaleInvoice.class))).thenReturn(saleInvoice1);
        // MockMvc HTTP Test
        mockMvc.perform(put("/sale-invoices/{id}", validId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id", is(1)))
                .andExpect(jsonPath("$.data.staff.id", is(2)))
                .andExpect(jsonPath("$.data.customer.id", is(2)));
    }

    @Test
    @DisplayName("Test Success GET All SaleDetail by SaleInvoice's Id")
    void testGetSaleDetailsBySaleInvoiceId() throws Exception {
        int validId = 1;
        // Prepare Mock data
        List<SaleDetail> saleDetailList = new ArrayList<>();
        saleDetailList.add(saleDetail1);
        saleDetailList.add(saleDetail2);
        // Mock Request
        Mockito.when(saleInvoiceService.getAllSaleDetailsBySaleInvoice(validId)).thenReturn(saleDetailList);
        // MockMVC HTTP Test
        mockMvc.perform(get("/sale-invoices/{id}/sale-details", validId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", hasSize(2)))
                .andExpect(jsonPath("$.data[0].id", is(1)))
                .andExpect(jsonPath("$.data[1].id", is(2)));
    }

    @Test
    @DisplayName("Test Success GET All SaleDetail by SaleInvoice invalid Id")
    void testFailGetSaleDetailsBySaleInvoiceId() throws Exception {
        int invalidId = 99;
        // Prepare Mock data
        List<SaleDetail> saleDetailList = new ArrayList<>();
        saleDetailList.add(saleDetail1);
        saleDetailList.add(saleDetail2);
        // Mock Request
        Mockito.when(saleInvoiceService.getAllSaleDetailsBySaleInvoice(invalidId)).thenThrow(new NullPointerException());
        // MockMVC HTTP Test
        mockMvc.perform(get("/sale-invoices/{id}/sale-details", invalidId))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Test Success GET All SaleInvoice in a period")
    void testGetAllDeliveryNotesByPeriod() throws Exception {
        // Prepare mocked object
        List<SaleInvoice> saleInvoiceList = new ArrayList<>();
        saleInvoiceList.add(saleInvoice1);
        saleInvoiceList.add(saleInvoice2);
        // Prepare Mock Request
        Mockito.when(saleInvoiceService.filterByPeriod(DateUtils.parseDate("26-05-2021"),
                DateUtils.parseDate("31-05-2021"))).thenReturn(saleInvoiceList);
        Mockito.when(saleInvoiceService.filterByPeriod(DateUtils.parseDate("26-05-2021"),
                DateUtils.parseDate("27-05-2021"))).thenReturn(saleInvoiceList.subList(0, 1));
        Mockito.when(saleInvoiceService.filterByPeriod(DateUtils.parseDate("20-05-2021"),
                DateUtils.parseDate("22-05-2021"))).thenReturn(saleInvoiceList.subList(0, 0));
        // MockMVC HTTP Test (2 SaleInvoice)
        mockMvc.perform(get("/sale-invoices/filter")
                .param("startDate", "26-05-2021")
                .param("endDate", "31-05-2021"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", hasSize(2)));
        // 1 SaleInvoice Test
        mockMvc.perform(get("/sale-invoices/filter")
                .param("startDate", "26-05-2021")
                .param("endDate", "27-05-2021"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", hasSize(1)));
        // Empty Test
        mockMvc.perform(get("/sale-invoices/filter")
                .param("startDate", "20-05-2021")
                .param("endDate", "22-05-2021"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", hasSize(0)));
    }

    @Test
    @DisplayName("Test Success GET all SaleInvoice by Customer and a period")
    void testGetAllSaleInvoicesByCustomerAndPeriod() throws Exception {
        int customerValidId = 1;
        // Prepare Mock Data
        List<SaleInvoice> saleInvoiceList = new ArrayList<>();
        saleInvoiceList.add(saleInvoice1); // SaleInvoice List of Customer 1
        // Prepare Mock Request
        // Mock for 1 SaleInvoice of Customer 1
        Mockito.when(saleInvoiceService.getAllSaleInvoicesByCustomerAndPeriod(
                customerValidId,
                DateUtils.parseDate("26-05-2021"),
                DateUtils.parseDate("31-05-2021")))
                .thenReturn(saleInvoiceList);
        // Mock for empty SaleInvoice of Customer 1
        Mockito.when(saleInvoiceService.getAllSaleInvoicesByCustomerAndPeriod(
                customerValidId,
                DateUtils.parseDate("05-05-2021"),
                DateUtils.parseDate("06-05-2021")))
                .thenReturn(saleInvoiceList.subList(0, 0));
        // MockMVC HTTP Test
        // 1 SaleInvoice by Customer 1 Test
        mockMvc.perform(get("/sale-invoices/by-customer/{id}/filter", customerValidId)
                        .param("startDate", "26-05-2021")
                        .param("endDate", "31-05-2021"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", hasSize(1)));
        // Empty SaleInvoiceTest
        mockMvc.perform(get("/sale-invoices/by-customer/{id}/filter", customerValidId)
                .param("startDate", "05-05-2021")
                .param("endDate", "06-05-2021"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", hasSize(0)));
    }//(int customerId, Date startDate, Date endDate)

    @Test
    @DisplayName("Test Fail GET All SaleInvoice by Customer's Invalid ID and Period")
    void testFailGetAllSaleInvoicesByCustomerAndPeriod() throws Exception {
        int invalidCustomerId = 99;
        // Prepare Mock Request
        Mockito.when(saleInvoiceService.getAllSaleInvoicesByCustomerAndPeriod(invalidCustomerId,
                DateUtils.parseDate("26-05-2021"),
                DateUtils.parseDate("31-05-2021")))
                .thenThrow(new NullPointerException());
        // MockMVC HTTP Test
        mockMvc.perform(get("/sale-invoices/by-customer/{id}/filter", invalidCustomerId)
                .param("startDate", "26-05-2021")
                .param("endDate", "31-05-2021"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Test Success GET ALL SaleInvoice by Staff's Id and Period")
    void testGetAllSaleInvoicesByStaffAndPeriod() throws Exception {
        int staffValidId = 1;
        // Prepare Mock Data
        List<SaleInvoice> saleInvoiceList = new ArrayList<>();
        saleInvoiceList.add(saleInvoice1); // SaleInvoice List of Customer 1
        // Prepare Mock Request
        // Mock for 1 SaleInvoice of Customer 1
        Mockito.when(saleInvoiceService.getAllSaleInvoicesByStaffAndPeriod(
                staffValidId,
                DateUtils.parseDate("26-05-2021"),
                DateUtils.parseDate("31-05-2021")))
                .thenReturn(saleInvoiceList);
        // Mock for empty SaleInvoice of Customer 1
        Mockito.when(saleInvoiceService.getAllSaleInvoicesByStaffAndPeriod(
                staffValidId,
                DateUtils.parseDate("05-05-2021"),
                DateUtils.parseDate("06-05-2021")))
                .thenReturn(saleInvoiceList.subList(0, 0));
        // MockMVC HTTP Test
        // 1 SaleInvoice by Customer 1 Test
        mockMvc.perform(get("/sale-invoices/by-staff/{id}/filter", staffValidId)
                .param("startDate", "26-05-2021")
                .param("endDate", "31-05-2021"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", hasSize(1)));
        // Empty SaleInvoiceTest
        mockMvc.perform(get("/sale-invoices/by-staff/{id}/filter", staffValidId)
                .param("startDate", "05-05-2021")
                .param("endDate", "06-05-2021"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", hasSize(0)));
    }

    @Test
    @DisplayName("Test Fail GET All SaleInvoice by Staff's Invalid ID and Period")
    void testFailGetAllSaleInvoicesByStaffAndPeriod() throws Exception {
        int invalidStaffId = 99;
        // Prepare Mock Request
        Mockito.when(saleInvoiceService.getAllSaleInvoicesByStaffAndPeriod(invalidStaffId,
                DateUtils.parseDate("26-05-2021"),
                DateUtils.parseDate("31-05-2021")))
                .thenThrow(new NullPointerException());
        // MockMVC HTTP Test
        mockMvc.perform(get("/sale-invoices/by-staff/{id}/filter", invalidStaffId)
                .param("startDate", "26-05-2021")
                .param("endDate", "31-05-2021"))
                .andExpect(status().isNotFound());
    }

}