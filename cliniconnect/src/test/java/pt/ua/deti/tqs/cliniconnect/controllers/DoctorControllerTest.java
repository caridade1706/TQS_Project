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
import pt.ua.deti.tqs.cliniconnect.dto.AddDoctorDTO;
import pt.ua.deti.tqs.cliniconnect.models.Doctor;
import pt.ua.deti.tqs.cliniconnect.services.DoctorService;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class DoctorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DoctorService doctorService;

    private String url = "/api/doctors";

    @BeforeEach
    void setUp() {

    }

    @Test
    @DisplayName("Testa a adição de um médico")
    void testGetAddDoctor() throws Exception {

        Date date = Date.valueOf("1999-01-01");

        AddDoctorDTO addDoctorDTO = new AddDoctorDTO();
        addDoctorDTO.setName("DoctorName");
        addDoctorDTO.setDob(date);
        addDoctorDTO.setEmail("DoctorName@ua.pt");
        addDoctorDTO.setPhone("123456789");
        addDoctorDTO.setAddress("Rua");
        addDoctorDTO.setCity("Aveiro");
        addDoctorDTO.setSpeciality("Cardiology");

        Doctor doctor = new Doctor();
        doctor.setName("DoctorName");
        doctor.setDob(date);
        doctor.setEmail("DoctorName@ua.pt");
        doctor.setPhone("123456789");
        doctor.setAddress("Rua");
        doctor.setCity("Aveiro");
        doctor.setSpeciality("Cardiology");
        doctor.setRole(Roles.DOCTOR);
        doctor.setHospitals(null);
        doctor.setAppointments(null);

        when(doctorService.addDoctor(any(AddDoctorDTO.class))).thenReturn(doctor);

        mockMvc.perform(post(url + "/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(addDoctorDTO)))
                .andExpect(status().isOk());

        verify(doctorService, times(1)).addDoctor(any(AddDoctorDTO.class));
    }

    @Test
    @DisplayName("Testa a adição de um médico e da null")
    void testGetAddDoctorNull() throws Exception {

        Date date = Date.valueOf("1999-01-01");

        AddDoctorDTO addDoctorDTO = new AddDoctorDTO();
        addDoctorDTO.setName("DoctorName");
        addDoctorDTO.setDob(date);
        addDoctorDTO.setEmail("DoctorName@ua.pt");
        addDoctorDTO.setPhone("123456789");
        addDoctorDTO.setAddress("Rua");
        addDoctorDTO.setCity("Aveiro");
        addDoctorDTO.setSpeciality("Cardiology");

        when(doctorService.addDoctor(any(AddDoctorDTO.class))).thenReturn(null);

        mockMvc.perform(post(url + "/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(addDoctorDTO)))
                .andExpect(status().isNotFound());

        verify(doctorService, times(1)).addDoctor(any(AddDoctorDTO.class));
    }

    @Test
    @DisplayName("Test Getting all Patients")
    void testGetAllPatients() throws Exception {

        UUID doctorId1 = UUID.randomUUID();
        UUID doctorId2 = UUID.randomUUID();

        Doctor doctor1 = new Doctor();
        doctor1.setId(doctorId1);
        doctor1.setName("Doctor 1");
        doctor1.setDob(Date.valueOf("1990-01-01"));
        doctor1.setEmail("doctor1@ua.pt");
        doctor1.setPassword(null);
        doctor1.setPhone("123456789");
        doctor1.setAddress("Rua do Teste");
        doctor1.setCity("Aveiro");
        doctor1.setRole(Roles.DOCTOR);
        doctor1.setSpeciality("Cardiology");
        doctor1.setHospitals(null);
        doctor1.setAppointments(null);

        Doctor doctor2 = new Doctor();
        doctor2.setId(doctorId2);
        doctor2.setName("Doctor 2");
        doctor2.setDob(Date.valueOf("1999-01-01"));
        doctor2.setEmail("doctor2@ua.pt");
        doctor2.setPassword(null);
        doctor2.setPhone("123456789");
        doctor2.setAddress("Rua do Teste");
        doctor2.setCity("Aveiro");
        doctor2.setRole(Roles.DOCTOR);
        doctor2.setSpeciality("Cardiology");
        doctor2.setHospitals(null);
        doctor2.setAppointments(null);

        when(doctorService.getAllDoctors()).thenReturn(List.of(doctor1, doctor2));

        mockMvc.perform(get(url + "/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(doctorId1.toString()))
                .andExpect(jsonPath("$[0].name").value("Doctor 1"))
                .andExpect(jsonPath("$[0].dob").value("1990-01-01"));

    }

    @Test
    @DisplayName("Test Getting all Patients with no patients")
    void testGetAllPatientsWithNoPatient() throws Exception {

        List<Doctor> doctors = new ArrayList<>();

        when(doctorService.getAllDoctors()).thenReturn(doctors);

        mockMvc.perform(get(url + "/"))
                .andExpect(status().isNotFound());

    }

    @Test
    @DisplayName("Test Getting all Patients with is null")
    void testGetAllPatientsNull() throws Exception {

        when(doctorService.getAllDoctors()).thenReturn(null);

        mockMvc.perform(get(url + "/"))
                .andExpect(status().isNotFound());

    }

    @Test
    @DisplayName("Testa a procura de médicos por especialidade")
    void testGetDoctorsBySpeciality() throws Exception {

        Doctor doctor1 = new Doctor();
        doctor1.setRole(Roles.DOCTOR);
        Doctor doctor2 = new Doctor();
        doctor2.setRole(Roles.DOCTOR);

        List<Doctor> doctors = List.of(doctor1, doctor2);

        when(doctorService.getDoctorsBySpeciality("Cardiology")).thenReturn(doctors);

        mockMvc.perform(get(url + "/speciality/Cardiology")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));

        verify(doctorService, times(1)).getDoctorsBySpeciality("Cardiology");
    }

    @Test
    @DisplayName("Testa a procura de médicos por especialidade nao encontrada")
    void testGetDoctorsBySpecialityNotFound() throws Exception {

        List<Doctor> doctors = new ArrayList<>();

        when(doctorService.getDoctorsBySpeciality("Cardiology")).thenReturn(doctors);

        mockMvc.perform(get(url + "/speciality/Cardiology")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(doctorService, times(1)).getDoctorsBySpeciality("Cardiology");
    }
}
