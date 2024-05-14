package pt.ua.deti.tqs.cliniconnect.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.Instant;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import pt.ua.deti.tqs.cliniconnect.models.Appointment;
import pt.ua.deti.tqs.cliniconnect.models.Patient;
import pt.ua.deti.tqs.cliniconnect.repositories.AppointmentRepository;
import pt.ua.deti.tqs.cliniconnect.services.impl.AppointmentServiceImpl;

@ExtendWith(MockitoExtension.class)
public class AppointmentServiceTest {

    @Mock(lenient = true)
    AppointmentRepository appointmentRepository;

    @InjectMocks
    AppointmentServiceImpl appointmentService;

    Patient patient;
    Appointment expectedAppointment;

    @BeforeEach
    void setUp() {
        expectedAppointment = new Appointment(UUID.randomUUID(), Date.from(Instant.parse("2024-07-10T00:00:00Z")),
                LocalTime.of(15, 30, 0), "confirmed", 50.0, "Cardioligy", "EUR", null, null, null);
    }

    @Test
    void testBookAppointment() {

        when(appointmentRepository.save(expectedAppointment)).thenReturn(expectedAppointment);

        Appointment actualAppointment = appointmentService.bookAppointment(expectedAppointment);

        assertEquals(expectedAppointment, actualAppointment);
        verify(appointmentRepository, times(1)).save(expectedAppointment);
    }

    @Test
    void testCancelAppointmentExist() {

        when(appointmentRepository.existsById(expectedAppointment.getId())).thenReturn(true);
        doNothing().when(appointmentRepository).deleteById(expectedAppointment.getId());

        appointmentService.cancelAppointment(expectedAppointment.getId());

        verify(appointmentRepository, times(1)).existsById(expectedAppointment.getId());
        verify(appointmentRepository, times(1)).deleteById(expectedAppointment.getId());

    }

    @Test
    void testCancelAppointmentDoNotExist() {

        when(appointmentRepository.existsById(expectedAppointment.getId())).thenReturn(false);
        doNothing().when(appointmentRepository).deleteById(expectedAppointment.getId());

        appointmentService.cancelAppointment(expectedAppointment.getId());

        verify(appointmentRepository, times(1)).existsById(expectedAppointment.getId());
        verify(appointmentRepository, times(0)).deleteById(expectedAppointment.getId());

    }

    @Test
    void testGetAppointmentsByPatient() {

        Patient patient = new Patient(UUID.randomUUID(), "Roberto Castro", new Date(), "robertorcasto@ua.pt",
                "password",
                "989345890", "Rua do Hospital", "Porto", "Lisboa CliniConnect Hospital", null);
        expectedAppointment = new Appointment(UUID.randomUUID(), Date.from(Instant.parse("2024-07-10T00:00:00Z")),
                LocalTime.of(15, 30, 0), "confirmed", 50.0, "Cardioligy", "EUR", patient, null, null);

        List<Appointment> expectedAppointmentList = new ArrayList<Appointment>();
        expectedAppointmentList.add(expectedAppointment);

        when(appointmentRepository.findByPatient_Id(patient.getId())).thenReturn(expectedAppointmentList);

        List<Appointment> actualAppointmentList = appointmentService.getAppointmentsByPatient(patient.getId());

        assertEquals(expectedAppointmentList, actualAppointmentList);
        verify(appointmentRepository, times(1)).findByPatient_Id(patient.getId());
    }
}