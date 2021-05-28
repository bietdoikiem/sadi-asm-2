package com.rmit.demo.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.rmit.demo.config.RestExceptionHandler;
import com.rmit.demo.model.*;
import com.rmit.demo.reponses.NoteStatsResponse;
import com.rmit.demo.reponses.NoteStatsResponseImpl;
import com.rmit.demo.service.SaleInvoiceService;
import com.rmit.demo.service.StatsService;
import com.rmit.demo.utils.DateUtils;
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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class StatsControllerTest {

    @MockBean
    protected StatsService statsService;
    @MockBean
    protected SaleInvoiceService saleInvoiceService;

    @Autowired
    @InjectMocks
    protected StatsController statsController;

    protected MockMvc mockMvc;

    // Define MockData
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
                .standaloneSetup(statsController)
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
    @DisplayName("Test Success GET Total Revenue in a period")
    void testGetRevenueByPeriod() throws Exception {
        // Prepare Mock Request
        Mockito.when(statsService.getRevenueByPeriod(DateUtils.parseDate("26-05-2021"), DateUtils.parseDate("31-05-2021")))
                .thenReturn(1600.0);
        // Prepare MockMVC HTTP Test
        mockMvc.perform(get("/stats/revenue/filter")
                .param("startDate", "26-05-2021")
                .param("endDate", "31-05-2021"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", is(1600.0)));

        // Malfunction params test
        mockMvc.perform(get("/stats/revenue/filter")
                .param("startDate", "26-05-")
                .param("endDate", "31-05-"))
                .andExpect(status().isBadRequest());
    }
    @Test
    @DisplayName("Test Fail GET Total Revenue in a period")
    void testFailGetRevenueByPeriod() throws Exception {
        // Malfunction date input params HTTP Test
        mockMvc.perform(get("/stats/revenue/filter")
                .param("startDate", "26-05-")
                .param("endDate", "31-05-"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Test Success GET Revenue by Customer in a period")
    void testGetRevenueByCustomerAndPeriod() throws Exception {
        // Prepare Mock Data
        int validCustomerId = 1;
        List<SaleInvoice> saleInvoiceList = new ArrayList<>();
        List<SaleDetail> saleDetailList1 = new ArrayList<>();
        saleDetailList1.add(saleDetail1);
        saleDetailList1.add(saleDetail2);
        saleInvoice1.setSaleDetailList(saleDetailList1);
        saleInvoiceList.add(saleInvoice1);
        // Mock request service
        Mockito.when(saleInvoiceService.getAllSaleInvoicesByCustomerAndPeriod(validCustomerId,
                DateUtils.parseDate("26-05-2021"),
                DateUtils.parseDate("31-05-2021"))).thenReturn(saleInvoiceList);
        Mockito.when(statsService.getRevenueByCustomerAndPeriod(validCustomerId,
                DateUtils.parseDate("26-05-2021"),
                DateUtils.parseDate("31-05-2021"))).thenReturn(450.0);
        // MockMVC Http Test
        mockMvc.perform(get("/stats/customers/{id}/revenue/filter", validCustomerId)
                        .param("startDate", "26-05-2021")
                        .param("endDate", "31-05-2021"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", is(450.0)));
    }

    @Test
    @DisplayName("Test Fail GET Revenue by Customer's invalid Id in a period")
    void testFailGetRevenueByCustomerAndPeriod() throws Exception {
        // Prepare Mock Data
        int invalidCustomerId = 99;
        // Mock request service
        Mockito.when(statsService.getRevenueByCustomerAndPeriod(invalidCustomerId,
                DateUtils.parseDate("26-05-2021"),
                DateUtils.parseDate("31-05-2021"))).thenThrow(new NullPointerException());
        // MockMVC Http Test
        mockMvc.perform(get("/stats/customers/{id}/revenue/filter", invalidCustomerId)
                .param("startDate", "26-05-2021")
                .param("endDate", "31-05-2021"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Test Success GET Revenue by Staff in a period")
    void testGetRevenueByStaffAndPeriod() throws Exception {
        // Prepare Mock Data
        int validStaffId = 1;
        List<SaleInvoice> saleInvoiceList = new ArrayList<>();
        List<SaleDetail> saleDetailList1 = new ArrayList<>();
        saleDetailList1.add(saleDetail1);
        saleDetailList1.add(saleDetail2);
        List<SaleDetail> saleDetailList2 = new ArrayList<>();
        saleDetailList2.add(saleDetail3);
        saleDetailList2.add(saleDetail4);
        saleInvoice1.setSaleDetailList(saleDetailList1);
        saleInvoice2.setSaleDetailList(saleDetailList2);
        saleInvoiceList.add(saleInvoice1);
        saleInvoiceList.add(saleInvoice2);
        double mockResult = 0;
        // Calculate Mock Result Revenue of Staff 1
        for (SaleDetail saleDetail: saleInvoice1.getSaleDetailList()) {
            mockResult += saleDetail.getProduct().getPrice(); // 450.0
        }
        // Mock request service
        Mockito.when(statsService.getRevenueByStaffAndPeriod(validStaffId,
                DateUtils.parseDate("26-05-2021"),
                DateUtils.parseDate("31-05-2021"))).thenReturn(mockResult);
        // MockMVC Http Test
        mockMvc.perform(get("/stats/staffs/{id}/revenue/filter", validStaffId)
                .param("startDate", "26-05-2021")
                .param("endDate", "31-05-2021"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", is(mockResult)));
    }

    @Test
    @DisplayName("Test Fail GET Revenue by Staff's invalid Id in a period")
    void testFailGetRevenueByStaffAndPeriod() throws Exception {
        // Prepare Mock Data
        int invalidStaffId = 99;
        // Mock request service
        Mockito.when(statsService.getRevenueByStaffAndPeriod(invalidStaffId,
                DateUtils.parseDate("26-05-2021"),
                DateUtils.parseDate("31-05-2021"))).thenThrow(new NullPointerException());
        // MockMVC Http Test
        mockMvc.perform(get("/stats/staffs/{id}/revenue/filter", invalidStaffId)
                .param("startDate", "26-05-2021")
                .param("endDate", "31-05-2021"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Test Success GET Products Received, Delivered and Balance Quantity")
    void testGetProductsReceivedAndDeliveredBetween() throws Exception {
        // Prepare Mock Data
        NoteStatsResponseImpl note1 = new NoteStatsResponseImpl(1, "Nike Air Force 1", 4, 3, 1);
        NoteStatsResponseImpl note2 = new NoteStatsResponseImpl(2, "Nike Mamba 9X", 4, 4, 0);
        List<NoteStatsResponse> noteStatsResponseList = new ArrayList<>();
        noteStatsResponseList.add(note1);
        noteStatsResponseList.add(note2);
        // Mock service
        // Retrieve two reports period
        Mockito.when(statsService.getProductsByDeliverNoteAndReceivingNoteAndPeriod(
                DateUtils.parseDate("26-05-2021"), DateUtils.parseDate("31-05-2021")
        )).thenReturn(noteStatsResponseList);
        // Empty period of time
        Mockito.when(statsService.getProductsByDeliverNoteAndReceivingNoteAndPeriod(
                DateUtils.parseDate("05-05-2021"), DateUtils.parseDate("06-05-2021")
        )).thenReturn(new ArrayList<>());
        // MockMVC HTTP Test
        // Test Retrieved Result
        mockMvc.perform(get("/stats/notes/filter")
                .param("startDate", "26-05-2021")
                .param("endDate", "31-05-2021"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].product_id", is(1)))
                .andExpect(jsonPath("$.data[1].product_id", is(2)));
        // Test Empty Result
        mockMvc.perform(get("/stats/notes/filter")
                .param("startDate", "05-05-2021")
                .param("endDate", "06-05-2021"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", hasSize(0)));
    }

}