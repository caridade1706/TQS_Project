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
import pt.ua.deti.tqs.cliniconnect.dto.RegisterStaffDTO;
import pt.ua.deti.tqs.cliniconnect.models.Staff;
import pt.ua.deti.tqs.cliniconnect.services.StaffService;
import pt.ua.deti.tqs.cliniconnect.services.impl.AuthServiceImpl;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.sql.Date;

@SpringBootTest
@AutoConfigureMockMvc
class StaffControllerTest {

        @Autowired
        private MockMvc mockMvc;

        @MockBean
        private StaffService staffService;

        @MockBean
        private AuthServiceImpl authService;


        private String url = "/api/staff";

        @BeforeEach
        void setUp() {

        }

        @Test
        @DisplayName("Testa o register de um Staff")
        public void testRegisterStaff() throws Exception {

                RegisterStaffDTO registerStaffDTO = new RegisterStaffDTO();
                registerStaffDTO.setName("Test");
                registerStaffDTO.setDob(Date.valueOf("1990-01-01"));
                registerStaffDTO.setEmail("teste@ua.pt");
                registerStaffDTO.setPassword("password123");
                registerStaffDTO.setPhone("123456789");
                registerStaffDTO.setAddress("Rua do Teste");
                registerStaffDTO.setCity("Aveiro");
                registerStaffDTO.setDepartment("Administration");
                registerStaffDTO.setTask("Receptionist");

                AuthResponse authResponse = new AuthResponse();
                authResponse.setToken("token");

                when(authService.registerStaff(registerStaffDTO)).thenReturn(authResponse);

                // Perform POST request to login endpoint
                mockMvc.perform(post(url + "/register")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(new ObjectMapper().writeValueAsString(registerStaffDTO))) // Convert loginDTO
                                                                                                     // to JSON string
                                .andExpect(status().isOk());

                // Verify the interaction with the authentication service
                verify(authService, times(1)).registerStaff(any(RegisterStaffDTO.class));
        }

        @Test
        @DisplayName("Testa o login de um staff")
        public void testLoginStaff() throws Exception {
                Staff staff = new Staff();
                staff.setEmail("test@example.com");
                staff.setPassword("password123");
                staff.setRole(Roles.STAFF);

                LoginDTO loginDTO = new LoginDTO();
                loginDTO.setEmail(staff.getEmail());
                loginDTO.setPassword(staff.getPassword());

                AuthResponse authResponse = new AuthResponse();
                authResponse.setToken("token");

                when(authService.loginStaff(loginDTO)).thenReturn(authResponse);

                // Perform POST request to login endpoint
                mockMvc.perform(post(url + "/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(new ObjectMapper().writeValueAsString(loginDTO))) // Convert loginDTO to JSON
                                                                                           // string
                                .andExpect(status().isOk());

                // Verify the interaction with the authentication service
                verify(authService, times(1)).loginStaff(any(LoginDTO.class));
        }

        @Test
        @DisplayName("Testa a procura de um staff por email")
        public void testGetStaffByEmail() throws Exception {
                Staff staff = new Staff();
                staff.setEmail("test@example.com");
                staff.setRole(Roles.STAFF);

                when(staffService.getPatientByEmail("test@example.com")).thenReturn(staff);

                mockMvc.perform(get(url + "/test@example.com")
                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.email").value(staff.getEmail()));

                verify(staffService, times(1)).getPatientByEmail(anyString());
        }

        @Test
        @DisplayName("Testa a procura de um staff por email e esse paciente não existir")
        public void testGetSatffByEmailNotExists() throws Exception {
                // Simulação do serviço para retornar null quando o paciente não é encontrado
                when(staffService.getPatientByEmail("test@example.com")).thenReturn(null);

                // Realiza a requisição GET e verifica se o status é 404 Not Found
                mockMvc.perform(get(url + "/test@example.com")
                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(status().isNotFound());

                // Verifica se o método do serviço foi chamado uma vez
                verify(staffService, times(1)).getPatientByEmail(anyString());
        }
}