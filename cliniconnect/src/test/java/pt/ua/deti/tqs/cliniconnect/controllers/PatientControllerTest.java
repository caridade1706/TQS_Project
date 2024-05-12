package pt.ua.deti.tqs.cliniconnect.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.support.BeanDefinitionDsl.Role;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import pt.ua.deti.tqs.cliniconnect.Roles;
import pt.ua.deti.tqs.cliniconnect.models.Patient;
import pt.ua.deti.tqs.cliniconnect.services.PatientService;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class PatientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PatientService patientService;

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
    }

}