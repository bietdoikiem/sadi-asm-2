package com.rmit.demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rmit.demo.config.RestExceptionHandler;
import com.rmit.demo.model.Order;
import com.rmit.demo.repository.OrderRepository;
import com.rmit.demo.service.OrderService;
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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.text.SimpleDateFormat;
import java.util.*;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.intThat;
import static org.mockito.ArgumentMatchers.isA;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class OrderControllerTest {

    @Mock
    protected OrderService orderService;
    @Mock
    protected OrderRepository orderRepository;

    @InjectMocks
    protected OrderController orderController;

    @Autowired
    protected MockMvc mockMvc;

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
                .standaloneSetup(orderController)
                .setControllerAdvice(new RestExceptionHandler())
                .build();
        Mockito.reset();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    @DisplayName("Test GET all Order list")
    void testGetAll() throws Exception {
        // Set up sample date for testing
        String date1 = "26-05-2021";
        String date2 = "27-05-2021";
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        sdf.setLenient(false);
        Date parsedDate1 = sdf.parse(date1);
        Date parsedDate2 = sdf.parse(date2);

        int hoursDiff = parsedDate1.getHours() - parsedDate1.getTimezoneOffset() / 60;
        int minutesDiff = (parsedDate1.getHours() - parsedDate1.getTimezoneOffset()) % 60;
        parsedDate1.setHours(hoursDiff);
        parsedDate1.setMinutes(minutesDiff);

        hoursDiff = parsedDate2.getHours() - parsedDate2.getTimezoneOffset() / 60;
        minutesDiff = (parsedDate2.getHours() - parsedDate2.getTimezoneOffset()) % 60;
        parsedDate2.setHours(hoursDiff);
        parsedDate2.setMinutes(minutesDiff);

        List<Order> orderList = new ArrayList<>();
        orderList.add(new Order(1, parsedDate1));
        orderList.add(new Order(2, parsedDate2));

        Mockito.when(orderService.getAll()).thenReturn(orderList);

        String url = "/orders";
        mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", hasSize(2)))
                .andExpect(jsonPath("$.data[0].id", is(1)))
                .andExpect(jsonPath("$.data[0].date", is("26-05-2021")))
                .andExpect(jsonPath("$.data[1].id", is(2)))
                .andExpect(jsonPath("$.data[1].date", is("27-05-2021")))
                .andReturn();
    }

    @Test
    @DisplayName("Test GET empty Order list")
    void testGetAllEmpty() throws Exception {

        List<Order> orderList = new ArrayList<>();

        Mockito.when(orderService.getAll()).thenReturn(orderList);

        String url = "/orders";
        MvcResult mvcResult = mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", hasSize(0)))
                .andReturn();
    }

    @Test
    @DisplayName("Test GET one Order by Id")
    void testGetOne() throws Exception {
        String date1 = "26-05-2021";
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        sdf.setLenient(false);
        Date parsedDate1 = sdf.parse(date1);
        int hoursDiff = parsedDate1.getHours() - parsedDate1.getTimezoneOffset() / 60;
        int minutesDiff = (parsedDate1.getHours() - parsedDate1.getTimezoneOffset()) % 60;
        parsedDate1.setHours(hoursDiff);
        parsedDate1.setMinutes(minutesDiff);

        int id = 1;
        Order order = new Order(id, parsedDate1);

        Mockito.when(orderService.getOne(id)).thenReturn(order);

        String url = "/orders/{id}";
        mockMvc.perform(get(url, id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id", is(id)))
                .andExpect(jsonPath("$.data.date", is("26-05-2021")));
    }

    @Test
    @DisplayName("Test GET Null Resource by Unknown ID")
    void testGetOneEmpty() throws Exception {
        int id = 5;
        Mockito.when(orderService.getOne(5)).thenReturn(null);
        String url = "/orders/{id}";
        mockMvc.perform(get(url, 5))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Test CREATE One Order")
    void testSaveOne() throws Exception {
        String date1 = "26-05-2021";
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        sdf.setLenient(false);
        Date parsedDate1 = sdf.parse(date1);
        int hoursDiff = parsedDate1.getHours() - parsedDate1.getTimezoneOffset() / 60;
        int minutesDiff = (parsedDate1.getHours() - parsedDate1.getTimezoneOffset()) % 60;
        parsedDate1.setHours(hoursDiff);
        parsedDate1.setMinutes(minutesDiff);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("date", "26-05-2021");
        String requestJson = asJsonString(requestBody);

        String url = "/orders";
        Mockito.when(orderService.saveOne(isA(Order.class))).
                thenReturn(new Order(1, parsedDate1));

        mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Test CREATE method Bad Request with wrong response format (wrong date field)")
    void testSaveOneError() throws Exception {
        String date1 = "26-05-2021";
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        sdf.setLenient(false);
        Date parsedDate1 = sdf.parse(date1);
        int hoursDiff = parsedDate1.getHours() - parsedDate1.getTimezoneOffset() / 60;
        int minutesDiff = (parsedDate1.getHours() - parsedDate1.getTimezoneOffset()) % 60;
        parsedDate1.setHours(hoursDiff);
        parsedDate1.setMinutes(minutesDiff);

        Map<String, Object> requestBody = new HashMap<>();
        // Redundant Id to Request Body for creating new Category
        requestBody.put("id", 1);
        requestBody.put("birth date", "26-05-2021");

        String requestJson = asJsonString(requestBody);
        Order order = new Order(1, parsedDate1);

        // Request Object

        Mockito.lenient().when(orderService.saveOne(order))
                .thenReturn(order);

        String url = "/orders";
        mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @DisplayName("Test success UPDATE method for Order")
    void testUpdateOne() throws Exception {
        // Set up sample date for testing
        String date1 = "26-05-2021";
        String date2 = "27-05-2021";
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        sdf.setLenient(false);
        Date parsedDate1 = sdf.parse(date1);
        Date parsedDate2 = sdf.parse(date2);

        int id = 1;
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("date", "27-05-2021");
        String requestJson = asJsonString(requestBody);

        // Mocked Request Object
        Order order = new Order(id, parsedDate1);
        Order updatedOrder = new Order(id, parsedDate2);
        // Mocked responses
        Mockito.lenient().when(orderRepository.findById(id)).thenReturn(Optional.of(order));
        Mockito.lenient().when(orderService.updateOne(intThat(i -> i == id), isA(Order.class))).thenReturn(updatedOrder);
        mockMvc.perform(put("/orders/{id}", order.getId()).contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Test failed UPDATE method for Order when input invalid Order's Id")
    void testFailUpdateOne() throws Exception {
        // Set up sample date for testing
        String date1 = "26-05-2021";
        String date2 = "27-05-2021";
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        sdf.setLenient(false);
        Date parsedDate1 = sdf.parse(date1);
        Date parsedDate2 = sdf.parse(date2);

        int validId = 1;
        int invalidId = 2;
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("date", "27-05-2021");
        String requestJson = asJsonString(requestBody);
        // Mocked Object
        Order order = new Order(1, parsedDate1);
        Order updatedOrder = new Order(1, parsedDate2);
        // Mocked response
        Mockito.lenient().when(orderRepository.findById(validId)).thenReturn(Optional.of(order)); // valid object
        Mockito.lenient().when(orderService.updateOne(intThat(id -> id == validId), isA(Order.class))).thenReturn(updatedOrder); // mock update request
        Mockito.lenient().when(orderService.updateOne(intThat(id -> id == invalidId), isA(Order.class))).thenThrow(new NullPointerException()); // mock update request
        // Perform Operation on valid entity successfully
        mockMvc.perform(put("/orders/{id}", validId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson)
        ).andExpect(status().isOk());
        // Update Operation fail if invalid Id is provided
        mockMvc.perform(put("/orders/{id}", invalidId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson)
        ).andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Test Success DELETE Method")
    void testDeleteOne() throws Exception {
        // Set up sample date for object
        String date1 = "26-05-2021";
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        sdf.setLenient(false);
        Date parsedDate1 = sdf.parse(date1);

        int id = 1;
        Order order = new Order(id, parsedDate1);

        Mockito.lenient().when(orderRepository.findById(id)).thenReturn(Optional.of(order));
        Mockito.lenient().when(orderService.deleteOne(id)).thenReturn(id);

        mockMvc.perform(delete("/orders/{id}", order.getId()))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.url", is("/orders/" + order.getId())));
    }

    @Test
    @DisplayName("Test Failed DELETE method for invalid id")
    void testFailDeleteOne() throws Exception {
        // Set up sample for object
        String date1 = "26-05-2021";
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        sdf.setLenient(false);
        Date parsedDate1 = sdf.parse(date1);

        int validId = 1;
        int invalidId = 2;
        Order order = new Order(validId, parsedDate1);

        // Return successfully if return valid id
        Mockito.when(orderService.deleteOne(validId)).thenReturn(validId);
        Mockito.when(orderService.deleteOne(invalidId)).thenThrow(new NullPointerException());

        // If retrieved valid Id, the operation is successful
        mockMvc.perform(delete("/orders/{id}", validId))
                .andExpect(status().isAccepted());
        // Else fail DELETE operation
        mockMvc.perform(delete("/orders/{id}", invalidId))
                .andExpect(status().isNotFound());
    }
}
