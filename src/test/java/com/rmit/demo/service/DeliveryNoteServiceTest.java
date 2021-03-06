package com.rmit.demo.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rmit.demo.config.RestExceptionHandler;
import com.rmit.demo.model.*;
import com.rmit.demo.repository.DeliveryDetailRepository;
import com.rmit.demo.repository.DeliveryNoteRepository;
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
class DeliveryNoteServiceTest {

    @Mock
    private DeliveryNoteRepository deliveryNoteRepository;

    @Mock
    private DeliveryDetailRepository deliveryDetailRepository;

    @Autowired
    @InjectMocks
    private DeliveryNoteService deliveryNoteService;
    // Defined Mock Objects
    protected DeliveryNote deliveryNote1;
    protected DeliveryNote deliveryNote2;
    protected Staff staff1;
    protected Staff staff2;
    protected List<DeliveryNote> deliveryNoteList;

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
        // Define mock objects
        staff1 = new Staff(1, "Minh Ng", "23 Jump Street",
                "0934717924", "s3812649@rmit.edu.vn");
        staff2 = new Staff(2, "Kha Bui", "06 Dance Street",
                "0702599925", "s3812669@rmit.edu.vn");
        Date date1 = DateUtils.parseDate("25-05-2021");
        Date date2 = DateUtils.parseDate("28-05-2021");
        deliveryNote1 = new DeliveryNote(1, date1, staff1);
        deliveryNote2 = new DeliveryNote(2, date2, staff2);
    }

    @AfterEach
    void tearDown() {
        staff1 = staff2 = null;
        deliveryNote1 = deliveryNote2 = null;
    }


    @Test
    @DisplayName("Test Success GET All DeliveryNote")
    void testGetAll() {
        // Prepare Mock Data
        List<DeliveryNote> deliveryNoteList = new ArrayList<>();
        deliveryNoteList.add(deliveryNote1);
        deliveryNoteList.add(deliveryNote2);
        // Prepare Mock Request
        Mockito.when(deliveryNoteRepository.findAll()).thenReturn(deliveryNoteList);
        // Verify and Assert Service call
        List<DeliveryNote> deliveryNotes = deliveryNoteService.getAll();
        assertEquals(2, deliveryNotes.size());
    }

    @Test
    @DisplayName("Test Empty GET All DeliveryNote")
    void testEmptyGetAll() {
        // Prepare Mock Data
        List<DeliveryNote> deliveryNoteList = new ArrayList<>();
        // Prepare Mock Request
        Mockito.when(deliveryNoteRepository.findAll()).thenReturn(deliveryNoteList);
        List<DeliveryNote> deliveryNotes = deliveryNoteService.getAll();
        assertEquals(0,deliveryNoteList.size());
    }

    @Test
    @DisplayName("Test GET ALL DeliveryNote by Pagination")
    void testPaginationGetAll() {
        // Prepare Mock Data
        List<DeliveryNote> deliveryNoteList = new ArrayList<>();
        deliveryNoteList.add(deliveryNote1);
        deliveryNoteList.add(deliveryNote2);
        // Mock Request
        Pageable pageable = PageRequest.of(0, 1);
        Page<DeliveryNote> deliveryNotePage = new PageImpl<>(deliveryNoteList.subList(0, 1));
        Mockito.when(deliveryNoteRepository.findAll(pageable)).thenReturn(deliveryNotePage);
        // Verify & Assertion
        List<DeliveryNote> mockedList = deliveryNoteService.getAll(0, 1);
        assertEquals(mockedList.size(), deliveryNoteList.subList(0, 1).size());
        Mockito.verify(deliveryNoteRepository, times(1)).findAll(pageable);
    }

    @Test
    @DisplayName("Test Empty GET ALL DeliveryNote by Pagination.")
    void testEmptyPaginationGetAll() {
        // Prepare Mock Data
        List<DeliveryNote> deliveryNoteList = new ArrayList<>();
        deliveryNoteList.add(deliveryNote1);
        deliveryNoteList.add(deliveryNote2);
        // Mock Request
        Pageable pageable = PageRequest.of(0, 1);
        Page<DeliveryNote> deliveryNotePage = new PageImpl<>(new ArrayList<>());
        Mockito.when(deliveryNoteRepository.findAll(pageable)).thenReturn(deliveryNotePage);
        // Verify & Assertion
        List<DeliveryNote> mockedList = deliveryNoteService.getAll(0, 1);
        assertEquals(mockedList.size(), deliveryNotePage.getContent().size());
        Mockito.verify(deliveryNoteRepository, times(1)).findAll(pageable);
    }

    @Test
    @DisplayName("Test Success GET One DeliveryNote by Id")
    void testGetOne() {
        int validID = 1;
        // Prepare Mock Request
        Mockito.when(deliveryNoteRepository.findById(validID)).thenReturn(Optional.of(deliveryNote1));
        // Verify And Assert
        DeliveryNote deliveryNote = deliveryNoteService.getOne(validID);
        assertEquals(deliveryNote.getId(), validID);
        verify(deliveryNoteRepository, times(1)).findById(validID);
    }

    @Test
    @DisplayName("Test Failed GET One DeliveryNote by Invalid Id")
    void testFailGetOne() {
        int invalidID = 99;
        // Prepare Mock Request
        Mockito.when(deliveryNoteRepository.findById(invalidID)).thenThrow(new NullPointerException());
        // Verify And Assert
        assertThrows(NullPointerException.class, () -> deliveryNoteService.getOne(invalidID));
        verify(deliveryNoteRepository, times(1)).findById(invalidID);
    }

    @Test
    @DisplayName("Test Success CREATE One DeliveryNote")
    void testSaveOne() {
        // Prepare Mock Data
        Category category = new Category(1, "Classic Sneaker");
        Product product1 = new Product(1, "Nike Air Force 1", "Air Force", "Nike", "Nike Co.", "Best Classic Sneaker", 200, category);
        Product product2 = new Product(2, "Nike Mamba 9X", "Mamba", "Nike", "Nike Co.", "Best Nike Of All Time", 250, category);
        List<DeliveryDetail> deliveryDetailList = new ArrayList<>();
        deliveryDetailList.add(new DeliveryDetail(product1, 2));
        deliveryDetailList.add(new DeliveryDetail(product2, 1));
        deliveryNote1.setDeliveryDetailList(deliveryDetailList);
        // Mock Request
        Mockito.when(deliveryNoteRepository.saveAndReset(deliveryNote1)).thenReturn(deliveryNote1);
        // Assert and Verify
        DeliveryNote saved = deliveryNoteService.saveOne(deliveryNote1);
        assertEquals(saved.getId(), deliveryNote1.getId());
        verify(deliveryNoteRepository, times(1)).saveAndReset(deliveryNote1);
    }

    @Test
    @DisplayName("Test Success UPDATE One DeliveryNote (Update DATE)")
    void testUpdateOne() {
        // Prepare Mock Data
        Category category = new Category(1, "Classic Sneaker");
        Product product1 = new Product(1, "Nike Air Force 1", "Air Force", "Nike", "Nike Co.", "Best Classic Sneaker", 200, category);
        Product product2 = new Product(2, "Nike Mamba 9X", "Mamba", "Nike", "Nike Co.", "Best Nike Of All Time", 250, category);
        List<DeliveryDetail> deliveryDetailList = new ArrayList<>();
        deliveryDetailList.add(new DeliveryDetail(product1, 2));
        deliveryDetailList.add(new DeliveryDetail(product2, 1));
        deliveryNote1.setDeliveryDetailList(deliveryDetailList);
        // Mock Request
        DeliveryNote updatedDeliveryNote = new DeliveryNote(1, DateUtils.parseDate("07-07-2021"), staff1);
        updatedDeliveryNote.setDeliveryDetailList(deliveryDetailList);
        Mockito.when(deliveryNoteRepository.saveAndReset(isA(DeliveryNote.class))).thenReturn(updatedDeliveryNote);
        // Verify service call
        DeliveryNote updatedOne = deliveryNoteService.saveOne(updatedDeliveryNote);
        assertEquals(updatedOne.getDate(), DateUtils.parseDate("07-07-2021"));
        verify(deliveryNoteRepository, times(1)).saveAndReset(updatedDeliveryNote);
    }

    @Test
    @DisplayName("Test Fail UPDATE One DeliveryNote by inputting wrong Id")
    void testFailUpdateOne() {
        // Prepare Mocked data
        int invalidID = 99;
        Category category = new Category(1, "Classic Sneaker");
        Product product1 = new Product(1, "Nike Air Force 1", "Air Force", "Nike", "Nike Co.", "Best Classic Sneaker", 200, category);
        Product product2 = new Product(2, "Nike Mamba 9X", "Mamba", "Nike", "Nike Co.", "Best Nike Of All Time", 250, category);
        List<DeliveryDetail> deliveryDetailList = new ArrayList<>();
        deliveryDetailList.add(new DeliveryDetail(product1, 2));
        deliveryDetailList.add(new DeliveryDetail(product2, 1));
        deliveryNote1.setDeliveryDetailList(deliveryDetailList);
        // Mock Request
        DeliveryNote updatedDeliveryNote = new DeliveryNote(1, DateUtils.parseDate("07-07-2021"), staff1);
        updatedDeliveryNote.setDeliveryDetailList(deliveryDetailList);
        // Throw Error when found invalid & non-existing Id of DeliveryNote
        Mockito.when(deliveryNoteRepository.findById(invalidID)).thenThrow(new NullPointerException());
        // Verify service call
        assertThrows(NullPointerException.class, () -> deliveryNoteService.updateOne(invalidID, updatedDeliveryNote));
        verify(deliveryNoteRepository, times(1)).findById(invalidID);
    }


    @Test
    @DisplayName("Test Success DELETE one DeliveryNote")
    void testDeleteOne() {
        // Mock request
        Mockito.when(deliveryNoteRepository.findById(1)).thenReturn(Optional.of(deliveryNote1));
        Mockito.doNothing().when(deliveryNoteRepository).delete(deliveryNote1);
        // Verify delete request & assertion service
        int result = deliveryNoteService.deleteOne(1);
        assertEquals(result, deliveryNote1.getId());
        verify(deliveryNoteRepository, times(1)).delete(deliveryNote1);
    }

    @Test
    @DisplayName("Test Fail DELETE one DeliveryNote by inputting wrong ID format")
    void testFailDeleteOne() {
        // Prepare Mock Data
        int invalidId = 99;
        // Mock fail request when finding and deleting invalidID
        Mockito.when(deliveryNoteRepository.findById(invalidId)).thenThrow(new NullPointerException());
        // Verify delete request & assertion service
        assertThrows(NullPointerException.class, () -> deliveryNoteService.deleteOne(invalidId));
        verify(deliveryNoteRepository, times(0)).delete(deliveryNote1);
    }

    @Test
    @DisplayName("Test Success GET All DeliveryDetail by DeliveryNote's Id")
    void testGetAllDeliveryDetailsByDeliveryNoteId() {
        // Prepare Mocked data
        int validId = 1;
        Category category = new Category(1, "Classic Sneaker");
        Product product1 = new Product(1, "Nike Air Force 1", "Air Force", "Nike", "Nike Co.", "Best Classic Sneaker", 200, category);
        Product product2 = new Product(2, "Nike Mamba 9X", "Mamba", "Nike", "Nike Co.", "Best Nike Of All Time", 250, category);
        List<DeliveryDetail> deliveryDetailList = new ArrayList<>();
        deliveryDetailList.add(new DeliveryDetail(product1, 2));
        deliveryDetailList.add(new DeliveryDetail(product2, 1));
        deliveryNote1.setDeliveryDetailList(deliveryDetailList);
        // Mock request
        Mockito.when(deliveryNoteRepository.findById(validId)).thenReturn(Optional.of(deliveryNote1));
        Mockito.when(deliveryDetailRepository.findDeliveryDetailsByDeliveryNote(deliveryNote1)).thenReturn(deliveryDetailList);
        // Verify & Assertion
        List<DeliveryDetail> retrievedList = deliveryNoteService.getAllDeliveryDetailsByDeliveryNoteId(validId);
        assertEquals(deliveryDetailList, retrievedList);
        verify(deliveryNoteRepository, times(1)).findById(validId);
        verify(deliveryDetailRepository, times(1)).findDeliveryDetailsByDeliveryNote(deliveryNote1);
    }

    @Test
    @DisplayName("Test Fail GET All DeliveryDetail by DeliveryNote's Id")
    void testFailGetAllDeliveryDetailsByDeliveryNoteId() {
        // Prepare Mocked data
        int validId = 1;
        int invalidId = 99;
        Category category = new Category(1, "Classic Sneaker");
        Product product1 = new Product(1, "Nike Air Force 1", "Air Force", "Nike", "Nike Co.", "Best Classic Sneaker", 200, category);
        Product product2 = new Product(2, "Nike Mamba 9X", "Mamba", "Nike", "Nike Co.", "Best Nike Of All Time", 250, category);
        List<DeliveryDetail> deliveryDetailList = new ArrayList<>();
        deliveryDetailList.add(new DeliveryDetail(product1, 2));
        deliveryDetailList.add(new DeliveryDetail(product2, 1));
        deliveryNote1.setDeliveryDetailList(deliveryDetailList);
        // Mock request
        Mockito.when(deliveryNoteRepository.findById(invalidId)).thenThrow(new NullPointerException());
        // Verify & Assertion
        assertThrows(NullPointerException.class, () -> deliveryNoteService.getAllDeliveryDetailsByDeliveryNoteId(invalidId));
        verify(deliveryNoteRepository, times(1)).findById(invalidId);
    }

    @Test
    @DisplayName("Test Success FILTER All DeliveryNote in a period")
    void testFilterByPeriod() {
        // Prepare Mock Data
        List<DeliveryNote> deliveryNoteList = new ArrayList<>();
        deliveryNoteList.add(deliveryNote1);
        deliveryNoteList.add(deliveryNote2);
        // Mock Request
        Date startDate = DateUtils.parseDate("25-05-2021 00:00:00");
        Date endDate = DateUtils.parseDate("28-05-2021 23:59:59");
        Date normStartDate = DateUtils.normalizeDateAtStart(startDate);
        Date normEndDate = DateUtils.normalizeDateAtEnd(endDate);
        Mockito.when(deliveryNoteRepository.findAllByDateBetween(normStartDate, normEndDate)).thenReturn(deliveryNoteList);
        // Verify & Assert
        List<DeliveryNote> retrievedList = deliveryNoteService.filterByPeriod(startDate, endDate);
        assertEquals(deliveryNoteList, retrievedList);
    }
    @Test
    @DisplayName("Test Empty FILTER All DeliveryNote in a period")
    void testEmptyFilterByPeriod() {
        // Prepare Mock Data
        List<DeliveryNote> deliveryNoteList = new ArrayList<>();
        deliveryNoteList.add(deliveryNote1);
        deliveryNoteList.add(deliveryNote2);
        // Mock Request
        Date startDate = DateUtils.parseDate("06-06-2021 00:00:00");
        Date endDate = DateUtils.parseDate("06-09-2021 23:59:59");
        Date normStartDate = DateUtils.normalizeDateAtStart(startDate);
        Date normEndDate = DateUtils.normalizeDateAtEnd(endDate);
        Mockito.when(deliveryNoteRepository.findAllByDateBetween(normStartDate, normEndDate)).thenReturn(new ArrayList<>());
        // Verify & Assert
        List<DeliveryNote> retrievedList = deliveryNoteService.filterByPeriod(startDate, endDate);
        assertEquals(0, retrievedList.size());
    }

}