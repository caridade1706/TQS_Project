package pt.ua.deti.tqs.cliniconnect.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class QueueManagementTest {

    private QueueManagement queueManagement;

    @BeforeEach
    void setUp() {
        queueManagement = new QueueManagement();
    }
    //QueueManagement(Long, String, Date, String, Hospital)
    @Test
    void createQueueManagementTest() {
        assertNotNull(queueManagement);

        Long id = 1L;
        Date date = new Date();
        Hospital hospital = new Hospital();

        queueManagement = new QueueManagement(id, "normal", date, "4", hospital);

        assertEquals(id, queueManagement.getId());
        assertEquals("normal", queueManagement.getPriorityStatus());
        assertEquals(date, queueManagement.getArrivalTime());
        assertEquals("4", queueManagement.getQueueNumber());
        assertEquals(hospital, queueManagement.getHospital());
    }

    @Test
    void testSetters() {
        Long id = 1L;
        Date date = new Date();
        Hospital hospital = new Hospital();

        queueManagement.setId(id);
        queueManagement.setPriorityStatus("normal");
        queueManagement.setArrivalTime(date);
        queueManagement.setQueueNumber("4");
        queueManagement.setHospital(hospital);

        assertEquals(id, queueManagement.getId());
        assertEquals("normal", queueManagement.getPriorityStatus());
        assertEquals(date, queueManagement.getArrivalTime());
        assertEquals("4", queueManagement.getQueueNumber());
        assertEquals(hospital, queueManagement.getHospital());
    }

    
}
