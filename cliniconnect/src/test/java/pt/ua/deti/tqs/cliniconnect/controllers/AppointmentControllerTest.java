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

import pt.ua.deti.tqs.cliniconnect.Roles;
import pt.ua.deti.tqs.cliniconnect.dto.CreateAppointmentDTO;
import pt.ua.deti.tqs.cliniconnect.models.Appointment;
import pt.ua.deti.tqs.cliniconnect.models.Doctor;
import pt.ua.deti.tqs.cliniconnect.models.Hospital;
import pt.ua.deti.tqs.cliniconnect.models.Patient;
import pt.ua.deti.tqs.cliniconnect.services.AppointmentService;

import java.time.Instant;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class AppointmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AppointmentService appointmentService;

    private String url = "/api/appointments";

    @Test
    @DisplayName("Test creating a new appointment")
    void testCreateAppointment() throws Exception {
        UUID hospitalId = UUID.randomUUID();
        UUID doctorId = UUID.randomUUID();
        UUID patientId = UUID.randomUUID();
        UUID appointmentId = UUID.randomUUID();

        // Setup entities
        Hospital hospital = new Hospital();
        hospital.setId(hospitalId);
        hospital.setName("hospitalName");
        hospital.setAddress("address");
        hospital.setCity("city");

        Doctor doctor = new Doctor();
        doctor.setId(doctorId);
        doctor.setName("doctorName");
        doctor.setSpeciality("specialty");
        doctor.setRole(Roles.DOCTOR);
        Set<Hospital> hospitals = new HashSet<>();
        hospitals.add(hospital);
        doctor.setHospitals(hospitals);

        Patient patient = new Patient();
        patient.setId(patientId);
        patient.setName("patientName");
        patient.setAddress("address");
        patient.setCity("city");
        patient.setRole(Roles.PATIENT);

        // Setup DTO
        CreateAppointmentDTO createAppointmentDTO = new CreateAppointmentDTO();
        createAppointmentDTO.setDate(Date.from(Instant.parse("2020-02-10T00:00:00Z")));
        createAppointmentDTO.setTime("10:30"); // Ensure valid time format
        createAppointmentDTO.setPrice(10.0);
        createAppointmentDTO.setType("type");
        createAppointmentDTO.setPatientName(patient.getName());
        createAppointmentDTO.setDoctorName(doctor.getName());
        createAppointmentDTO.setHospitalName(hospital.getName());

        // Setup Appointment
        Appointment appointment = new Appointment();
        appointment.setId(appointmentId);
        appointment.setType("type");
        appointment.setPatient(patient);
        appointment.setDoctor(doctor);
        appointment.setHospital(hospital);
        appointment.setStatus("Created");
        appointment.setDate(Date.from(Instant.parse("2020-02-10T00:00:00Z")));
        appointment.setPrice(10.0);

        // Mock service call with any instance
        when(appointmentService.bookAppointment(any(CreateAppointmentDTO.class))).thenReturn(appointment);

        // Perform request and verify response
        mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(createAppointmentDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(appointmentId.toString()));

        verify(appointmentService, times(1)).bookAppointment(any(CreateAppointmentDTO.class));
    }

    @Test
    @DisplayName("Test creating a new appointment with error")
    void testCreateAppointmentNull() throws Exception {
        // Setup DTO
        CreateAppointmentDTO createAppointmentDTO = new CreateAppointmentDTO();

        // Mock service call
        when(appointmentService.bookAppointment(any(CreateAppointmentDTO.class))).thenReturn(null);

        // Perform request and verify response
        mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(createAppointmentDTO)))
                .andExpect(status().isNotFound());

        verify(appointmentService, times(1)).bookAppointment(any(CreateAppointmentDTO.class));
    }

    @Test
    @DisplayName("Test canceling an existing appointment")
    void testCancelAppointmentSuccess() throws Exception {
        UUID appointmentId = UUID.randomUUID();

        // Mock service call to return true indicating successful cancellation
        when(appointmentService.cancelAppointment(appointmentId)).thenReturn(true);

        // Perform request and verify response
        mockMvc.perform(delete(url + "/" + appointmentId.toString()))
                .andExpect(status().isOk());

        verify(appointmentService, times(1)).cancelAppointment(appointmentId);
    }

    @Test
    @DisplayName("Test canceling a non-existing appointment")
    void testCancelAppointmentNotFound() throws Exception {
        UUID appointmentId = UUID.randomUUID();

        // Mock service call to return false indicating appointment not found
        when(appointmentService.cancelAppointment(appointmentId)).thenReturn(false);

        // Perform request and verify response
        mockMvc.perform(delete(url + "/" + appointmentId.toString()))
                .andExpect(status().isNotFound());

        verify(appointmentService, times(1)).cancelAppointment(appointmentId);
    }

    @Test
    @DisplayName("Test getting appointments by patient when appointments exist")
    void testGetAppointmentsByPatientSuccess() throws Exception {
        UUID patientId = UUID.randomUUID();

        // Setup mock data
        Appointment appointment = new Appointment();
        appointment.setId(UUID.randomUUID());
        appointment.setType("type");
        appointment.setStatus("Created");
        appointment.setDate(Date.from(Instant.parse("2020-02-10T00:00:00Z")));
        appointment.setPrice(10.0);

        List<Appointment> appointments = List.of(appointment);

        // Mock service call to return a list of appointments
        when(appointmentService.getAppointmentsByPatient(patientId)).thenReturn(appointments);

        // Perform request and verify response
        mockMvc.perform(get(url + "/patient/" + patientId.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(appointment.getId().toString()));

        verify(appointmentService, times(1)).getAppointmentsByPatient(patientId);
    }

    @Test
    @DisplayName("Test getting appointments by patient when no appointments exist")
    void testGetAppointmentsByPatientNotFound() throws Exception {
        UUID patientId = UUID.randomUUID();

        // Mock service call to return an empty list
        when(appointmentService.getAppointmentsByPatient(patientId)).thenReturn(List.of());

        // Perform request and verify response
        mockMvc.perform(get(url + "/patient/" + patientId.toString()))
                .andExpect(status().isNotFound());

        verify(appointmentService, times(1)).getAppointmentsByPatient(patientId);
    }

    @Test
    @DisplayName("Test getting appointments by patient when appointments is null")
    void testGetAppointmentsByPatientNull() throws Exception {
        UUID patientId = UUID.randomUUID();

        // Mock service call to return null
        when(appointmentService.getAppointmentsByPatient(patientId)).thenReturn(null);

        // Perform request and verify response
        mockMvc.perform(get(url + "/patient/" + patientId.toString()))
                .andExpect(status().isNotFound());

        verify(appointmentService, times(1)).getAppointmentsByPatient(patientId);
    }

    @Test
    @DisplayName("Test getting today's appointments when appointments exist")
    void testGetAppointmentsTodaySuccess() throws Exception {
        // Setup mock data
        Appointment appointment = new Appointment();
        appointment.setId(UUID.randomUUID());
        appointment.setType("type");
        appointment.setStatus("Created");
        appointment.setDate(new Date()); // Today's date
        appointment.setPrice(10.0);

        List<Appointment> todayAppointments = List.of(appointment);

        // Mock service call to return a list of today's appointments
        when(appointmentService.getAppointmentsByDate(any(Date.class))).thenReturn(todayAppointments);

        // Perform request and verify response
        mockMvc.perform(get(url + "/today"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(appointment.getId().toString()));

        verify(appointmentService, times(1)).getAppointmentsByDate(any(Date.class));
    }

    @Test
    @DisplayName("Test getting today's appointments when no appointments exist")
    void testGetAppointmentsTodayNoContent() throws Exception {
        // Mock service call to return an empty list
        when(appointmentService.getAppointmentsByDate(any(Date.class))).thenReturn(List.of());

        // Perform request and verify response
        mockMvc.perform(get(url + "/today"))
                .andExpect(status().isNoContent());

        verify(appointmentService, times(1)).getAppointmentsByDate(any(Date.class));
    }
}