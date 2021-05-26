package com.rmit.demo.service;

import com.rmit.demo.model.Category;
import com.rmit.demo.model.Order;
import com.rmit.demo.repository.OrderRepository;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Autowired
    @InjectMocks
    private OrderService orderService;
    private List<Order> orderList;
    private Order order1;
    private Order order2;
    private Order order3;

    @BeforeEach
    void setUp() throws ParseException {
        String date1 = "26-05-2021";
        String date2 = "27-05-2021";
        String date3 = "28-05-2021";

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        sdf.setLenient(false);

        Date parsedDate1 = sdf.parse(date1);
        Date parsedDate2 = sdf.parse(date2);
        Date parsedDate3 = sdf.parse(date3);

        orderList = new ArrayList<>();
        order1 = new Order(1,parsedDate1);
        order2 = new Order(2,parsedDate2);
        order3 = new Order(3,parsedDate3);
        orderList.add(order1);
        orderList.add(order2);
        orderList.add(order3);
    }

    @AfterEach
    void tearDown() {
        order1 = order2 = order3 = null;
        orderList = null;
    }

    @Test
    @DisplayName("Test GET All Orders")
    void testGetALlOrders() {
        Mockito.when(orderRepository.findAll()).thenReturn(orderList);
        List<Order> orderList1 = orderService.getAll();
        assertEquals(orderList1, orderList);
        Mockito.verify(orderRepository, Mockito.times(1)).findAll();
    }

    @Test
    @DisplayName("Test GET List of Empty Order")
    void testGetEmptyOrders() {
        Mockito.when(orderRepository.findAll()).thenReturn(new ArrayList<>());
        List<Order> orderList1 = orderService.getAll();
        assertEquals(new ArrayList<>(), orderList1);
        Mockito.verify(orderRepository, Mockito.times(1)).findAll();
    }

    @Test
    @DisplayName("Test GET One Order by Id")
    void testGetOne() {
        int id = 1;
        Mockito.when(orderRepository.findById(id)).thenReturn(Optional.of(order1));
        Order foundOrder = orderService.getOne(id);
        assertEquals(foundOrder, order1);
    }

    @Test
    @DisplayName("Test Failed GET One Order by Invalid Id")
    void testFailGetOne() {
        int id = 1;
        int invalidId=99;
        Mockito.when(orderRepository.findById(invalidId)).thenReturn(null);
        assertThrows(NullPointerException.class, () -> orderService.getOne(invalidId));
    }

    @Test
    @DisplayName("Test Success CREATE method for Order")
    void testSaveOne() {
        Mockito.when(orderRepository.saveAndReset(order1)).thenReturn(order1);
        Order savedOrder = orderService.saveOne(order1);
        assertEquals(order1, savedOrder);
    }

    @Test
    @DisplayName("Test Success UPDATE method for Order")
    void testUpdateOne() throws ParseException {
        // Set up sample date for testing
        String updateDate = "30-05-2021";
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        sdf.setLenient(false);
        Date parsedUpdateDate = sdf.parse(updateDate);

        // Mocked object
        Order updatedOne = new Order(1, parsedUpdateDate);
        Mockito.when(orderRepository.findById(updatedOne.getId())).thenReturn(Optional.of(order1));
        Mockito.when(orderRepository.saveAndReset(order1)).thenReturn(updatedOne);
        Order result = orderService.updateOne(1, updatedOne);
        assertEquals(result.getDate(), updatedOne.getDate());
    }

    @Test
    @DisplayName("Test Failed UPDATE method for Category")
    void testFailUpdateOne() throws ParseException {
        // Set up sample date for testing
        String updateDate = "30-05-2021";
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        sdf.setLenient(false);
        Date parsedUpdateDate = sdf.parse(updateDate);

        // Mocked
        int validId = 1;
        int invalidId = 99;
        Order updatedOne = new Order(invalidId, parsedUpdateDate);

        Mockito.lenient().when(orderRepository.findById(ArgumentMatchers.eq(validId))).thenReturn(Optional.of(order1));
        Mockito.lenient().when(orderRepository.saveAndReset(order1)).thenReturn(updatedOne);
        assertThrows(NullPointerException.class, () -> orderService.updateOne(invalidId, updatedOne));
    }

    @Test
    @DisplayName("Test Success DELETE method for Order")
    void testDeleteOne() {
        int validId = 1;
        doReturn(Optional.of(order1)).when(orderRepository).findById(validId);
        doAnswer(i -> {return null;}).when(orderRepository).delete(order1);
        int result = orderService.deleteOne(validId);
        assertEquals(validId, result);
    }

    @Test
    @DisplayName("Test Failed DELETE method for Order with invalid Id")
    void testFailDeleteOne() {
        int validId = 1;
        int invalidId = 2;
        doReturn(Optional.of(order1)).when(orderRepository).findById(validId);
        doAnswer(i -> {return null;}).when(orderRepository).delete(order1);
        int result = orderService.deleteOne(validId);
        assertEquals(validId, result);
        assertThrows(NullPointerException.class, () -> orderService.deleteOne(invalidId));
    }
}
