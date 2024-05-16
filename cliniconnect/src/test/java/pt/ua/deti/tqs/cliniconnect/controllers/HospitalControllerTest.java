package pt.ua.deti.tqs.cliniconnect.controllers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import pt.ua.deti.tqs.cliniconnect.dto.CreateHospitalDTO;
import pt.ua.deti.tqs.cliniconnect.models.Hospital;
import pt.ua.deti.tqs.cliniconnect.services.HospitalService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class HospitalControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private HospitalService hospitalService;

    private String url = "/api/hospitals";

    @Test
    @DisplayName("Test creating a new hospital")
    void testCreateHospital() throws Exception {
        UUID hospitalId = UUID.randomUUID();

        // Setup DTO
        CreateHospitalDTO createHospitalDTO = new CreateHospitalDTO();
        createHospitalDTO.setName("HospitalName");
        createHospitalDTO.setAddress("RUA");
        createHospitalDTO.setCity("Aveiro");

        // Setup Hospital
        Hospital hospital = new Hospital();
        hospital.setId(hospitalId);
        hospital.setName(createHospitalDTO.getName());
        hospital.setAddress(createHospitalDTO.getAddress());
        hospital.setCity(createHospitalDTO.getCity());
        hospital.setStaffs(null);
        hospital.setDoctors(null);
        hospital.setAppointments(null);
        hospital.setQueueManagement(null);

        // Mock service call with any instance
        when(hospitalService.saveHospital(any(CreateHospitalDTO.class))).thenReturn(hospital);

        // Perform request and verify response
        mockMvc.perform(post(url + "/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(createHospitalDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(hospitalId.toString()));

        verify(hospitalService, times(1)).saveHospital(any(CreateHospitalDTO.class));
    }

    @Test
    @DisplayName("Test creating a new appointment with error")
    void testCreateHospitalNull() throws Exception {
        // Setup DTO
        CreateHospitalDTO createHospitalDTO = new CreateHospitalDTO();

        // Mock service call
        when(hospitalService.saveHospital(any(CreateHospitalDTO.class))).thenReturn(null);

        // Perform request and verify response
        mockMvc.perform(post(url + "/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(createHospitalDTO)))
                .andExpect(status().isNotFound());

        verify(hospitalService, times(1)).saveHospital(any(CreateHospitalDTO.class));
    }

    @Test
    @DisplayName("Test get a hospital by hospitalId")
    void testGetHospitalById() throws Exception {

        UUID hospitalId = UUID.randomUUID();

        // Setup Hospital
        Hospital hospital = new Hospital(hospitalId, "HospitalName", "Rua", "Aveiro", null, null, null, null);

        when(hospitalService.getHospitalById(hospitalId)).thenReturn(Optional.of(hospital));

        mockMvc.perform(get(url + "/" + hospitalId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(hospitalId.toString()))
                .andExpect(jsonPath("$.name").value("HospitalName"));
    }

    @Test
    @DisplayName("Test get a hospital by hospitalId when not found")
    void testGetHospitalByIdNotFound() throws Exception {

        UUID hospitalId = UUID.randomUUID();

        when(hospitalService.getHospitalById(hospitalId)).thenReturn(Optional.empty());

        mockMvc.perform(get(url + "/" + hospitalId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Test get all hospitals")
    void testGetAllHospitals() throws Exception {

        UUID hospitalId = UUID.randomUUID();

        // Setup Hospital
        Hospital hospital = new Hospital();
        hospital.setId(hospitalId);
        hospital.setName("HospitalName");
        hospital.setAddress("Rua");
        hospital.setCity("Aveiro");
        hospital.setStaffs(null);
        hospital.setDoctors(null);
        hospital.setAppointments(null);
        hospital.setQueueManagement(null);

        when(hospitalService.getAllHospitals()).thenReturn(List.of(hospital));

        mockMvc.perform(get(url + "/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].id").value(hospitalId.toString()))
                .andExpect(jsonPath("$[0].name").value("HospitalName"));
    }

    @Test
    @DisplayName("Test get all hospitals null")
    void testGetAllHospitalsNull() throws Exception {

        List<Hospital> hospitals = null;

        when(hospitalService.getAllHospitals()).thenReturn(hospitals);

        mockMvc.perform(get(url + "/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Test get all hospitals null")
    void testGetAllHospitalsEmpty() throws Exception {

        List<Hospital> hospitals = new ArrayList<>();

        when(hospitalService.getAllHospitals()).thenReturn(hospitals);

        mockMvc.perform(get(url + "/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Test delete a hospital by hospitalId")
    void testDeleteHospital() throws Exception {
        UUID hospitalId = UUID.randomUUID();

        // Mock the service call
        doNothing().when(hospitalService).deleteHospital(hospitalId);

        // Perform the delete request and verify response
        mockMvc.perform(delete(url + "/" + hospitalId.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        // Verify that the service method was called once
        verify(hospitalService, times(1)).deleteHospital(hospitalId);
    }

}