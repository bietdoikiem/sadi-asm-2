package com.rmit.demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rmit.demo.config.RestExceptionHandler;
import com.rmit.demo.model.Order;
import com.rmit.demo.model.ReceiveDetail;
import com.rmit.demo.model.ReceivingNote;
import com.rmit.demo.repository.OrderRepository;
import com.rmit.demo.repository.ReceivingNoteRepository;
import com.rmit.demo.service.OrderService;
import com.rmit.demo.service.ReceivingNoteService;
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
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class ReceivingNoteControllerTest {

    @MockBean
    protected ReceivingNoteService receivingNoteService;
    @MockBean
    protected ReceivingNoteRepository receivingNoteRepository;

    @Autowired
    @InjectMocks
    protected ReceivingNoteController receivingNoteController;

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
                .standaloneSetup(receivingNoteController)
                .setControllerAdvice(new RestExceptionHandler())
                .build();
        Mockito.reset();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    @DisplayName("Test GET all Receiving note list")
    void testGetAll() throws Exception {
        // Set up sample date for object
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

        List<ReceivingNote> receivingNoteList = new ArrayList<>();
        receivingNoteList.add(new ReceivingNote(1, parsedDate1));
        receivingNoteList.add(new ReceivingNote(2, parsedDate2));

        Mockito.when(receivingNoteService.getAll()).thenReturn(receivingNoteList);

        String url = "/receiving-notes";
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
    @DisplayName("Test GET empty Receiving note list")
    void testGetAllEmpty() throws Exception {

        List<ReceivingNote> receivingNoteList = new ArrayList<>();

        Mockito.when(receivingNoteService.getAll()).thenReturn(receivingNoteList);

        String url = "/receiving-notes";
        MvcResult mvcResult = mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", hasSize(0)))
                .andReturn();
    }

    @Test
    @DisplayName("Test GET one Receiving Note by Id")
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
        ReceivingNote receivingNote = new ReceivingNote(id, parsedDate1);

        Mockito.when(receivingNoteService.getOne(id)).thenReturn(receivingNote);

        String url = "/receiving-notes/{id}";
        mockMvc.perform(get(url, id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id", is(id)))
                .andExpect(jsonPath("$.data.date", is("26-05-2021")));
    }

    @Test
    @DisplayName("Test GET Null Resource by Unknown ID")
    void testGetOneEmpty() throws Exception {
        int id = 5;
        Mockito.when(receivingNoteService.getOne(5)).thenReturn(null);
        String url = "/receiving-notes/{id}";
        mockMvc.perform(get(url, 5))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Test CREATE One Receiving Note")
    void testSaveOne() throws Exception {
        // Set up date for object
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

        String url = "/receiving-notes";
        Mockito.when(receivingNoteService.saveOne(isA(ReceivingNote.class))).
                thenReturn(new ReceivingNote(1, parsedDate1));

        mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Test CREATE method Bad Request with wrong response format (wrong date field)")
    void testSaveOneError() throws Exception {
        // Set up date for object
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
        ReceivingNote receivingNote = new ReceivingNote(1, parsedDate1);

        // Request Object

        Mockito.lenient().when(receivingNoteService.saveOne(receivingNote))
                .thenReturn(receivingNote);

        String url = "/receiving-notes";
        mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @DisplayName("Test success UPDATE method for Receiving note")
    void testUpdateOne() throws Exception {
        // Set up sample date for object
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
        ReceivingNote receivingNote = new ReceivingNote(id, parsedDate1);
        ReceivingNote updatedReceivingNote = new ReceivingNote(id, parsedDate2);
        // Mocked responses
        Mockito.lenient().when(receivingNoteRepository.findById(id)).thenReturn(Optional.of(receivingNote));
        Mockito.lenient().when(receivingNoteService.updateOne(intThat(i -> i == id), isA(ReceivingNote.class))).thenReturn(updatedReceivingNote);
        mockMvc.perform(put("/receiving-notes/{id}", receivingNote.getId()).contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Test failed UPDATE method for Receiving Note when input invalid Receiving Note's Id")
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
        ReceivingNote receivingNote = new ReceivingNote(1, parsedDate1);
        ReceivingNote updatedReceivingNote = new ReceivingNote(1, parsedDate2);
        // Mocked response
        Mockito.lenient().when(receivingNoteRepository.findById(validId)).thenReturn(Optional.of(receivingNote)); // valid object
        Mockito.lenient().when(receivingNoteService.updateOne(intThat(id -> id == validId), isA(ReceivingNote.class))).thenReturn(updatedReceivingNote); // mock update request
        Mockito.lenient().when(receivingNoteService.updateOne(intThat(id -> id == invalidId), isA(ReceivingNote.class))).thenThrow(new NullPointerException()); // mock update request
        // Perform Operation on valid entity successfully
        mockMvc.perform(put("/receiving-notes/{id}", validId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson)
        ).andExpect(status().isOk());
        // Update Operation fail if invalid Id is provided
        mockMvc.perform(put("/receiving-notes/{id}", invalidId)
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
        ReceivingNote receivingNote = new ReceivingNote(id, parsedDate1);

        Mockito.lenient().when(receivingNoteRepository.findById(id)).thenReturn(Optional.of(receivingNote));
        Mockito.lenient().when(receivingNoteService.deleteOne(id)).thenReturn(id);

        mockMvc.perform(delete("/receiving-notes/{id}", receivingNote.getId()))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.url", is("/receiving-notes/" + receivingNote.getId())));
    }

    @Test
    @DisplayName("Test Failed DELETE method for invalid id")
    void testFailDeleteOne() throws Exception {
        // Set up date for object
        String date1 = "26-05-2021";
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        sdf.setLenient(false);
        Date parsedDate1 = sdf.parse(date1);

        int validId = 1;
        int invalidId = 2;
        ReceivingNote receivingNote = new ReceivingNote(validId, parsedDate1);

        // Return successfully if return valid id
        Mockito.when(receivingNoteService.deleteOne(validId)).thenReturn(validId);
        Mockito.when(receivingNoteService.deleteOne(invalidId)).thenThrow(new NullPointerException());

        // If retrieved valid Id, the operation is successful
        mockMvc.perform(delete("/receiving-notes/{id}", validId))
                .andExpect(status().isAccepted());
        // Else fail DELETE operation
        mockMvc.perform(delete("/receiving-notes/{id}", invalidId))
                .andExpect(status().isNotFound());
    }

}
