package com.rmit.demo.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rmit.demo.model.*;
import com.rmit.demo.reponses.NoteStatsResponse;
import com.rmit.demo.reponses.NoteStatsResponseImpl;
import com.rmit.demo.repository.SaleInvoiceRepository;
import com.rmit.demo.utils.DateUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rmit.demo.config.RestExceptionHandler;
import com.rmit.demo.model.*;
import com.rmit.demo.repository.*;
import com.rmit.demo.utils.DateUtils;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class StatsServiceTest {

    @Mock
    private SaleInvoiceRepository saleInvoiceRepository;
    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private StaffRepository staffRepository;
    @Mock
    private SaleInvoiceService saleInvoiceService;

    @InjectMocks
    private StatsService statsService;
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
    @DisplayName("Test Success GET Revenue By Customer and Period")
    void testGetRevenueByCustomerAndPeriod() {
        // Prepare Mock Data
        int validId = 1;
        List<SaleInvoice> saleInvoiceList = new ArrayList<>();
        saleInvoiceList.add(saleInvoice1);
        List<SaleDetail> saleDetailList1 = new ArrayList<>();
        saleDetailList1.add(saleDetail1);
        saleDetailList1.add(saleDetail2);
        saleInvoice1.setSaleDetailList(saleDetailList1);
        // Mock request to repository
        Mockito.when(saleInvoiceService.getAllSaleInvoicesByCustomerAndPeriod(validId,
                DateUtils.parseDate("26-05-2021"),
                DateUtils.parseDate("31-05-2021"))).thenReturn(saleInvoiceList);
        // Actual Service Call
        double revenue = statsService.getRevenueByCustomerAndPeriod(validId, DateUtils.parseDate("26-05-2021"),
                DateUtils.parseDate("31-05-2021"));
        assertEquals(450.0, revenue);
        verify(saleInvoiceService, times(1)).getAllSaleInvoicesByCustomerAndPeriod(validId,
                DateUtils.parseDate("26-05-2021"),
                DateUtils.parseDate("31-05-2021"));
    }

    @Test
    @DisplayName("Test Fail GET Revenue By Customer's invalid Id and Period")
    void testFailGetRevenueByCustomerAndPeriod() {
        // Prepare Mock Data
        int invalidId = 99;
        List<SaleInvoice> saleInvoiceList = new ArrayList<>();
        saleInvoiceList.add(saleInvoice1);
        List<SaleDetail> saleDetailList1 = new ArrayList<>();
        saleDetailList1.add(saleDetail1);
        saleDetailList1.add(saleDetail2);
        saleInvoice1.setSaleDetailList(saleDetailList1);
        // Mock request to repository
        Mockito.when(saleInvoiceService.getAllSaleInvoicesByCustomerAndPeriod(invalidId,
                DateUtils.parseDate("26-05-2021"),
                DateUtils.parseDate("31-05-2021"))).thenThrow(new NullPointerException());
        // Actual Service Call
        assertThrows(NullPointerException.class, () -> statsService.getRevenueByCustomerAndPeriod(invalidId, DateUtils.parseDate("26-05-2021"),
                DateUtils.parseDate("31-05-2021")));
        verify(saleInvoiceService, times(1)).getAllSaleInvoicesByCustomerAndPeriod(invalidId,
                DateUtils.parseDate("26-05-2021"),
                DateUtils.parseDate("31-05-2021"));
    }

    @Test
    @DisplayName("Test Success GET Revenue By Staff and Period")
    void testGetRevenueByStaffAndPeriod() {
        // Prepare Mock Data
        int validId = 1;
        List<SaleInvoice> saleInvoiceList = new ArrayList<>();
        saleInvoiceList.add(saleInvoice1);
        List<SaleDetail> saleDetailList1 = new ArrayList<>();
        saleDetailList1.add(saleDetail1);
        saleDetailList1.add(saleDetail2);
        saleInvoice1.setSaleDetailList(saleDetailList1);
        // Mock request to repository
        Mockito.when(saleInvoiceService.getAllSaleInvoicesByStaffAndPeriod(validId,
                DateUtils.parseDate("26-05-2021"),
                DateUtils.parseDate("31-05-2021"))).thenReturn(saleInvoiceList);
        // Actual Service Call
        double revenue = statsService.getRevenueByStaffAndPeriod(validId, DateUtils.parseDate("26-05-2021"),
                DateUtils.parseDate("31-05-2021"));
        assertEquals(450.0, revenue);
        verify(saleInvoiceService, times(1)).getAllSaleInvoicesByStaffAndPeriod(validId,
                DateUtils.parseDate("26-05-2021"),
                DateUtils.parseDate("31-05-2021"));
    }

    @Test
    @DisplayName("Test Fail GET Revenue By Staff's invalid Id and Period")
    void testFailGetRevenueByStaffAndPeriod() {
        // Prepare Mock Data
        int invalidId = 99;
        List<SaleInvoice> saleInvoiceList = new ArrayList<>();
        saleInvoiceList.add(saleInvoice1);
        List<SaleDetail> saleDetailList1 = new ArrayList<>();
        saleDetailList1.add(saleDetail1);
        saleDetailList1.add(saleDetail2);
        saleInvoice1.setSaleDetailList(saleDetailList1);
        // Mock request to repository
        Mockito.when(saleInvoiceService.getAllSaleInvoicesByStaffAndPeriod(invalidId,
                DateUtils.parseDate("26-05-2021"),
                DateUtils.parseDate("31-05-2021"))).thenThrow(new NullPointerException());
        // Actual Service Call
        assertThrows(NullPointerException.class, () -> statsService.getRevenueByStaffAndPeriod(invalidId, DateUtils.parseDate("26-05-2021"),
                DateUtils.parseDate("31-05-2021")));
        verify(saleInvoiceService, times(1)).getAllSaleInvoicesByStaffAndPeriod(invalidId,
                DateUtils.parseDate("26-05-2021"),
                DateUtils.parseDate("31-05-2021"));
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
        // Mock Service
        // Retrieve two reports period
        Mockito.when(saleInvoiceRepository.findProductsReceivedAndDeliveredBetween(
                DateUtils.parseDate("26-05-2021"),
                DateUtils.parseDate("31-05-2021")
        )).thenReturn(noteStatsResponseList);
        // Empty period of time
        Mockito.when(saleInvoiceRepository.findProductsReceivedAndDeliveredBetween(
                DateUtils.parseDate("05-05-2021"),
                DateUtils.parseDate("06-05-2021")
        )).thenReturn(new ArrayList<>());
        // Call Service
        List<NoteStatsResponse> noteStatsResponses = statsService.getProductsByDeliverNoteAndReceivingNoteAndPeriod(
                DateUtils.parseDate("26-05-2021"),
                DateUtils.parseDate("31-05-2021"));
        // Verify & Assertion
        assertEquals(noteStatsResponseList, noteStatsResponses);
        verify(saleInvoiceRepository, times(1)).findProductsReceivedAndDeliveredBetween(
                DateUtils.parseDate("26-05-2021"),
                DateUtils.parseDate("31-05-2021")
        );
        // Test Empty Result when retrieving in a different time period
        List<NoteStatsResponse> empty = statsService.getProductsByDeliverNoteAndReceivingNoteAndPeriod(
                DateUtils.parseDate("05-05-2021"),
                DateUtils.parseDate("06-05-2021"));
        assertEquals(new ArrayList<>(), empty);
        verify(saleInvoiceRepository, times(1)).findProductsReceivedAndDeliveredBetween(
                DateUtils.parseDate("05-05-2021"),
                DateUtils.parseDate("06-05-2021")
        );
    }

}