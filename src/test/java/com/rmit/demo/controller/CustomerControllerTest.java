package com.rmit.demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rmit.demo.model.Customer;
import com.rmit.demo.repository.CustomerRepository;
import com.rmit.demo.service.CustomerService;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@ExtendWith(SpringExtension.class)
@WebMvcTest(CustomerController.class)
@ActiveProfiles("sandbox")
//@ContextConfiguration("")
public class CustomerControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CustomerService customerService;

    @MockBean
    private CustomerRepository customerRepository;

    @Test
    void testSaveOne() {

    }

    @Test
    void testGetAll() throws Exception {
        ArrayList<Customer> customerArrayList = new ArrayList<>();
        Customer customer = new Customer(1, "Duy", "TpHCM", "0326795463", "duyhs1234@gmail.com", "323333-21", "Someone");
        customerArrayList.add(customer);

        Mockito.when(customerService.getAllCustomers()).thenReturn(customerArrayList);

        String url = "/customers";
        mockMvc.perform(get(url)).andExpect(status().isOk());
    }

    @Test
    void testGetOne() {

    }

    @Test
    void testUpdateAll() {

    }

    @Test
    void testDeleteAll() {

    }

    @Test
    void testGetAllByPagination() {

    }

    @Test
    void testGetAllByName() {

    }

    @Test
    void testGetAllByAddress() {

    }

    @Test
    void testGetAllByPhone() {

    }
}
