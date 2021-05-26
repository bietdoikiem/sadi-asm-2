package com.rmit.demo.service;

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

@ExtendWith(MockitoExtension.class)
class SaleInvoiceServiceTest {

    @Mock
    private SaleInvoiceRepository saleInvoiceRepository;
    @Mock
    private SaleDetailRepository saleDetailRepository;
    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private StaffRepository staffRepository;

    @Autowired
    @InjectMocks
    private SaleInvoiceService saleInvoiceService;

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
    @DisplayName("Test Success GET All SaleInvoice")
    void testGetAll() {
        // Prepare Mock Data
        List<SaleInvoice> saleInvoiceList = new ArrayList<>();
        saleInvoiceList.add(saleInvoice1);
        saleInvoiceList.add(saleInvoice2);
        // Prepare Mock Request
        Mockito.when(saleInvoiceRepository.findAll()).thenReturn(saleInvoiceList);
        // Verify and Assert Service call
        List<SaleInvoice> saleInvoices = saleInvoiceService.getAll();
        assertEquals(2, saleInvoices.size());
        verify(saleInvoiceRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Test Empty GET All SaleInvoice")
    void testEmptyGetAll() {
        // Prepare Mock Data
        List<SaleInvoice> saleInvoiceList = new ArrayList<>();
        // Prepare Mock Request
        Mockito.when(saleInvoiceRepository.findAll()).thenReturn(saleInvoiceList);
        List<SaleInvoice> saleInvoices = saleInvoiceService.getAll();
        assertEquals(0,saleInvoices.size());
    }

    @Test
    @DisplayName("Test GET ALL SaleInvoice by Pagination")
    void testPaginationGetAll() {
        // Prepare Mock Data
        List<SaleInvoice> saleInvoiceList = new ArrayList<>();
        saleInvoiceList.add(saleInvoice1);
        saleInvoiceList.add(saleInvoice2);
        // Mock Request
        Pageable pageable = PageRequest.of(0, 1);
        Page<SaleInvoice> saleInvoicePage = new PageImpl<>(saleInvoiceList.subList(0, 1));
        Mockito.when(saleInvoiceRepository.findAll(pageable)).thenReturn(saleInvoicePage);
        // Verify & Assertion
        List<SaleInvoice> mockedList = saleInvoiceService.getAll(0, 1);
        assertEquals(mockedList.size(), saleInvoiceList.subList(0, 1).size());
        Mockito.verify(saleInvoiceRepository, times(1)).findAll(pageable);
    }

    @Test
    @DisplayName("Test Empty GET ALL SaleInvoice by Pagination")
    void testEmptyPaginationGetAll() {
        // Prepare Mock Data
        List<SaleInvoice> saleInvoiceList = new ArrayList<>();
        saleInvoiceList.add(saleInvoice1);
        saleInvoiceList.add(saleInvoice2);
        // Mock Request
        Pageable pageable = PageRequest.of(0, 1);
        Page<SaleInvoice> saleInvoicePage = new PageImpl<>(saleInvoiceList.subList(0, 1));
        Mockito.when(saleInvoiceRepository.findAll(pageable)).thenReturn(saleInvoicePage);
        // Verify & Assertion
        List<SaleInvoice> mockedList = saleInvoiceService.getAll(0, 1);
        assertEquals(mockedList.size(), saleInvoicePage.getContent().size());
        Mockito.verify(saleInvoiceRepository, times(1)).findAll(pageable);
    }

    @Test
    @DisplayName("Test Success GET One SaleInvoice by Id")
    void testGetOne() {
        int validID = 1;
        // Prepare Mock Request
        Mockito.when(saleInvoiceRepository.findById(validID)).thenReturn(Optional.of(saleInvoice1));
        // Verify And Assert
        SaleInvoice saleInvoice = saleInvoiceService.getOne(validID);
        assertEquals(saleInvoice.getId(), validID);
        verify(saleInvoiceRepository, times(1)).findById(validID);
    }

    @Test
    @DisplayName("Test Fail GET One SaleInvoice by Id")
    void testFailGetOne() {
        int invalidID = 99;
        // Prepare Mock Request
        Mockito.when(saleInvoiceRepository.findById(invalidID)).thenThrow(new NullPointerException());
        // Verify And Assert
        assertThrows(NullPointerException.class, () -> saleInvoiceService.getOne(invalidID));
        verify(saleInvoiceRepository, times(1)).findById(invalidID);
    }

    @Test
    @DisplayName("Test Success CREATE One SaleInvoice")
    void testSaveOne() {
        // Prepare Mock Data
        List<SaleDetail> saleDetailList = new ArrayList<>();
        saleDetailList.add(saleDetail1);
        saleDetailList.add(saleDetail2);
        saleInvoice1.setSaleDetailList(saleDetailList);
        // Mock Request
        Mockito.when(saleInvoiceRepository.saveAndReset(saleInvoice1)).thenReturn(saleInvoice1);
        // Assert and Verify
        SaleInvoice saved = saleInvoiceService.saveOne(saleInvoice1);
        assertEquals(saved.getId(), saleInvoice1.getId());
        verify(saleInvoiceRepository, times(1)).saveAndReset(saleInvoice1);
    }

    @Test
    @DisplayName("Test Success UPDATE One SaleInvoice (Update DATE)")
    void testUpdateOne() {
        List<SaleDetail> saleDetailList = new ArrayList<>();
        saleDetailList.add(saleDetail1);
        saleDetailList.add(saleDetail2);
        saleInvoice1.setSaleDetailList(saleDetailList);
        // MockRequest
        SaleInvoice updated = new SaleInvoice(1, DateUtils.parseDate("07-07-2021"), staff1, customer1, 0);
        updated.setSaleDetailList(saleDetailList);
        Mockito.when(saleInvoiceRepository.saveAndReset(isA(SaleInvoice.class))).thenReturn(updated);
        // Verify service call
        SaleInvoice updatedOne = saleInvoiceService.saveOne(updated);
        assertEquals(updatedOne.getDate(), DateUtils.parseDate("07-07-2021"));
        verify(saleInvoiceRepository, times(1)).saveAndReset(updated);
    }

    @Test
    @DisplayName("Test Fail UPDATE one SaleInvoice by inputting wrong Id")
    void testFailUpdateOne() {
        int invalidId = 99;
        //
        List<SaleDetail> saleDetailList = new ArrayList<>();
        saleDetailList.add(saleDetail1);
        saleDetailList.add(saleDetail2);
        saleInvoice1.setSaleDetailList(saleDetailList);
        // MockRequest
        SaleInvoice updated = new SaleInvoice(1, DateUtils.parseDate("07-07-2021"), staff1, customer1, 0);
        updated.setSaleDetailList(saleDetailList);
        Mockito.when(saleInvoiceRepository.findById(invalidId)).thenThrow(new NullPointerException());
        // Verify service call
        SaleInvoice updatedOne = saleInvoiceService.saveOne(updated);
        assertThrows(NullPointerException.class, () -> saleInvoiceService.updateOne(invalidId, updated));
        verify(saleInvoiceRepository, times(1)).findById(invalidId);
    }

    @Test
    @DisplayName("Test Success DELETE one SaleInvoice")
    void testDeleteOne() {
        // Mock request
        Mockito.when(saleInvoiceRepository.findById(1)).thenReturn(Optional.of(saleInvoice1));
        Mockito.doNothing().when(saleInvoiceRepository).delete(saleInvoice1);
        // Verify delete request & assertion service
        int result = saleInvoiceService.deleteOne(1);
        assertEquals(result, saleInvoice1.getId());
        verify(saleInvoiceRepository, times(1)).delete(saleInvoice1);
    }

    @Test
    @DisplayName("Test Success DELETE one SaleInvoice by inputting wrong Id")
    void testFailDeleteOne() {
        int invalidId = 99;
        // Mock request
        Mockito.when(saleInvoiceRepository.findById(invalidId)).thenThrow(new NullPointerException());
        // Verify delete request & assertion service
        assertThrows(NullPointerException.class, () -> saleInvoiceService.deleteOne(invalidId));
        verify(saleInvoiceRepository, times(1)).findById(invalidId);
        verify(saleInvoiceRepository, times(0)).delete(saleInvoice1);
    }

    @Test
    @DisplayName("Test Success GET All SaleDetail by SaleInvoice")
    void testGetAllSaleDetailsBySaleInvoice() {
        // Prepare Mock Data
        int validId = 1;
        List<SaleDetail> saleDetailList = new ArrayList<>();
        saleDetailList.add(saleDetail1);
        saleDetailList.add(saleDetail2);
        saleInvoice1.setSaleDetailList(saleDetailList);
        // Mock Request
        Mockito.when(saleDetailRepository.findSaleDetailsBySaleInvoice(saleInvoice1)).thenReturn(saleDetailList);
        Mockito.when(saleInvoiceRepository.findById(validId)).thenReturn(Optional.of(saleInvoice1));
        // Verify & Assertion
        List<SaleDetail> retrievedList = saleInvoiceService.getAllSaleDetailsBySaleInvoice(validId);
        assertEquals(saleDetailList, retrievedList);
        verify(saleInvoiceRepository, times(1)).findById(validId);
        verify(saleDetailRepository, times(1)).findSaleDetailsBySaleInvoice(saleInvoice1);
    }

    @Test
    @DisplayName("Test Failed GET All SaleDetail By invalid SaleInvoice's Id")
    void testFailGetAllSaleDetailsBySaleInvoice() {
        // Prepare Mock Data
        int invalidId = 99;
        List<SaleDetail> saleDetailList = new ArrayList<>();
        saleDetailList.add(saleDetail1);
        saleDetailList.add(saleDetail2);
        saleInvoice1.setSaleDetailList(saleDetailList);
        // Mock Request
        Mockito.when(saleInvoiceRepository.findById(invalidId)).thenThrow(new NullPointerException());
        // Verify & Assertion
        assertThrows(NullPointerException.class, () -> saleInvoiceService.getAllSaleDetailsBySaleInvoice(invalidId));
        verify(saleInvoiceRepository, times(1)).findById(invalidId);
    }

    @Test
    @DisplayName("Test Success FILTER All SaleInvoice in a period")
    void testFilterByPeriod() {
        // Prepare Mock Data
        List<SaleInvoice> saleInvoiceList = new ArrayList<>();
        saleInvoiceList.add(saleInvoice1);
        saleInvoiceList.add(saleInvoice2);
        // Mock Repo
        Date startDate = DateUtils.parseDate("26-05-2021 00:00:00");
        Date endDate = DateUtils.parseDate("31-05-2021 23:59:59");
        Date normStartDate = DateUtils.normalizeDateAtStart(startDate);
        Date normEndDate = DateUtils.normalizeDateAtEnd(endDate);
        Mockito.when(saleInvoiceRepository.findAllByDateBetween(normStartDate, normEndDate)).thenReturn(saleInvoiceList);
        // Verify & Assert
        List<SaleInvoice> retrievedList = saleInvoiceService.filterByPeriod(startDate, endDate);
        assertEquals(saleInvoiceList, retrievedList);
    }

    @Test
    @DisplayName("Test Empty FILTER All SaleInvoice in a period")
    void testEmptyFilterByPeriod() {
        // Prepare Mock Data
        List<SaleInvoice> saleInvoiceList = new ArrayList<>();
        // Mock Repo
        Date startDate = DateUtils.parseDate("05-05-2021 00:00:00");
        Date endDate = DateUtils.parseDate("06-05-2021 23:59:59");
        Date normStartDate = DateUtils.normalizeDateAtStart(startDate);
        Date normEndDate = DateUtils.normalizeDateAtEnd(endDate);
        Mockito.when(saleInvoiceRepository.findAllByDateBetween(normStartDate, normEndDate)).thenReturn(saleInvoiceList);
        // Verify & Assert Empty List
        List<SaleInvoice> retrievedList = saleInvoiceService.filterByPeriod(startDate, endDate);
        assertEquals(saleInvoiceList, retrievedList);
        assertEquals(saleInvoiceList.size(), retrievedList.size());
    }

    @Test
    @DisplayName("Test Success GET All SaleInvoice of a Customer by a Period")
    void testGetAllSaleInvoicesByCustomerAndPeriod() {
        // Prepare MockData
        int validCustomerId = 1;
        List<SaleInvoice> saleInvoiceList = new ArrayList<>();
        saleInvoiceList.add(saleInvoice1);
        saleInvoiceList.add(saleInvoice2);
        Date startDate = DateUtils.parseDate("05-05-2021 00:00:00");
        Date endDate = DateUtils.parseDate("06-05-2021 23:59:59");
        Date normStartDate = DateUtils.normalizeDateAtStart(startDate);
        Date normEndDate = DateUtils.normalizeDateAtEnd(endDate);
        // Mock Repo
        Mockito.when(customerRepository.findById(validCustomerId)).thenReturn(Optional.of(customer1));
        Mockito.when(saleInvoiceRepository.findSaleInvoicesByCustomerAndDateBetween(customer1, normStartDate, normEndDate))
                .thenReturn(saleInvoiceList);
        // Verify & Assert
        List<SaleInvoice> retrievedList = saleInvoiceService.getAllSaleInvoicesByCustomerAndPeriod(validCustomerId, startDate, endDate);
        assertEquals(saleInvoiceList, retrievedList);
        verify(customerRepository, times(1)).findById(validCustomerId);
        verify(saleInvoiceRepository, times(1)).findSaleInvoicesByCustomerAndDateBetween(customer1, normStartDate, normEndDate);
    }

    @Test
    @DisplayName("Test Fail GET All SaleInvoice By invalid Customer's Id and a Period")
    void testFailGetAllSaleInvoicesByCustomerAndPeriod() {
        // Prepare MockData
        int invalidCustomerId = 1;
        List<SaleInvoice> saleInvoiceList = new ArrayList<>();
        saleInvoiceList.add(saleInvoice1);
        saleInvoiceList.add(saleInvoice2);
        Date startDate = DateUtils.parseDate("05-05-2021 00:00:00");
        Date endDate = DateUtils.parseDate("06-05-2021 23:59:59");
        Date normStartDate = DateUtils.normalizeDateAtStart(startDate);
        Date normEndDate = DateUtils.normalizeDateAtEnd(endDate);
        // Mock Repo
        Mockito.when(customerRepository.findById(invalidCustomerId)).thenThrow(new NullPointerException());
        // Verify & Assert
        assertThrows(NullPointerException.class, () -> saleInvoiceService.getAllSaleInvoicesByCustomerAndPeriod(invalidCustomerId, startDate, endDate));
        verify(customerRepository, times(1)).findById(invalidCustomerId);
        verify(saleInvoiceRepository, times(0)).findSaleInvoicesByCustomerAndDateBetween(customer1, normStartDate, normEndDate);
    }

    @Test
    @DisplayName("Test Success GET All SaleInvoice of a Staff by a Period")
    void testGetAllSaleInvoicesByStaffAndPeriod() {
        // Prepare MockData
        int validStaffId = 1;
        List<SaleInvoice> saleInvoiceList = new ArrayList<>();
        saleInvoiceList.add(saleInvoice1);
        saleInvoiceList.add(saleInvoice2);
        Date startDate = DateUtils.parseDate("05-05-2021 00:00:00");
        Date endDate = DateUtils.parseDate("06-05-2021 23:59:59");
        Date normStartDate = DateUtils.normalizeDateAtStart(startDate);
        Date normEndDate = DateUtils.normalizeDateAtEnd(endDate);
        // Mock Repo
        Mockito.when(staffRepository.findById(validStaffId)).thenReturn(Optional.of(staff1));
        Mockito.when(saleInvoiceRepository.findSaleInvoicesByStaffAndDateBetween(staff1, normStartDate, normEndDate))
                .thenReturn(saleInvoiceList);
        // Verify & Assert
        List<SaleInvoice> retrievedList = saleInvoiceService.getAllSaleInvoicesByStaffAndPeriod(validStaffId, startDate, endDate);
        assertEquals(saleInvoiceList, retrievedList);
        verify(staffRepository, times(1)).findById(validStaffId);
        verify(saleInvoiceRepository, times(1)).findSaleInvoicesByStaffAndDateBetween(staff1, normStartDate, normEndDate);
    }

    @Test
    @DisplayName("Test Fail GET All SaleInvoice By invalid Staff's Id and a Period")
    void testFailGetAllSaleInvoicesByStaffAndPeriod() {
        // Prepare MockData
        int invalidStaffId = 1;
        List<SaleInvoice> saleInvoiceList = new ArrayList<>();
        saleInvoiceList.add(saleInvoice1);
        saleInvoiceList.add(saleInvoice2);
        Date startDate = DateUtils.parseDate("05-05-2021 00:00:00");
        Date endDate = DateUtils.parseDate("06-05-2021 23:59:59");
        Date normStartDate = DateUtils.normalizeDateAtStart(startDate);
        Date normEndDate = DateUtils.normalizeDateAtEnd(endDate);
        // Mock Repo
        Mockito.when(staffRepository.findById(invalidStaffId)).thenThrow(new NullPointerException());
        // Verify & Assert
        assertThrows(NullPointerException.class, () -> saleInvoiceService.getAllSaleInvoicesByStaffAndPeriod(invalidStaffId, startDate, endDate));
        verify(staffRepository, times(1)).findById(invalidStaffId);
        verify(saleInvoiceRepository, times(0)).findSaleInvoicesByStaffAndDateBetween(staff1, normStartDate, normEndDate);
    }
}