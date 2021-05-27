package com.rmit.demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rmit.demo.config.RestExceptionHandler;
import com.rmit.demo.model.*;
import com.rmit.demo.repository.CustomerRepository;
import com.rmit.demo.service.CustomerService;
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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.util.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import java.util.ArrayList;

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
        customer1 = customer2 = customer3 = null;
        customerArrayList = null;
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
        int invalidId = 99;
        Mockito.when(customerService.getOne(invalidId)).thenReturn(null);
        String url = "/customers/{id}";
        mockMvc.perform(get(url, invalidId))
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
        requestBody.put("name", "Duy");
        requestBody.put("address", "TpHCM");
        requestBody.put("phone", null);
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
    @DisplayName("Test Success UPDATE for Customer")
    void testUpdateOne() throws Exception {
        int validId = 1;
        // Prepare Json Request
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("name", "Duy");
        requestBody.put("address", "TpHCM");
        requestBody.put("phone", "123456789");
        requestBody.put("email", "duyhs1234@gmail.com");
        requestBody.put("fax", "2132-1232");
        requestBody.put("contactPerson", "Minh");

        String requestJson = asJsonString(requestBody);

        // Mock data when requesting service
        Mockito.when(customerRepository.findById(validId)).thenReturn(Optional.of(customer1));
        Mockito.when(customerService.updateCustomer(intThat(id -> id == validId), isA(Customer.class))).thenReturn(customer1);

        // MockMvc HTTP Test
        mockMvc.perform(put("/customers/{id}",validId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Test Fail UPDATE for Customer")
    void testFailUpdateOne() throws Exception {
        int validId = 1;
        int invalidId = 99;

        // Prepare Json Request
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("name", "Duy");
        requestBody.put("address", "TpHCM");
        requestBody.put("phone", "123456789");
        requestBody.put("email", "duyhs1234@gmail.com");
        requestBody.put("fax", "2132-1232");
        requestBody.put("contactPerson", "Minh");
        String requestJson = asJsonString(requestBody);

        // Mock data when requesting service
        Mockito.when(customerRepository.findById(validId)).thenReturn(Optional.of(customer1));
        Mockito.when(customerService.updateCustomer(intThat(id -> id == invalidId), isA(Customer.class))).thenReturn(customer1);

        // MockMvc HTTP Test
        mockMvc.perform(put("/customers/{id}",validId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Test Success DELETE Customer")
    void testDeleteOne() throws Exception {
        int validId = 1;

        // Mock data when request service and repository
        Mockito.when(customerRepository.findById(validId)).thenReturn(Optional.of(customer1));
        Mockito.when(customerService.deleteCustomer(validId)).thenReturn(validId);

        // MockMVC HTTP Test
        mockMvc.perform(delete("/customers/{id}", validId))
                .andExpect(status().isAccepted());
    }

    @Test
    @DisplayName("Test Fail DELETE Method (Invalid Id)")
    void testFailDeleteOne() throws Exception {
        int validId = 1;
        int invalidId = 99;

        // Mock data when request service and repository
        Mockito.when(customerService.deleteCustomer(validId)).thenReturn(validId);
        Mockito.when(customerService.deleteCustomer(invalidId)).thenThrow(new NullPointerException());

        // MockMVC HTTP Test
        mockMvc.perform(delete("/customers/{id}", validId))
                .andExpect(status().isAccepted());

        // MockMVC HTTP Fail Test
        mockMvc.perform(delete("/customers/{id}", invalidId))
                .andExpect(status().isNotFound());

    }

    @Test
    @DisplayName("Test Success GET All Customers by Pagination")
    void testPaginationGetAll() throws Exception {
        // Mocked list
        ArrayList<Customer> customerArrayList = new ArrayList<>();
        customerArrayList.add(customer1);
        customerArrayList.add(customer2);

        // Mock service to return mocked objects
        Mockito.when(customerService.getAllCustomers(0, 1)).thenReturn(new ArrayList<>(customerArrayList.subList(0, 1)));
        Mockito.when(customerService.getAllCustomers(1, 1)).thenReturn(new ArrayList<>(customerArrayList.subList(1, 2)));

        // Test retrieve first page (index 0) with size 1
        mockMvc.perform(get("/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .param("page", "0")
                .param("size", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isSuccess", is(true)))
                .andExpect(jsonPath("$.data", hasSize(1)))
                .andExpect(jsonPath("$.data[0].id", is(1)));

        // Test retrieve second page (index 1) with size 1
        mockMvc.perform(get("/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .param("page", "1")
                .param("size", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isSuccess", is(true)))
                .andExpect(jsonPath("$.data", hasSize(1)))
                .andExpect(jsonPath("$.data[0].id", is(2)));
    }

    @Test
    @DisplayName("Test Fail GET All Customers by Pagination due to invalid page and size input")
    void testFailPaginationGetAll() throws Exception {
        int invalidPage = -1;
        int invalidSize = -1;

        // Mocked list
        ArrayList<Customer> customerArrayList = new ArrayList<>();
        customerArrayList.add(customer1);
        customerArrayList.add(customer2);

        // Mock service to return mocked objects
        Mockito.when(customerService.getAllCustomers(-1, -1)).thenThrow(new IllegalArgumentException());
        // Test retrieve first page (index 0) with size 1
        mockMvc.perform(get("/customers").param("page", String.format("%d", invalidPage)).param("size", String.format("%d", invalidSize)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Test Success GET All Customers by Name")
    void testGetCustomersByName() throws Exception {
        String name = "Duy";
        // Prepare Mocked data
        ArrayList<Customer> customerArrayList = new ArrayList<>();
        customerArrayList.add(customer1);
        customerArrayList.add(customer2);
        customerArrayList.add(customer3);

        // Mock Request
        Mockito.when(customerService.getAllCustomersByName(name)).thenReturn(new ArrayList<>(customerArrayList.subList(0, 1)));
        // MockMVC HTTP Test
        mockMvc.perform(get("/customers?name={name}", name))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", hasSize(1)))
                .andExpect(jsonPath("$.data[0].id", is(1)));
    }

    @Test
    @DisplayName("Test Fail GET All Customers by Name")
    void testFailGetCustomersByName() throws Exception {
        String invalidName = "Someone";

        // Prepare Mocked data
        ArrayList<Customer> customerArrayList = new ArrayList<>();
        customerArrayList.add(customer1);
        customerArrayList.add(customer2);

        // Mock Request
        Mockito.when(customerService.getAllCustomersByName(invalidName)).thenReturn(new ArrayList<>());

        // MockMVC HTTP Test
        mockMvc.perform(get("/customers?name={name}", invalidName))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", hasSize(0)));
    }

    @Test
    @DisplayName("Test Success GET All Customers by Address")
    void testGetCustomersByAddress() throws Exception {
        String address = "TpHCM";

        // Prepare Mocked data
        ArrayList<Customer> customerArrayList = new ArrayList<>();
        customerArrayList.add(customer1);
        customerArrayList.add(customer2);
        customerArrayList.add(customer3);

        // Mock Request
        Mockito.when(customerService.getAllCustomersByAddress(address)).thenReturn(new ArrayList<>(customerArrayList.subList(0, 1)));

        // MockMVC HTTP Test
        mockMvc.perform(get("/customers?address={address}", address))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", hasSize(1)))
                .andExpect(jsonPath("$.data[0].id", is(1)));
    }

    @Test
    @DisplayName("Test Fail GET All Customers by Address")
    void testFailGetCustomersByAddress() throws Exception {
        String invalidAddress = "TienGiang";

        // Prepare Mocked data
        ArrayList<Customer> customerArrayList = new ArrayList<>();
        customerArrayList.add(customer1);
        customerArrayList.add(customer2);

        // Mock Request
        Mockito.when(customerService.getAllCustomersByAddress(invalidAddress)).thenReturn(new ArrayList<>());

        // MockMVC HTTP Test
        mockMvc.perform(get("/customers?address={address}", invalidAddress))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", hasSize(0)));
    }

    @Test
    @DisplayName("Test Success GET All Customers by Phone")
    void testGetCustomersByPhone() throws Exception {
        String phone = "0326795463";

        // Prepare Mocked data
        ArrayList<Customer> customerArrayList = new ArrayList<>();
        customerArrayList.add(customer1);
        customerArrayList.add(customer2);
        customerArrayList.add(customer3);

        // Mock Request
        Mockito.when(customerService.getAllCustomersByPhone(phone)).thenReturn(new ArrayList<>(customerArrayList.subList(0, 1)));

        // MockMVC HTTP Test
        mockMvc.perform(get("/customers?phone={phone}", phone))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Test Fail GET All Customers by Name")
    void testFailGetCustomersByPhone() throws Exception {
        String invalidPhone = "114341123";

        // Prepare Mocked data
        ArrayList<Customer> customerArrayList = new ArrayList<>();
        customerArrayList.add(customer1);
        customerArrayList.add(customer2);

        // Mock Request
        Mockito.when(customerService.getAllCustomersByPhone(invalidPhone)).thenReturn(new ArrayList<>());

        // MockMVC HTTP Test
        mockMvc.perform(get("/customers?phone={phone}", invalidPhone))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", hasSize(0)));
    }
}