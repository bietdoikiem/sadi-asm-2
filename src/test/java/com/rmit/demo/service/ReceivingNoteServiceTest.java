package com.rmit.demo.service;

import com.rmit.demo.model.ReceiveDetail;
import com.rmit.demo.model.ReceivingNote;
import com.rmit.demo.repository.ReceivingNoteRepository;
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
public class ReceivingNoteServiceTest {

    @Mock
    private ReceivingNoteRepository receivingNoteRepository;

    @Autowired
    @InjectMocks
    private ReceivingNoteService receivingNoteService;
    private List<ReceivingNote> receivingNoteList;
    private ReceivingNote receivingNote1;
    private ReceivingNote receivingNote2;
    private ReceivingNote receivingNote3;

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

        receivingNoteList = new ArrayList<>();
        receivingNote1 = new ReceivingNote(1, parsedDate1);
        receivingNote2 = new ReceivingNote(2, parsedDate2);
        receivingNote3 = new ReceivingNote(3, parsedDate3);
        receivingNoteList.add(receivingNote1);
        receivingNoteList.add(receivingNote2);
        receivingNoteList.add(receivingNote3);
    }

    @AfterEach
    void tearDown() {
        receivingNote1 = receivingNote2 = receivingNote3 = null;
        receivingNoteList = null;
    }

    @Test
    @DisplayName("Test GET All Receiving Notes")
    void testGetALlReceivingNotes() {
        Mockito.when(receivingNoteRepository.findAll()).thenReturn(receivingNoteList);
        List<ReceivingNote> receivingNoteList1 = receivingNoteService.getAll();
        assertEquals(receivingNoteList1, receivingNoteList);
        Mockito.verify(receivingNoteRepository, Mockito.times(1)).findAll();
    }

    @Test
    @DisplayName("Test GET List of Empty Receiving Note")
    void testGetEmptyReceivingNotes() {
        Mockito.when(receivingNoteRepository.findAll()).thenReturn(new ArrayList<>());
        List<ReceivingNote> receivingNoteList1 = receivingNoteService.getAll();
        assertEquals(new ArrayList<>(), receivingNoteList1);
        Mockito.verify(receivingNoteRepository, Mockito.times(1)).findAll();
    }

    @Test
    @DisplayName("Test GET One Receiving Note by Id")
    void testGetOne() {
        int id = 1;
        Mockito.when(receivingNoteRepository.findById(id)).thenReturn(Optional.of(receivingNote1));
        ReceivingNote foundReceivingNote = receivingNoteService.getOne(id);
        assertEquals(foundReceivingNote, receivingNote1);
    }

    @Test
    @DisplayName("Test Failed GET One Receiving Note by Invalid Id")
    void testFailGetOne() {
        int id = 1;
        int invalidId = 99;
        Mockito.when(receivingNoteRepository.findById(invalidId)).thenReturn(null);
        assertThrows(NullPointerException.class, () -> receivingNoteService.getOne(invalidId));
    }

    // TEST SAVE ONE
    @Test
    @DisplayName("Test Success CREATE method for Receiving Note")
    void testSaveOne() {
        // Prepare Mock Data
        ReceiveDetail receiveDetail1 = new ReceiveDetail(1, 3);
        ReceiveDetail receiveDetail2 = new ReceiveDetail(2, 5);

        List<ReceiveDetail> receiveDetailList = new ArrayList<>();
        receiveDetailList.add(receiveDetail1);
        receiveDetailList.add(receiveDetail2);
        receivingNote1.setReceiveDetailList(receiveDetailList);

        Mockito.when(receivingNoteRepository.saveAndReset(receivingNote1)).thenReturn(receivingNote1);
        ReceivingNote savedReceivingNote = receivingNoteService.saveOne(receivingNote1);
        assertEquals(receivingNote1, savedReceivingNote);
    }

    @Test
    @DisplayName("Test Success UPDATE method for Receiving Note")
    void testUpdateOne() throws ParseException {
        // Set up sample date for testing
        String updateDate = "30-05-2021";
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        sdf.setLenient(false);
        Date parsedUpdateDate = sdf.parse(updateDate);

        // Mocked object
        ReceivingNote updatedOne = new ReceivingNote(1, parsedUpdateDate);
        Mockito.when(receivingNoteRepository.findById(updatedOne.getId())).thenReturn(Optional.of(receivingNote1));
        Mockito.when(receivingNoteRepository.saveAndReset(receivingNote1)).thenReturn(updatedOne);
        ReceivingNote result = receivingNoteService.updateOne(1, updatedOne);
        assertEquals(result.getDate(), updatedOne.getDate());
    }

    @Test
    @DisplayName("Test Failed UPDATE method for Receiving Note")
    void testFailUpdateOne() throws ParseException {
        // Set up sample date for testing
        String updateDate = "30-05-2021";
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        sdf.setLenient(false);
        Date parsedUpdateDate = sdf.parse(updateDate);

        // Mocked
        int validId = 1;
        int invalidId = 99;
        ReceivingNote updatedOne = new ReceivingNote(invalidId, parsedUpdateDate);

        Mockito.lenient().when(receivingNoteRepository.findById(ArgumentMatchers.eq(validId))).thenReturn(Optional.of(receivingNote1));
        Mockito.lenient().when(receivingNoteRepository.saveAndReset(receivingNote1)).thenReturn(updatedOne);
        assertThrows(NullPointerException.class, () -> receivingNoteService.updateOne(invalidId, updatedOne));
    }

    @Test
    @DisplayName("Test Success DELETE method for Receiving Note")
    void testDeleteOne() {
        int validId = 1;
        doReturn(Optional.of(receivingNote1)).when(receivingNoteRepository).findById(validId);
        doAnswer(i -> {
            return null;
        }).when(receivingNoteRepository).delete(receivingNote1);
        int result = receivingNoteService.deleteOne(validId);
        assertEquals(validId, result);
    }

    @Test
    @DisplayName("Test Failed DELETE method for Receiving Note with invalid Id")
    void testFailDeleteOne() {
        int validId = 1;
        int invalidId = 2;
        doReturn(Optional.of(receivingNote1)).when(receivingNoteRepository).findById(validId);
        doAnswer(i -> {return null;}).when(receivingNoteRepository).delete(receivingNote1);
        int result = receivingNoteService.deleteOne(validId);
        assertEquals(validId, result);
        assertThrows(NullPointerException.class, () -> receivingNoteService.deleteOne(invalidId));
    }

}
