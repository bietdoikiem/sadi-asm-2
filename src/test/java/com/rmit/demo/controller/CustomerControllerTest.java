package com.rmit.demo.controller;

import com.fasterxml.jackson.annotation.JacksonInject;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rmit.demo.config.RestExceptionHandler;
import com.rmit.demo.model.*;
import com.rmit.demo.repository.CustomerRepository;
import com.rmit.demo.service.CustomerService;
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
class CustomerControllerTest {

    @MockBean
    protected  CustomerService customerService;

    @MockBean
    protected CustomerRepository customerRepository;

    @Autowired
    @InjectMocks
    protected CustomerController customerController;

    protected MockMvc mockMvc;

    // Defined Mock Objects
    protected ArrayList<Customer> customerArrayList;
    protected Customer customer1;
    protected Customer customer2;
    protected Customer customer3;

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
                .standaloneSetup(customerController)
                .setControllerAdvice(new RestExceptionHandler())
                .build();
        Mockito.reset();

        // Define mock object
        customer1 = new Customer(1, "Duy", "TpHCM", "0326795463", "duyhs1234@gmail.com", "2132-1232", "Minh");
        customer2 = new Customer(2, "Dong", "TpHCM", "313213213", "dong4@gmail.com", "1313-3243", "Duy");
        customer3 = new Customer(3, "Minh", "TpHCM", "214322514", "minhsimp@gmail.com", "5512-12341", "Dong");
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    @DisplayName("Test GET all Customer list")
    void testGetAll() throws Exception {
        ArrayList<Customer> customerArrayList = new ArrayList<>();
        customerArrayList.add(new Customer(1, "Duy", "TpHCM", "0326795463", "duyhs1234@gmail.com", "2132-1232", "Minh"));
        customerArrayList.add(new Customer(2, "Dong", "TpHCM", "313213213", "dong4@gmail.com", "1313-3243", "Duy"));

        Mockito.when(customerService.getAllCustomers()).thenReturn(customerArrayList);

        String url = "/customers";
        mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", hasSize(2)))
                .andExpect(jsonPath("$.data[0].id", is(1)))
                .andExpect(jsonPath("$.data[0].name", is("Duy")))
                .andExpect(jsonPath("$.data[0].address", is("TpHCM")))
                .andExpect(jsonPath("$.data[0].phone", is("0326795463")))
                .andExpect(jsonPath("$.data[0].email", is("duyhs1234@gmail.com")))
                .andExpect(jsonPath("$.data[0].fax", is("2132-1232")))
                .andExpect(jsonPath("$.data[0].contactPerson", is("Minh")))
                .andExpect(jsonPath("$.data[1].id", is(2)))
                .andExpect(jsonPath("$.data[1].name", is("Dong")))
                .andExpect(jsonPath("$.data[1].address", is("TpHCM")))
                .andExpect(jsonPath("$.data[1].phone", is("313213213")))
                .andExpect(jsonPath("$.data[1].email", is("dong4@gmail.com")))
                .andExpect(jsonPath("$.data[1].fax", is("1313-3243")))
                .andExpect(jsonPath("$.data[1].contactPerson", is("Duy")))
                .andReturn();
    }

    @Test
    @DisplayName("Test GET empty Customer list")
    void testGetAllEmpty() throws Exception {
        ArrayList<Customer> customerArrayList = new ArrayList<>();

        Mockito.when(customerService.getAllCustomers()).thenReturn(customerArrayList);

        String url = "/customers";
        mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", hasSize(0)))
                .andReturn();
    }

    @Test
    @DisplayName("Test GET one Category by Id")
    void testGetOne() throws Exception {
        int id = 1;
        Customer customer = new Customer(1, "Duy", "TpHCM", "0326795463", "duyhs1234@gmail.com", "2132-1232", "Minh");

        Mockito.when(customerService.getOne(id)).thenReturn(customer);

        String url = "/customers/{id}";
        mockMvc.perform(get(url, id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id", is(id)))
                .andExpect(jsonPath("$.data.id", is(1)))
                .andExpect(jsonPath("$.data.name", is("Duy")))
                .andExpect(jsonPath("$.data.address", is("TpHCM")))
                .andExpect(jsonPath("$.data.phone", is("0326795463")))
                .andExpect(jsonPath("$.data.email", is("duyhs1234@gmail.com")))
                .andExpect(jsonPath("$.data.fax", is("2132-1232")))
                .andExpect(jsonPath("$.data.contactPerson", is("Minh")));
    }

    @Test
    @DisplayName("Test GET Null Customer by Unknown ID")
    void testGetOneEmpty() throws Exception {
        int id = 99;
        Mockito.when(customerService.getOne(id)).thenReturn(null);
        String url = "/customers/{id}";
        mockMvc.perform(get(url, id))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Test Success CREATE One Customer")
    void testSaveOne() throws Exception {
        // Prepare Mocked data
        ArrayList<Customer> customerArrayList = new ArrayList<>();
        customerArrayList.add(new Customer(0, "Duy", "TpHCM", "0326795463", "duyhs1234@gmail.com", "2132-1232", "Minh"));
        customerArrayList.add(new Customer(1, "Dong", "TpHCM", "313213213", "dong4@gmail.com", "1313-3243", "Duy"));

        // Prepare Json Request
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("name", "Duy");
        requestBody.put("address", "TpHCM");
        requestBody.put("phone", "0326795463");
        requestBody.put("email", "duyhs1234@gmail.com");
        requestBody.put("fax", "2132-1232");
        requestBody.put("contactPerson", "Minh");

        String requestJson = asJsonString(requestBody);

        // Mock service to save mocked data
        Mockito.when(customerService.saveCustomer(isA(Customer.class))).thenReturn(customer1);
        // MockMvc HTTP Test
        mockMvc.perform(post("/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.id", is(1)));
    }

    @Test
    @DisplayName("Test Success CREATE One Customer with Null Field")
    void testSaveOneNullField() throws Exception {
        // Prepare Mocked data
        ArrayList<Customer> customerArrayList = new ArrayList<>();
        customerArrayList.add(new Customer(0, "Duy", "TpHCM", null, "duyhs1234@gmail.com", "2132-1232", "Minh"));
        customerArrayList.add(new Customer(1, "Dong", "TpHCM", "313213213", "dong4@gmail.com", "1313-3243", "Duy"));

        // Prepare Json Request
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("name", "25-06-2001");
        requestBody.put("address", "25-06-2001");
        requestBody.put("phone", "25-06-2001");
        requestBody.put("email", "25-06-2001");
        requestBody.put("fax", "25-06-2001");
        requestBody.put("contactPerson", "25-06-2001");

        String requestJson = asJsonString(requestBody);

        // Mock service to save mocked data
        Mockito.when(customerService.saveCustomer(isA(Customer.class))).thenReturn(customer1);
        // MockMvc HTTP Test
        mockMvc.perform(post("/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.id", is(1)));
    }
}