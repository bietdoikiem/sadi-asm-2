package com.rmit.demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rmit.demo.config.RestExceptionHandler;
import com.rmit.demo.model.*;
import com.rmit.demo.repository.StaffRepository;
import com.rmit.demo.service.StaffService;
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
class StaffControllerTest {

    @MockBean
    protected  StaffService staffService;

    @MockBean
    protected StaffRepository staffRepository;

    @Autowired
    @InjectMocks
    protected StaffController staffController;

    protected MockMvc mockMvc;

    // Defined Mock Objects
    protected ArrayList<Staff> staffArrayList;
    protected Staff staff1;
    protected Staff staff2;
    protected Staff staff3;

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
                .standaloneSetup(staffController)
                .setControllerAdvice(new RestExceptionHandler())
                .build();
        Mockito.reset();

        // Define mock object
        staff1 = new Staff(1, "Duy", "TpHCM", "0326795463", "duyhs1234@gmail.com");
        staff2 = new Staff(2, "Dong", "TpHCM", "313213213", "dong4@gmail.com");
        staff3 = new Staff(3, "Minh", "TpHCM", "214322514", "minhsimp@gmail.com");
    }

    @AfterEach
    void tearDown() {
        staff1 = staff2 = staff3 = null;
        staffArrayList = null;
    }

    @Test
    @DisplayName("Test GET all Staff list")
    void testGetAll() throws Exception {
        ArrayList<Staff> staffArrayList = new ArrayList<>();
        staffArrayList.add(staff1);
        staffArrayList.add(staff2);

        Mockito.when(staffService.getAllStaffs()).thenReturn(staffArrayList);

        String url = "/staffs";
        mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", hasSize(2)))
                .andExpect(jsonPath("$.data[0].id", is(1)))
                .andExpect(jsonPath("$.data[0].name", is("Duy")))
                .andExpect(jsonPath("$.data[0].address", is("TpHCM")))
                .andExpect(jsonPath("$.data[0].phone", is("0326795463")))
                .andExpect(jsonPath("$.data[0].email", is("duyhs1234@gmail.com")))
                .andExpect(jsonPath("$.data[1].id", is(2)))
                .andExpect(jsonPath("$.data[1].name", is("Dong")))
                .andExpect(jsonPath("$.data[1].address", is("TpHCM")))
                .andExpect(jsonPath("$.data[1].phone", is("313213213")))
                .andExpect(jsonPath("$.data[1].email", is("dong4@gmail.com")))
                .andReturn();
    }

    @Test
    @DisplayName("Test GET empty Staff list")
    void testGetAllEmpty() throws Exception {
        ArrayList<Staff> staffArrayList = new ArrayList<>();

        Mockito.when(staffService.getAllStaffs()).thenReturn(staffArrayList);

        String url = "/staffs";
        mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", hasSize(0)))
                .andReturn();
    }

    @Test
    @DisplayName("Test GET one Staff by Id")
    void testGetOne() throws Exception {
        int id = 1;

        Mockito.when(staffService.getOne(id)).thenReturn(staff1);

        String url = "/staffs/{id}";
        mockMvc.perform(get(url, id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id", is(id)))
                .andExpect(jsonPath("$.data.id", is(1)))
                .andExpect(jsonPath("$.data.name", is("Duy")))
                .andExpect(jsonPath("$.data.address", is("TpHCM")))
                .andExpect(jsonPath("$.data.phone", is("0326795463")))
                .andExpect(jsonPath("$.data.email", is("duyhs1234@gmail.com")));
    }

    @Test
    @DisplayName("Test GET Null Staff by Unknown ID")
    void testGetOneEmpty() throws Exception {
        int invalidId = 99;
        Mockito.when(staffService.getOne(invalidId)).thenReturn(null);
        String url = "/staffs/{id}";
        mockMvc.perform(get(url, invalidId))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Test Success CREATE One Staff")
    void testSaveOne() throws Exception {
        // Prepare Mocked data
        ArrayList<Staff> staffArrayList = new ArrayList<>();
        staffArrayList.add(staff1);
        staffArrayList.add(staff2);

        // Prepare Json Request
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("name", "Duy");
        requestBody.put("address", "TpHCM");
        requestBody.put("phone", "0326795463");
        requestBody.put("email", "duyhs1234@gmail.com");

        String requestJson = asJsonString(requestBody);

        // Mock service to save mocked data
        Mockito.when(staffService.saveStaff(isA(Staff.class))).thenReturn(staff1);
        // MockMvc HTTP Test
        mockMvc.perform(post("/staffs")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.id", is(1)));
    }

    @Test
    @DisplayName("Test Success CREATE One Staff with Null Field")
    void testSaveOneNullField() throws Exception {
        // Prepare Mocked data
        ArrayList<Staff> staffArrayList = new ArrayList<>();
        staffArrayList.add(staff1);
        staffArrayList.add(staff2);

        // Prepare Json Request
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("name", "Duy");
        requestBody.put("address", "TpHCM");
        requestBody.put("phone", null);
        requestBody.put("email", "duyhs1234@gmail.com");

        String requestJson = asJsonString(requestBody);

        // Mock service to save mocked data
        Mockito.when(staffService.saveStaff(isA(Staff.class))).thenReturn(staff1);

        // MockMvc HTTP Test
        mockMvc.perform(post("/staffs")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.id", is(1)));
    }

    @Test
    @DisplayName("Test Success UPDATE for Staff")
    void testUpdateOne() throws Exception {
        int validId = 1;
        // Prepare Json Request
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("name", "Duy");
        requestBody.put("address", "TpHCM");
        requestBody.put("phone", "123456789");
        requestBody.put("email", "duyhs1234@gmail.com");

        String requestJson = asJsonString(requestBody);

        // Mock data when requesting service
        Mockito.when(staffRepository.findById(validId)).thenReturn(Optional.of(staff1));
        Mockito.when(staffService.updateStaff(intThat(id -> id == validId), isA(Staff.class))).thenReturn(staff1);

        // MockMvc HTTP Test
        mockMvc.perform(put("/staffs/{id}",validId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Test Fail UPDATE for Staff")
    void testFailUpdateOne() throws Exception {
        int validId = 1;
        int invalidId = 99;

        // Prepare Json Request
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("name", "Duy");
        requestBody.put("address", "TpHCM");
        requestBody.put("phone", "123456789");
        requestBody.put("email", "duyhs1234@gmail.com");

        String requestJson = asJsonString(requestBody);

        // Mock data when requesting service
        Mockito.when(staffRepository.findById(validId)).thenReturn(Optional.of(staff1));
        Mockito.when(staffService.updateStaff(intThat(id -> id == invalidId), isA(Staff.class))).thenReturn(staff1);

        // MockMvc HTTP Test
        mockMvc.perform(put("/staffs/{id}",validId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Test Success DELETE Staff")
    void testDeleteOne() throws Exception {
        int validId = 1;

        // Mock data when request service and repository
        Mockito.when(staffRepository.findById(validId)).thenReturn(Optional.of(staff1));
        Mockito.when(staffService.deleteStaff(validId)).thenReturn(validId);

        // MockMVC HTTP Test
        mockMvc.perform(delete("/staffs/{id}", validId))
                .andExpect(status().isAccepted());
    }

    @Test
    @DisplayName("Test Fail DELETE Staff")
    void testFailDeleteOne() throws Exception {
        int validId = 1;
        int invalidId = 99;

        // Mock data when request service and repository
        Mockito.when(staffService.deleteStaff(validId)).thenReturn(validId);
        Mockito.when(staffService.deleteStaff(invalidId)).thenThrow(new NullPointerException());

        // MockMVC HTTP Test
        mockMvc.perform(delete("/staffs/{id}", validId))
                .andExpect(status().isAccepted());

        // MockMVC HTTP Fail Test
        mockMvc.perform(delete("/staffs/{id}", invalidId))
                .andExpect(status().isNotFound());

    }

    @Test
    @DisplayName("Test Success GET All Staffs by Pagination")
    void testPaginationGetAll() throws Exception {
        // Mocked list
        ArrayList<Staff> staffArrayList = new ArrayList<>();
        staffArrayList.add(staff1);
        staffArrayList.add(staff2);

        // Mock service to return mocked objects
        Mockito.when(staffService.getAllStaffs(0, 1)).thenReturn(new ArrayList<>(staffArrayList.subList(0, 1)));
        Mockito.when(staffService.getAllStaffs(1, 1)).thenReturn(new ArrayList<>(staffArrayList.subList(1, 2)));

        // Test retrieve first page (index 0) with size 1
        mockMvc.perform(get("/staffs")
                .contentType(MediaType.APPLICATION_JSON)
                .param("page", "0")
                .param("size", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isSuccess", is(true)))
                .andExpect(jsonPath("$.data", hasSize(1)))
                .andExpect(jsonPath("$.data[0].id", is(1)));

        // Test retrieve second page (index 1) with size 1
        mockMvc.perform(get("/staffs")
                .contentType(MediaType.APPLICATION_JSON)
                .param("page", "1")
                .param("size", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isSuccess", is(true)))
                .andExpect(jsonPath("$.data", hasSize(1)))
                .andExpect(jsonPath("$.data[0].id", is(2)));
    }

    @Test
    @DisplayName("Test Fail GET All Staffs by Pagination due to invalid page and size input")
    void testFailPaginationGetAll() throws Exception {
        int invalidPage = -1;
        int invalidSize = -1;

        // Mocked list
        ArrayList<Staff> staffArrayList = new ArrayList<>();
        staffArrayList.add(staff1);
        staffArrayList.add(staff2);

        // Mock service to return mocked objects
        Mockito.when(staffService.getAllStaffs(-1, -1)).thenThrow(new IllegalArgumentException());
        // Test retrieve first page (index 0) with size 1
        mockMvc.perform(get("/staffs").param("page", String.format("%d", invalidPage)).param("size", String.format("%d", invalidSize)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Test Success GET All Staffs by Name")
    void testGetStaffsByName() throws Exception {
        String name = "Duy";
        // Prepare Mocked data
        ArrayList<Staff> staffArrayList = new ArrayList<>();
        staffArrayList.add(staff1);
        staffArrayList.add(staff2);
        staffArrayList.add(staff3);

        // Mock Request
        Mockito.when(staffService.getAllStaffsByName(name)).thenReturn(new ArrayList<>(staffArrayList.subList(0, 1)));
        // MockMVC HTTP Test
        mockMvc.perform(get("/staffs?name={name}", name))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", hasSize(1)))
                .andExpect(jsonPath("$.data[0].id", is(1)));
    }

    @Test
    @DisplayName("Test Fail GET All Staffs by Name")
    void testFailGetStaffsByName() throws Exception {
        String invalidName = "Someone";

        // Prepare Mocked data
        ArrayList<Staff> staffArrayList = new ArrayList<>();
        staffArrayList.add(staff1);
        staffArrayList.add(staff2);

        // Mock Request
        Mockito.when(staffService.getAllStaffsByName(invalidName)).thenReturn(new ArrayList<>());

        // MockMVC HTTP Test
        mockMvc.perform(get("/staffs?name={name}", invalidName))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", hasSize(0)));
    }

    @Test
    @DisplayName("Test Success GET All Staffs by Address")
    void testGetStaffsByAddress() throws Exception {
        String address = "TpHCM";

        // Prepare Mocked data
        ArrayList<Staff> staffArrayList = new ArrayList<>();
        staffArrayList.add(staff1);
        staffArrayList.add(staff2);
        staffArrayList.add(staff2);

        // Mock Request
        Mockito.when(staffService.getAllStaffsByAddress(address)).thenReturn(new ArrayList<>(staffArrayList.subList(0, 1)));

        // MockMVC HTTP Test
        mockMvc.perform(get("/staffs?address={address}", address))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", hasSize(1)))
                .andExpect(jsonPath("$.data[0].id", is(1)));
    }

    @Test
    @DisplayName("Test Fail GET All Staffs by Address")
    void testFailGetStaffsByAddress() throws Exception {
        String invalidAddress = "TienGiang";

        // Prepare Mocked data
        ArrayList<Staff> staffArrayList = new ArrayList<>();
        staffArrayList.add(staff1);
        staffArrayList.add(staff2);

        // Mock Request
        Mockito.when(staffService.getAllStaffsByAddress(invalidAddress)).thenReturn(new ArrayList<>());

        // MockMVC HTTP Test
        mockMvc.perform(get("/staffs?address={address}", invalidAddress))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", hasSize(0)));
    }

    @Test
    @DisplayName("Test Success GET All Staff by Phone")
    void testGetStaffsByPhone() throws Exception {
        String phone = "0326795463";

        // Prepare Mocked data
        ArrayList<Staff> staffArrayList = new ArrayList<>();
        staffArrayList.add(staff1);
        staffArrayList.add(staff2);
        staffArrayList.add(staff3);

        // Mock Request
        Mockito.when(staffService.getAllStaffsByPhone(phone)).thenReturn(new ArrayList<>(staffArrayList.subList(0, 1)));

        // MockMVC HTTP Test
        mockMvc.perform(get("/staffs?phone={phone}", phone))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Test Fail GET All Staffs by Name")
    void testFailGetStaffsByPhone() throws Exception {
        String invalidPhone = "114341123";

        // Prepare Mocked data
        ArrayList<Staff> staffArrayList = new ArrayList<>();
        staffArrayList.add(staff1);
        staffArrayList.add(staff2);

        // Mock Request
        Mockito.when(staffService.getAllStaffsByPhone(invalidPhone)).thenReturn(new ArrayList<>());

        // MockMVC HTTP Test
        mockMvc.perform(get("/staffs?phone={phone}", invalidPhone))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", hasSize(0)));
    }
}