package pt.ua.deti.tqs.cliniconnect.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class HospitalTest {

    private Hospital hospital;

    @BeforeEach
    void setUp() {
        hospital = new Hospital();
    }

    @Test
    void createHospitalTest() {
        assertNotNull(hospital);

        UUID id = UUID.randomUUID();

        hospital = new Hospital(id, "name", "address", "city", new HashSet<>(), new HashSet<>(), new HashSet<>(), new QueueManagement());

        assertEquals(id, hospital.getId());
        assertEquals("name", hospital.getName());
        assertEquals("address", hospital.getAddress());
        assertEquals("city", hospital.getCity());
    }

    @Test
    void testSetters() {
        UUID id = UUID.randomUUID();
        QueueManagement queueManagement = new QueueManagement();

        hospital.setId(id);
        hospital.setName("name");
        hospital.setAddress("address");
        hospital.setCity("city");
        hospital.setStaffs(new HashSet<>());
        hospital.setDoctors(new HashSet<>());
        hospital.setAppointments(new HashSet<>());
        hospital.setQueueManagement(queueManagement);

        assertEquals(id, hospital.getId());
        assertEquals("name", hospital.getName());
        assertEquals("address", hospital.getAddress());
        assertEquals("city", hospital.getCity());
        assertEquals(new HashSet<>(), hospital.getStaffs());
        assertEquals(new HashSet<>(), hospital.getDoctors());
        assertEquals(new HashSet<>(), hospital.getAppointments());
        assertEquals(queueManagement, hospital.getQueueManagement());
    }


    
}
