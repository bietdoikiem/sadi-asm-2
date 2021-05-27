package com.rmit.demo.service;

import com.rmit.demo.model.Staff;
import com.rmit.demo.repository.StaffRepository;
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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import java.util.ArrayList;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StaffServiceTest {

    @Mock
    private StaffRepository staffRepository;

    @Autowired
    @InjectMocks
    private StaffService staffService;
    private ArrayList<Staff> staffArrayList;
    private Staff staff1;
    private Staff staff2;
    private Staff staff3;

    @BeforeEach
    void setUp() {
        staffArrayList = new ArrayList<>();
        staff1 = new Staff(1, "Duy", "TpHCM", "0326795463", "duyhs1234@gmail.com");
        staff2 = new Staff(2, "Dong", "TpHCM", "313213213", "dong4@gmail.com");
        staff3 = new Staff(3, "Minh", "TpHCM", "214322514", "minhsimp@gmail.com");
        staffArrayList.add(staff1);
        staffArrayList.add(staff2);
        staffArrayList.add(staff3);
    }

    @AfterEach
    void tearDown() {
        staff1 = staff2 = staff3 = null;
        staffArrayList = null;
    }

    @Test
    @DisplayName("Test GET ALL Staffs")
    void testGetAllStaffs() {
        Mockito.when(staffRepository.findAll()).thenReturn(staffArrayList);
        ArrayList<Staff> staffArrayList1 = staffService.getAllStaffs();
        assertEquals(staffArrayList1, staffArrayList);
        Mockito.verify(staffRepository, Mockito.times(1)).findAll();
    }

    @Test
    @DisplayName("Test GET List of Empty Staff")
    void testGetEmptyStaffs() {
        Mockito.when(staffRepository.findAll()).thenReturn(new ArrayList<>());
        ArrayList<Staff> staffArrayList1 = staffService.getAllStaffs();
        assertEquals(staffArrayList1, new ArrayList<>());
        Mockito.verify(staffRepository, Mockito.times(1)).findAll();
    }

    @Test
    @DisplayName("Test GET One Staff by Id")
    void testGetOne() {
        int id = 1;
        Mockito.when(staffRepository.findById(id)).thenReturn(Optional.of(staff1));
        Staff foundStaff = staffService.getOne(id);
        assertEquals(foundStaff, staff1);
    }

    @Test
    @DisplayName("Test Failed GET One Staff by Invalid Id")
    void testFailGetOne() {
        int invalidId = 99;
        Mockito.when(staffRepository.findById(invalidId)).thenReturn(null);
        assertThrows(NullPointerException.class, () -> staffService.getOne(invalidId));
    }

    @Test
    @DisplayName("Test Success CREATE Staff")
    void testSaveOne() {
        Mockito.when(staffRepository.saveAndReset(staff1)).thenReturn(staff1);
        Staff savedStaff = staffService.saveStaff(staff1);
        assertEquals(savedStaff, staff1);
    }

//    @Test
//    @DisplayName("Test Fail CREATE Staff")
//    void testFailSaveOne() {
//        Mockito.when(customerRepository.save(customer1)).thenReturn(customer1);
//        Customer savedCustomer = customerService.saveCustomer(customer1);
//        assertEquals(savedCustomer, customer1);
//    }

    @Test
    @DisplayName("Test Success UPDATE method for Staff")
    void testUpdateOne() {
        // Mock customer object
        Staff updatedOne = new Staff(1, "DuyDuy", "TpHCM", "0326795463", "Duyddduy@gmail.com");
        Mockito.when(staffRepository.findById(updatedOne.getId())).thenReturn(Optional.of(staff1));
        Mockito.when(staffRepository.saveAndReset(staff1)).thenReturn(updatedOne);
        Staff result = staffService.updateStaff(1, updatedOne);
        assertEquals(result.getName(), updatedOne.getName());
    }

    @Test
    @DisplayName("Test Failed UPDATE method for Customer")
    void testFailUpdateOne() {
        // Mocked
        int validId = 1;
        int invalidId = 99;
        Staff updatedOne = new Staff(invalidId, "DuyDuy", "TpHCM", "0326795463", "Duyddduy@gmail.com");
        Mockito.lenient().when(staffRepository.findById(ArgumentMatchers.eq(validId))).thenReturn(Optional.of(staff1));
        Mockito.lenient().when(staffRepository.save(staff1)).thenReturn(updatedOne);
        assertThrows(NullPointerException.class, () -> staffService.updateStaff(invalidId, updatedOne));
    }

    @Test
    @DisplayName("Test Success DELETE method for Customer")
    void testDeleteOne() {
        int validId = 1;
        doReturn(Optional.of(staff1)).when(staffRepository).findById(validId);
        doAnswer(i -> { return null; }).when(staffRepository).delete(staff1);
        int result = staffService.deleteStaff(validId);
        assertEquals(validId, result);
    }

    @Test
    @DisplayName("Test Fail DELETE method for Customer")
    void testFailDeleteOne() {
        int validId = 1;
        int invalidId = 99;
        doReturn(Optional.of(staff1)).when(staffRepository).findById(validId);
        doAnswer(i -> {return null;}).when(staffRepository).delete(staff1);
        int result = staffService.deleteStaff(validId);
        assertEquals(validId, result);
        assertThrows(NullPointerException.class, () -> staffService.deleteStaff(invalidId));
    }

    @Test
    @DisplayName("Test GET ALL Staff by Pagination")
    void testPaginationGetAll() {
        // Prepare Mock Data
        ArrayList<Staff> staffArrayList = new ArrayList<>();
        staffArrayList.add(staff1);
        staffArrayList.add(staff2);

        // Mock Request
        Pageable pageable = PageRequest.of(0, 1);
        Page<Staff> staffPage = new PageImpl<>(staffArrayList.subList(0, 1));
        Mockito.when(staffRepository.findAll(pageable)).thenReturn(staffPage);

        // Verify & Assertion
        ArrayList<Staff> mockedList = staffService.getAllStaffs(0, 1);
        assertEquals(mockedList.size(), staffPage.getContent().size());
        Mockito.verify(staffRepository, times(1)).findAll(pageable);
    }

    @Test
    @DisplayName("Test Empty GET ALL Staff by Pagination")
    void testEmptyPaginationGetAll() {
        // Prepare Mock Data
        ArrayList<Staff> staffArrayList = new ArrayList<>();
        staffArrayList.add(staff1);
        staffArrayList.add(staff2);

        // Mock Request
        Pageable pageable = PageRequest.of(0, 1);
        Page<Staff> staffPage = new PageImpl<>(new ArrayList<>());
        Mockito.when(staffRepository.findAll(pageable)).thenReturn(staffPage);

        // Verify & Assertion
        ArrayList<Staff> mockedList = staffService.getAllStaffs(0, 1);
        assertEquals(mockedList.size(), staffPage.getContent().size());
        Mockito.verify(staffRepository, times(1)).findAll(pageable);
    }

    @Test
    @DisplayName("Test Success GET ALL Staffs by Name")
    void testGetAllStaffsByName() {
        String name = "Duy";
        ArrayList<Staff> staffArrayList = new ArrayList<>();
        staffArrayList.add(staff1);
        staffArrayList.add(staff2);
        staffArrayList.add(staff3);

        Mockito.when(staffRepository.findAllByName(name)).thenReturn(staffArrayList);
        ArrayList<Staff> customerArrayList1 = staffService.getAllStaffsByName(name);
        assertEquals(customerArrayList1, staffArrayList);
        Mockito.verify(staffRepository, Mockito.times(1)).findAllByName(name);
    }

    @Test
    @DisplayName("Test Fail GET ALL Staff by Name")
    void testGetFailAllStaffsByName() {
        String invalidName = "Someone";
        ArrayList<Staff> staffArrayList = new ArrayList<>();
        staffArrayList.add(staff1);
        staffArrayList.add(staff2);
        staffArrayList.add(staff3);

        Mockito.when(staffRepository.findAllByName(invalidName)).thenReturn(new ArrayList<>());
        ArrayList<Staff> staffArrayList1 = staffService.getAllStaffsByName(invalidName);
        assertEquals(staffArrayList1, new ArrayList<>());
        Mockito.verify(staffRepository, Mockito.times(1)).findAllByName(invalidName);
    }

    @Test
    @DisplayName("Test Success GET ALL Customers by Address")
    void testGetAllStaffsByAddress() {
        String address = "TpHCM";
        ArrayList<Staff> staffArrayList = new ArrayList<>();
        staffArrayList.add(staff1);
        staffArrayList.add(staff2);
        staffArrayList.add(staff3);

        Mockito.when(staffRepository.findAllByAddress(address)).thenReturn(staffArrayList);
        ArrayList<Staff> customerArrayList1 = staffService.getAllStaffsByAddress(address);
        assertEquals(customerArrayList1, staffArrayList);
        Mockito.verify(staffRepository, Mockito.times(1)).findAllByAddress(address);
    }

    @Test
    @DisplayName("Test Fail GET ALL Customers by Address")
    void testGetFailAllStaffsByAddress() {
        String invalidAddress = "TienGiang";
        ArrayList<Staff> staffArrayList = new ArrayList<>();
        staffArrayList.add(staff1);
        staffArrayList.add(staff2);
        staffArrayList.add(staff3);

        Mockito.when(staffRepository.findAllByAddress(invalidAddress)).thenReturn(new ArrayList<>());
        ArrayList<Staff> staffArrayList1 = staffService.getAllStaffsByAddress(invalidAddress);
        assertEquals(staffArrayList1, new ArrayList<>());
        Mockito.verify(staffRepository, Mockito.times(1)).findAllByAddress(invalidAddress);
    }

    @Test
    @DisplayName("Test Success GET ALL Customers by Phone")
    void testGetAllStaffsByPhone() {
        String phone = "0326795463";
        ArrayList<Staff> staffArrayList = new ArrayList<>();
        staffArrayList.add(staff1);
        staffArrayList.add(staff2);
        staffArrayList.add(staff3);

        Mockito.when(staffRepository.findAllByPhone(phone)).thenReturn(staffArrayList);
        ArrayList<Staff> customerArrayList1 = staffService.getAllStaffsByPhone(phone);
        assertEquals(customerArrayList1, staffArrayList);
        Mockito.verify(staffRepository, Mockito.times(1)).findAllByPhone(phone);
    }

    @Test
    @DisplayName("Test Fail GET ALL Customers by Phone")
    void testGetFailAllStaffsByPhone() {
        String invalidPhone = "112233445566";
        ArrayList<Staff> staffArrayList = new ArrayList<>();
        staffArrayList.add(staff1);
        staffArrayList.add(staff2);
        staffArrayList.add(staff3);

        Mockito.when(staffRepository.findAllByPhone(invalidPhone)).thenReturn(new ArrayList<>());
        ArrayList<Staff> staffArrayList1 = staffService.getAllStaffsByPhone(invalidPhone);
        assertEquals(staffArrayList1, new ArrayList<>());
        Mockito.verify(staffRepository, Mockito.times(1)).findAllByPhone(invalidPhone);
    }
}