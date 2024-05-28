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

import io.cucumber.java.sl.In;
import pt.ua.deti.tqs.cliniconnect.Roles;
import pt.ua.deti.tqs.cliniconnect.dto.AddDoctorDTO;
import pt.ua.deti.tqs.cliniconnect.models.Doctor;
import pt.ua.deti.tqs.cliniconnect.services.DoctorService;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

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
