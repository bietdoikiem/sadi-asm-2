package com.rmit.demo.service;

import com.rmit.demo.model.Customer;
import com.rmit.demo.repository.CustomerRepository;
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
class CustomerServiceTest {
    @Mock
    private CustomerRepository customerRepository;

    @Autowired
    @InjectMocks
    private CustomerService customerService;
    private ArrayList<Customer> customerArrayList;
    private Customer customer1;
    private Customer customer2;
    private Customer customer3;

    @BeforeEach
    void setUp() {
        customerArrayList = new ArrayList<>();
        customer1 = new Customer(1, "Duy", "TpHCM", "0326795463", "duyhs1234@gmail.com", "2132-1232", "Minh");
        customer2 = new Customer(2, "Dong", "TpHCM", "313213213", "dong4@gmail.com", "1313-3243", "Duy");
        customer3 = new Customer(3, "Minh", "TpHCM", "214322514", "minhsimp@gmail.com", "5512-12341", "Dong");
        customerArrayList.add(customer1);
        customerArrayList.add(customer2);
        customerArrayList.add(customer3);
    }

    @AfterEach
    void tearDown() {
        customer1 = customer2 = customer3 = null;
        customerArrayList = null;
    }

    @Test
    @DisplayName("Test GET ALL Customers")
    void testGetAllCustomers() {
        Mockito.when(customerRepository.findAll()).thenReturn(customerArrayList);
        ArrayList<Customer> customerArrayList1 = customerService.getAllCustomers();
        assertEquals(customerArrayList1, customerArrayList);
        Mockito.verify(customerRepository, Mockito.times(1)).findAll();
    }

    @Test
    @DisplayName("Test GET List of Empty Customer")
    void testGetEmptyCustomers() {
        Mockito.when(customerRepository.findAll()).thenReturn(new ArrayList<>());
        ArrayList<Customer> customerArrayList1 = customerService.getAllCustomers();
        assertEquals(customerArrayList1, new ArrayList<>());
        Mockito.verify(customerRepository, Mockito.times(1)).findAll();
    }

    @Test
    @DisplayName("Test GET One Customer by Id")
    void testGetOne() {
        int id = 1;
        Mockito.when(customerRepository.findById(id)).thenReturn(Optional.of(customer1));
        Customer foundCustomer = customerService.getOne(id);
        assertEquals(foundCustomer, customer1);
    }

    @Test
    @DisplayName("Test Failed GET One Customer by Invalid Id")
    void testFailGetOne() {
        int invalidId = 99;
        Mockito.when(customerRepository.findById(invalidId)).thenReturn(null);
        assertThrows(NullPointerException.class, () -> customerService.getOne(invalidId));
    }

    @Test
    @DisplayName("Test Success CREATE Customer")
    void testSaveOne() {
        Mockito.when(customerRepository.saveAndReset(customer1)).thenReturn(customer1);
        Customer savedCustomer = customerService.saveCustomer(customer1);
        assertEquals(savedCustomer, customer1);
    }

//    @Test
//    @DisplayName("Test Fail CREATE Customer")
//    void testFailSaveOne() {
//        Mockito.when(customerRepository.save(customer1)).thenReturn(customer1);
//        Customer savedCustomer = customerService.saveCustomer(customer1);
//        assertEquals(savedCustomer, customer1);
//    }

    @Test
    @DisplayName("Test Success UPDATE method for Customer")
    void testUpdateOne() {
        // Mock customer object
        Customer updatedOne = new Customer(1, "DuyDuy", "TpHCM", "0326795463", "Duyddduy@gmail.com", "2132-1232", "Minh");
        Mockito.when(customerRepository.findById(updatedOne.getId())).thenReturn(Optional.of(customer1));
        Mockito.when(customerRepository.saveAndReset(customer1)).thenReturn(updatedOne);
        Customer result = customerService.updateCustomer(1, updatedOne);
        assertEquals(result.getName(), updatedOne.getName());
    }

    @Test
    @DisplayName("Test Failed UPDATE method for Customer")
    void testFailUpdateOne() {
        // Mocked
        int validId = 1;
        int invalidId = 99;
        Customer updatedOne = new Customer(invalidId, "DuyDuy", "TpHCM", "0326795463", "Duyddduy@gmail.com", "2132-1232", "Minh");
        Mockito.lenient().when(customerRepository.findById(ArgumentMatchers.eq(validId))).thenReturn(Optional.of(customer1));
        Mockito.lenient().when(customerRepository.save(customer1)).thenReturn(updatedOne);
        assertThrows(NullPointerException.class, () -> customerService.updateCustomer(invalidId, updatedOne));
    }

    @Test
    @DisplayName("Test Success DELETE method for Customer")
    void testDeleteOne() {
        int validId = 1;
        doReturn(Optional.of(customer1)).when(customerRepository).findById(validId);
        doAnswer(i -> { return null; }).when(customerRepository).delete(customer1);
        int result = customerService.deleteCustomer(validId);
        assertEquals(validId, result);
    }

    @Test
    @DisplayName("Test Fail DELETE method for Customer")
    void testFailDeleteOne() {
        int validId = 1;
        int invalidId = 99;
        doReturn(Optional.of(customer1)).when(customerRepository).findById(validId);
        doAnswer(i -> {return null;}).when(customerRepository).delete(customer1);
        int result = customerService.deleteCustomer(validId);
        assertEquals(validId, result);
        assertThrows(NullPointerException.class, () -> customerService.deleteCustomer(invalidId));
    }
}