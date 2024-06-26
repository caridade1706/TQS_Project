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
import pt.ua.deti.tqs.cliniconnect.models.Specialties;
import pt.ua.deti.tqs.cliniconnect.repositories.PatientRepository;
import pt.ua.deti.tqs.cliniconnect.services.PatientService;
import pt.ua.deti.tqs.cliniconnect.services.impl.AuthServiceImpl;
import pt.ua.deti.tqs.cliniconnect.services.impl.SpecialtiesServiceImpl;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@SpringBootTest
@AutoConfigureMockMvc
class PatientControllerTest {

        @Autowired
        private MockMvc mockMvc;

        @MockBean
        private PatientService patientService;

        @MockBean
        private PatientRepository patientRepository;

        @MockBean
        private AuthServiceImpl authService;

        @MockBean
        private SpecialtiesServiceImpl specialtiesService;

        private String url = "/api/patients";

        @BeforeEach
        void setUp() {
        }

        @Test
        @DisplayName("Testa a procura de um paciente por email")
        void testGetPatientByEmail() throws Exception {
                Patient patient = new Patient();
                patient.setEmail("test@example.com");
                patient.setRole(Roles.PATIENT);

                HashMap<String, Integer> specialties = new HashMap<>();
                specialties.put("Cardiology", 5);
                specialties.put("Neurology", 3);

                when(patientService.getPatientByEmail("test@example.com")).thenReturn(patient);
                when(specialtiesService.getByPatient(patient)).thenReturn(specialties);

                mockMvc.perform(get(url + "/test@example.com")
                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.patient.email").value(patient.getEmail()))
                                .andExpect(jsonPath("$.specialties.Cardiology").value(5))
                                .andExpect(jsonPath("$.specialties.Neurology").value(3));

                verify(patientService, times(1)).getPatientByEmail(anyString());
                verify(specialtiesService, times(1)).getByPatient(any(Patient.class));
        }

        @Test
        @DisplayName("Testa a procura de um paciente por email e esse paciente não existir")
        void testGetPatientByEmailNotExists() throws Exception {
                // Simulação do serviço para retornar null quando o paciente não é encontrado
                when(patientService.getPatientByEmail("test@example.com")).thenReturn(null);

                // Realiza a requisição GET e verifica se o status é 404 Not Found
                mockMvc.perform(get(url + "/test@example.com")
                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(status().isNotFound());

                // Verifica se o método do serviço foi chamado uma vez
                verify(patientService, times(1)).getPatientByEmail(anyString());
        }

        @Test
        @DisplayName("Test Getting all Patients")
        void testGetAllPatients() throws Exception {

                UUID patientId1 = UUID.randomUUID();
                UUID patientId2 = UUID.randomUUID();


                Patient patient1 = new Patient();
                patient1.setId(patientId1);
                patient1.setName("Patient 1");
                patient1.setDob(Date.valueOf("1990-01-01"));
                patient1.setEmail("patient1@ua.pt");
                patient1.setPassword(null);
                patient1.setPhone("123456789");
                patient1.setAddress("Rua do Teste");
                patient1.setCity("Aveiro");
                patient1.setRole(Roles.PATIENT);
                patient1.setPatientNumber("123456789");
                patient1.setPreferredHospital("Hospital de Aveiro");

                Patient patient2 = new Patient();
                patient2.setId(patientId2);
                patient2.setName("Patient 2");
                patient2.setDob(Date.valueOf("1999-01-01"));
                patient2.setEmail("patient2@ua.pt");
                patient2.setPassword(null);
                patient2.setPhone("123456789");
                patient2.setAddress("Rua do Teste");
                patient2.setCity("Aveiro");
                patient2.setRole(Roles.PATIENT);
                patient2.setPatientNumber("123456789");
                patient2.setPreferredHospital("Hospital de Aveiro");

                when(patientRepository.findAll()).thenReturn(List.of(patient1, patient2));

                mockMvc.perform(get(url + "/"))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$[0].id").value(patientId1.toString()))
                                .andExpect(jsonPath("$[0].name").value("Patient 1"))
                                .andExpect(jsonPath("$[0].dob").value("1990-01-01"));

        }

        @Test
        @DisplayName("Test Getting all Patients with no patients")
        void testGetAllPatientsWithNoPatient() throws Exception {

                List<Patient> patients = new ArrayList<>();

                when(patientRepository.findAll()).thenReturn(patients);

                mockMvc.perform(get(url + "/"))
                                .andExpect(status().isNotFound());

        }

        @Test
        @DisplayName("Testa o register de um paciente")
        void testRegisterPatient() throws Exception {

                RegisterPatientDTO registerPatientDTO = new RegisterPatientDTO();
                registerPatientDTO.setName("Test");
                registerPatientDTO.setDob(Date.valueOf("1990-01-01"));
                registerPatientDTO.setEmail("teste@ua.pt");
                registerPatientDTO.setPassword("password123");
                registerPatientDTO.setPhone("123456789");
                registerPatientDTO.setAddress("Rua do Teste");
                registerPatientDTO.setCity("Aveiro");
                registerPatientDTO.setPreferredHospital("Hospital de Aveiro");

                AuthResponse authResponse = new AuthResponse();
                authResponse.setToken("token");

                when(authService.registerPatient(registerPatientDTO)).thenReturn(authResponse);

                // Perform POST request to login endpoint
                mockMvc.perform(post(url + "/register")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(new ObjectMapper().writeValueAsString(registerPatientDTO)))                                                        // to JSON string
                                .andExpect(status().isOk());

                // Verify the interaction with the authentication service
                // verify(authService, times(1)).registerPatient(registerPatientDTO);
        }

        @Test
        @DisplayName("Testa o login de um paciente")
        void testLoginPatien() throws Exception {
                Patient patient = new Patient();
                patient.setEmail("test@example.com");
                patient.setPassword("password123");
                patient.setRole(Roles.PATIENT);

                LoginDTO loginDTO = new LoginDTO();
                loginDTO.setEmail(patient.getEmail());
                loginDTO.setPassword(patient.getPassword());

                AuthResponse authResponse = new AuthResponse();
                authResponse.setToken("token");

                when(authService.loginPatient(loginDTO)).thenReturn(authResponse);

                // Perform POST request to login endpoint
                mockMvc.perform(post(url + "/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(new ObjectMapper().writeValueAsString(loginDTO))) // Convert loginDTO to JSON
                                                                                           // string
                                .andExpect(status().isOk());

                // Verify the interaction with the authentication service
                // verify(authService, times(1)).loginPatient(loginDTO);
        }

        @Test
        @DisplayName("Testa a procura de um paciente por email sem especialidades")
        void testGetPatientByEmailWithoutSpecialties() throws Exception {
                Patient patient = new Patient();
                patient.setEmail("test@example.com");
                patient.setRole(Roles.PATIENT);

                // Retorna um paciente válido, mas sem especialidades
                when(patientService.getPatientByEmail("test@example.com")).thenReturn(patient);
                when(specialtiesService.getByPatient(patient)).thenReturn(new HashMap<>()); // Retorna um mapa vazio

                mockMvc.perform(get(url + "/test@example.com")
                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.patient.email").value(patient.getEmail()))
                                .andExpect(jsonPath("$.specialties").isEmpty());

                verify(patientService, times(1)).getPatientByEmail(anyString());
                verify(specialtiesService, times(1)).getByPatient(any(Patient.class));
        }

        @SuppressWarnings("unchecked")
        @Test
        @DisplayName("Testa a adição de especialidades a um paciente")
        void testAddSpecialty() throws Exception {
                String email = "test@example.com";
                Patient patient = new Patient();
                patient.setEmail(email);
                patient.setRole(Roles.PATIENT);

                HashMap<String, Integer> specialties = new HashMap<>();
                specialties.put("Cardiology", 5);

                when(patientService.getPatientByEmail(email)).thenReturn(patient);
                when(specialtiesService.addSpecialty(eq(patient), any(HashMap.class)))
                                .thenReturn(new Specialties(patient, specialties));

                mockMvc.perform(post(url + "/" + email + "/specialties")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(new ObjectMapper().writeValueAsString(specialties))) // Converte specialties
                                                                                              // para JSON
                                .andExpect(status().isOk());

                verify(patientService, times(1)).getPatientByEmail(email);
                verify(specialtiesService, times(1)).addSpecialty(eq(patient), any(HashMap.class));
        }

        @SuppressWarnings("unchecked")
        @Test
        @DisplayName("Testa a adição de especialidades a um paciente que já tem especialidades")
        void testAddSpecialtyWhenPatientHasExistingSpecialties() throws Exception {
                String email = "test@example.com";
                Patient patient = new Patient();
                patient.setEmail(email);
                patient.setRole(Roles.PATIENT);

                HashMap<String, Integer> existingSpecialties = new HashMap<>();
                existingSpecialties.put("Neurology", 3);

                HashMap<String, Integer> newSpecialties = new HashMap<>();
                newSpecialties.put("Cardiology", 5);

                HashMap<String, Integer> combinedSpecialties = new HashMap<>(existingSpecialties);
                combinedSpecialties.putAll(newSpecialties);

                when(patientService.getPatientByEmail(email)).thenReturn(patient);
                when(specialtiesService.getByPatient(patient)).thenReturn(existingSpecialties);
                when(specialtiesService.addSpecialty(eq(patient), any(HashMap.class)))
                                .thenReturn(new Specialties(patient, combinedSpecialties));

                mockMvc.perform(post(url + "/" + email + "/specialties")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(new ObjectMapper().writeValueAsString(newSpecialties)))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.specialty.Cardiology").value(5))
                                .andExpect(jsonPath("$.specialty.Neurology").value(3));

                verify(patientService, times(1)).getPatientByEmail(email);
                verify(specialtiesService, times(1)).addSpecialty(eq(patient), any(HashMap.class));
        }

        @Test
        @DisplayName("Testa a remoção de uma especialidade de um paciente existente")
        void testDeleteSpecialty() throws Exception {
                String email = "test@example.com";
                String specialty = "Cardiology";
                Patient patient = new Patient();
                patient.setEmail(email);
                patient.setRole(Roles.PATIENT);

                HashMap<String, Integer> specialties = new HashMap<>();
                specialties.put(specialty, 5);

                when(patientService.getPatientByEmail(email)).thenReturn(patient);
                when(specialtiesService.getByPatient(patient)).thenReturn(specialties);
                when(specialtiesService.deleteSpecialty(patient, specialty))
                                .thenReturn(new Specialties(patient, new HashMap<>()));

                mockMvc.perform(delete(url + "/" + email + "/specialties/" + specialty)
                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(status().isOk());

                verify(patientService, times(1)).getPatientByEmail(email);
                verify(specialtiesService, times(1)).deleteSpecialty(patient, specialty);
        }

        @Test
        @DisplayName("Testa a remoção de uma especialidade que não existe de um paciente")
        void testDeleteNonExistingSpecialty() throws Exception {
                String email = "test@example.com";
                String specialty = "Cardiology";
                Patient patient = new Patient();
                patient.setEmail(email);
                patient.setRole(Roles.PATIENT);

                HashMap<String, Integer> specialties = new HashMap<>();

                when(patientService.getPatientByEmail(email)).thenReturn(patient);
                when(specialtiesService.getByPatient(patient)).thenReturn(specialties);

                mockMvc.perform(delete(url + "/" + email + "/specialties/" + specialty)
                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(status().isNotFound());

                verify(patientService, times(1)).getPatientByEmail(email);
                verify(specialtiesService, times(1)).getByPatient(patient);
                verify(specialtiesService, times(0)).deleteSpecialty(patient, specialty);
        }

        @Test
        @DisplayName("Testa a remoção de uma especialidade de um paciente sem especialidades (retorno null)")
        void testDeleteSpecialtyNoSpecialties() throws Exception {
                String email = "test@example.com";
                String specialty = "Cardiology";
                Patient patient = new Patient();
                patient.setEmail(email);
                patient.setRole(Roles.PATIENT);

                when(patientService.getPatientByEmail(email)).thenReturn(patient);
                when(specialtiesService.getByPatient(patient)).thenReturn(null);

                mockMvc.perform(delete(url + "/" + email + "/specialties/" + specialty)
                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(status().isNotFound());

                verify(patientService, times(1)).getPatientByEmail(email);
                verify(specialtiesService, times(1)).getByPatient(patient);
                verify(specialtiesService, times(0)).deleteSpecialty(patient, specialty);
        }

        @Test
        @DisplayName("Testa a remoção de uma especialidade de um paciente não existente")
        void testDeleteSpecialtyPatientNotFound() throws Exception {
                String email = "test@example.com";
                String specialty = "Cardiology";

                when(patientService.getPatientByEmail(email)).thenReturn(null);

                mockMvc.perform(delete(url + "/" + email + "/specialties/" + specialty)
                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(status().isNotFound());

                verify(patientService, times(1)).getPatientByEmail(email);
                verify(specialtiesService, times(0)).deleteSpecialty(any(Patient.class), eq(specialty));
        }

        @Test
        @DisplayName("Testa a procura das especialidades de um paciente")
        void testGetSpecialtiesWithSpecialties() throws Exception {
                String email = "test@example.com";
                Patient patient = new Patient();
                patient.setEmail(email);
                patient.setRole(Roles.PATIENT);

                HashMap<String, Integer> specialties = new HashMap<>();
                specialties.put("Cardiology", 5);
                specialties.put("Neurology", 3);

                when(patientService.getPatientByEmail(email)).thenReturn(patient);
                when(specialtiesService.getByPatient(patient)).thenReturn(specialties);

                mockMvc.perform(get(url + "/" + email + "/specialties")
                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.Cardiology").value(5))
                                .andExpect(jsonPath("$.Neurology").value(3));

                verify(patientService, times(1)).getPatientByEmail(email);
                verify(specialtiesService, times(1)).getByPatient(patient);
        }

        @Test
        @DisplayName("Testa a procura das especialidades de um paciente sem especialidades")
        void testGetSpecialtiesWithoutSpecialties() throws Exception {
                String email = "test@example.com";
                Patient patient = new Patient();
                patient.setEmail(email);
                patient.setRole(Roles.PATIENT);

                HashMap<String, Integer> specialties = new HashMap<>();

                when(patientService.getPatientByEmail(email)).thenReturn(patient);
                when(specialtiesService.getByPatient(patient)).thenReturn(specialties);

                mockMvc.perform(get(url + "/" + email + "/specialties")
                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$").isEmpty());

                verify(patientService, times(1)).getPatientByEmail(email);
                verify(specialtiesService, times(1)).getByPatient(patient);
        }

        @Test
        @DisplayName("Testa a procura das especialidades de um paciente não encontrado")
        void testGetSpecialtiesPatientNotFound() throws Exception {
                String email = "test@example.com";

                when(patientService.getPatientByEmail(email)).thenReturn(null);

                mockMvc.perform(get(url + "/" + email + "/specialties")
                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(status().isNotFound());

                verify(patientService, times(1)).getPatientByEmail(email);
                verify(specialtiesService, times(0)).getByPatient(any(Patient.class));
        }

        @SuppressWarnings("unchecked")
        @Test
        @DisplayName("Testa a adição de especialidades a um paciente não existente")
        void testAddSpecialtyToNonExistentPatient() throws Exception {
                String email = "test@example.com";
                HashMap<String, Integer> specialties = new HashMap<>();
                specialties.put("Cardiology", 5);

                // Simulação do serviço para retornar null quando o paciente não é encontrado
                when(patientService.getPatientByEmail(email)).thenReturn(null);

                // Realiza a requisição POST e verifica se o status é 404 Not Found
                mockMvc.perform(post(url + "/" + email + "/specialties")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(new ObjectMapper().writeValueAsString(specialties)))
                                .andExpect(status().isNotFound());

                // Verifica se o método do serviço foi chamado uma vez
                verify(patientService, times(1)).getPatientByEmail(email);
                verify(specialtiesService, times(0)).addSpecialty(any(Patient.class), any(HashMap.class));
        }

}