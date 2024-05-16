package pt.ua.deti.tqs.cliniconnect.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class StaffsTest {

    private Staff staff;

    @BeforeEach
    public void setUp() {
        staff = new Staff();
    }

    @Test
    void testSettersAndGetters() {
        assertNotNull(staff);
        staff.setDepartment("department");
        staff.setTask("task");
        staff.setHospitals(new HashSet<>());
        staff.setEmail("email");

        assertEquals("department", staff.getDepartment());
        assertEquals("task", staff.getTask());
        assertEquals(new HashSet<>(), staff.getHospitals());
        assertEquals("email", staff.getUsername());
    }

}
