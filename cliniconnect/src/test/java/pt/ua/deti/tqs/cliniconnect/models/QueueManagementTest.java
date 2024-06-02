package pt.ua.deti.tqs.cliniconnect.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import pt.ua.deti.tqs.cliniconnect.PriorityStatus;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class QueueManagementTest {

    private QueueManagement queueManagement;

    @BeforeEach
    void setUp() {
        queueManagement = new QueueManagement();
    }
    
    @Test
    void createQueueManagementTest() {
        assertNotNull(queueManagement);

        UUID id = UUID.randomUUID();
        Hospital hospital = new Hospital();

        LocalDateTime arrivalTime = LocalDateTime.now();
        LocalDateTime calledTime = LocalDateTime.now();

        queueManagement = new QueueManagement(id, PriorityStatus.A , arrivalTime, calledTime, false, false, "4",  "4", hospital);

        assertEquals(id, queueManagement.getId());
        assertEquals(PriorityStatus.A, queueManagement.getPriorityStatus());
        assertEquals(arrivalTime, queueManagement.getArrivalTime());
        assertEquals(calledTime, queueManagement.getCalledTime());
        assertEquals(false, queueManagement.isCalled());
        assertEquals("4", queueManagement.getCounterNumber());
        assertEquals("4", queueManagement.getQueueNumber());
        assertEquals(hospital, queueManagement.getHospital());
    }

    @Test
    void testSetters() {
        
        UUID id = UUID.randomUUID();

        Hospital hospital = new Hospital();
        LocalDateTime time = LocalDateTime.now();

        queueManagement.setId(id);
        queueManagement.setPriorityStatus(PriorityStatus.A);
        queueManagement.setArrivalTime(time);
        queueManagement.setQueueNumber("4");
        queueManagement.setHospital(hospital);
        queueManagement.setCalled(true);
        queueManagement.setCounterNumber("4");
        queueManagement.setCalledTime(time);

        assertEquals(id, queueManagement.getId());
        assertEquals(PriorityStatus.A, queueManagement.getPriorityStatus());
        assertEquals(time, queueManagement.getArrivalTime());
        assertEquals("4", queueManagement.getQueueNumber());
        assertEquals(hospital, queueManagement.getHospital());
        assertEquals(true, queueManagement.isCalled());
        assertEquals("4", queueManagement.getCounterNumber());
        assertEquals(time, queueManagement.getCalledTime());
    }

    
}
