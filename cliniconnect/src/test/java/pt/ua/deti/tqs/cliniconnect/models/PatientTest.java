package pt.ua.deti.tqs.cliniconnect.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class PatientTest {

    private Patient patient;

    @BeforeEach
    void setUp() {
        patient = new Patient();
    }

    @Test
    void testSettersAndGetters() {
        assertNotNull(patient);
        patient.setPatientNumber("876556789");
        patient.setAppointments(new HashSet<>());
        patient.setEmail("email");

        assertEquals("876556789", patient.getPatientNumber());
        assertEquals(new HashSet<>(), patient.getAppointments());
        assertEquals("email", patient.getUsername());
    }

    @Test
    void testPatientNumber() {
        patient.setPatientNumber("876556789");
        assertEquals("876556789", patient.getPatientNumber());
    }

    @Test
    void testPreferredHospital() {
        patient.setPreferredHospital("hospital");
        assertEquals("hospital", patient.getPreferredHospital());
    }

    @Test
    void testAppointments() {
        Set<Appointment> appointments = new HashSet<>();
        Appointment appointment = new Appointment();
        appointments.add(appointment);
        patient.setAppointments(appointments);
        assertEquals(appointments, patient.getAppointments());
    }
}