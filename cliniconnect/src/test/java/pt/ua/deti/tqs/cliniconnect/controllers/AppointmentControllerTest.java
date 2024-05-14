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
import pt.ua.deti.tqs.cliniconnect.models.Appointment;
import pt.ua.deti.tqs.cliniconnect.services.AppointmentService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class AppointmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AppointmentService appointmentService;

    @BeforeEach
    public void setUp() {

    }

    @Test
    @DisplayName("Testa a criação de um novo agendamento")
    public void testCreateAppointment() throws Exception {
        Appointment appointment = new Appointment();
        appointment.setId(UUID.randomUUID());
        when(appointmentService.bookAppointment(any(Appointment.class))).thenReturn(appointment);

        mockMvc.perform(post("/api/appointments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\": \"123\", \"patientId\": \"456\", \"doctorId\": \"789\", \"date\": \"2024-04-16T10:00:00Z\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(appointment.getId().toString()));

        
        verify(appointmentService, times(1)).bookAppointment(any(Appointment.class));
    }

    @Test
    @DisplayName("Testa a criação de um novo agendamento e falha")
    public void testCreateAppointmentFail() throws Exception {
        Appointment appointment = new Appointment();
        appointment.setId(UUID.randomUUID());
        when(appointmentService.bookAppointment(any(Appointment.class))).thenReturn(null);

        mockMvc.perform(post("/api/appointments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\": \"123\", \"patientId\": \"456\", \"doctorId\": \"789\", \"date\": \"2024-04-16T10:00:00Z\"}"))
                .andExpect(status().isNotFound());

        
        verify(appointmentService, times(1)).bookAppointment(any(Appointment.class));
    }

    @Test
    @DisplayName("Testa a cancelação de um agendamento")
    public void testCancelAppointment() throws Exception {
        UUID id = UUID.randomUUID();
        when(appointmentService.cancelAppointment(id)).thenReturn(true);

        mockMvc.perform(delete("/api/appointments/" + id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(appointmentService, times(1)).cancelAppointment(id);
    }

    @Test
    @DisplayName("Testa a cancelação de um agendamento e não encontra o agendamento")
    public void testCancelAppointmentNotFound() throws Exception {
        UUID id = UUID.randomUUID();
        when(appointmentService.cancelAppointment(id)).thenReturn(false);

        mockMvc.perform(delete("/api/appointments/" + id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(appointmentService, times(1)).cancelAppointment(id);
    }

    @Test
    @DisplayName("Testa a obtenção de agendamentos por paciente")
    public void testGetAppointmentsByPatient() throws Exception {
        UUID patientId = UUID.randomUUID();
        List<Appointment> appointments = new ArrayList<>();
        appointments.add(new Appointment());
        when(appointmentService.getAppointmentsByPatient(patientId)).thenReturn(appointments);

        mockMvc.perform(get("/api/appointments/patient/" + patientId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
        
        verify(appointmentService, times(1)).getAppointmentsByPatient(patientId);
    }

    @Test
    @DisplayName("Testa a obtenção de agendamentos por paciente e não encontra agendamentos")
    public void testGetAppointmentsByPatientAndAppointmentsNotFound() throws Exception {
        UUID patientId = UUID.randomUUID();
        List<Appointment> appointments = new ArrayList<>();
        when(appointmentService.getAppointmentsByPatient(patientId)).thenReturn(appointments);

        mockMvc.perform(get("/api/appointments/patient/" + patientId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
        
        verify(appointmentService, times(1)).getAppointmentsByPatient(patientId);
    }

    @Test
    @DisplayName("Testa a obtenção de agendamentos por paciente e null")
    public void testGetAppointmentsByPatientAndAppointmentsNull() throws Exception {
        UUID patientId = UUID.randomUUID();
        List<Appointment> appointments = null;
        when(appointmentService.getAppointmentsByPatient(patientId)).thenReturn(appointments);

        mockMvc.perform(get("/api/appointments/patient/" + patientId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
        
        verify(appointmentService, times(1)).getAppointmentsByPatient(patientId);
    }
}