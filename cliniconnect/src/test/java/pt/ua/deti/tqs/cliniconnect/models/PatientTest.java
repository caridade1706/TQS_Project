package pt.ua.deti.tqs.cliniconnect.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class PatientTest {

    private Patient patient;

    @BeforeEach
    void setUp() {
        patient = new Patient();
    }

    @Test
    void testSettersAndGetters() {
        assertNotNull(patient);
        patient.setAppointments(new HashSet<>());
        patient.setEmail("email");

        
        assertEquals(new HashSet<>(), patient.getAppointments());
        assertEquals("email", patient.getUsername());
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
