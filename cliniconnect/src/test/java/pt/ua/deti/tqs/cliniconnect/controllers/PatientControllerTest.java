package pt.ua.deti.tqs.cliniconnect.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import pt.ua.deti.tqs.cliniconnect.Roles;
import pt.ua.deti.tqs.cliniconnect.dto.AuthResponse;
import pt.ua.deti.tqs.cliniconnect.dto.LoginDTO;
import pt.ua.deti.tqs.cliniconnect.dto.RegisterPatientDTO;
import pt.ua.deti.tqs.cliniconnect.models.Patient;
import pt.ua.deti.tqs.cliniconnect.services.PatientService;
import pt.ua.deti.tqs.cliniconnect.services.impl.AuthServiceImpl;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.sql.Date;

@SpringBootTest
@AutoConfigureMockMvc
class PatientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PatientService patientService;

    @MockBean
    private AuthServiceImpl authService;

    private String url = "/api/patients";

    @BeforeEach
    void setUp() {

    }

    @Test
    @DisplayName("Testa a procura de um paciente por email")
    public void testGetPatientByEmail() throws Exception {
        Patient patient = new Patient();
        patient.setEmail("test@example.com");
        patient.setRole(Roles.PATIENT);
        when(patientService.getPatientByEmail("test@example.com")).thenReturn(patient);

        mockMvc.perform(get(url + "/test@example.com")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value(patient.getEmail()));

        verify(patientService, times(1)).getPatientByEmail(anyString());
    }

    @Test
    @DisplayName("Testa a procura de um paciente por email e esse paciente não existir")
    public void testGetPatientByEmailNotExits() throws Exception {
        Patient patient = null;
        
        when(patientService.getPatientByEmail("test@example.com")).thenReturn(patient);

        mockMvc.perform(get(url + "/test@example.com")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(patientService, times(1)).getPatientByEmail(anyString());
    }

    @Test
    @DisplayName("Testa o register de um paciente")
    public void testRegisterPatient() throws Exception {
        
        RegisterPatientDTO registerPatientDTO = new RegisterPatientDTO();
        registerPatientDTO.setName("Test");
        registerPatientDTO.setDob(Date.valueOf("1990-01-01"));
        registerPatientDTO.setEmail("teste@ua.pt");
        registerPatientDTO.setPassword("password123");
        registerPatientDTO.setPhone("123456789");
        registerPatientDTO.setAddress("Rua do Teste");
        registerPatientDTO.setCity("Aveiro");
        registerPatientDTO.setPreferredHospital("Hospital de Aveiro");

        when(authService.registerPatient(registerPatientDTO)).thenReturn(AuthResponse.builder().token("token").build());
       
        // Perform POST request to login endpoint
        mockMvc.perform(post(url + "/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(registerPatientDTO))) // Convert loginDTO to JSON string
                .andExpect(status().isOk());

        // Verify the interaction with the authentication service
        verify(authService, times(1)).registerPatient(registerPatientDTO);
    }

    @Test
    @DisplayName("Testa o login de um paciente")
    public void testLoginPatien() throws Exception {
        Patient patient = new Patient();
        patient.setEmail("test@example.com");
        patient.setPassword("password123");
        patient.setRole(Roles.PATIENT);

        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setEmail(patient.getEmail());
        loginDTO.setPassword(patient.getPassword());

        when(authService.loginPatient(loginDTO)).thenReturn(AuthResponse.builder().token("token").build());

        // Perform POST request to login endpoint
        mockMvc.perform(post(url + "/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(loginDTO))) // Convert loginDTO to JSON string
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists());

        // Verify the interaction with the authentication service
        verify(authService, times(1)).loginPatient(loginDTO);
    }

}