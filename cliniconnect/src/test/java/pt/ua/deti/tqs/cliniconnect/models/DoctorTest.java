package pt.ua.deti.tqs.cliniconnect.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class DoctorTest {

    private Doctor doctor;

    @BeforeEach
    public void setUp() {
        doctor = new Doctor();
    }

    @Test
    void testSettersAndGetters() {
        assertNotNull(doctor);
        doctor.setSpeciality("speciality");
        doctor.setHospitals(new HashSet<>());
        doctor.setAppointments(new HashSet<>());
        doctor.setEmail("email");

        assertEquals("speciality", doctor.getSpeciality());
        assertEquals(new HashSet<>(), doctor.getHospitals());
        assertEquals(new HashSet<>(), doctor.getAppointments());
        assertEquals("email", doctor.getUsername());
    }
    
}
