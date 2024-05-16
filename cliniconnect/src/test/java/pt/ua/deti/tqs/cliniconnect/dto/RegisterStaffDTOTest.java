package pt.ua.deti.tqs.cliniconnect.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;

class RegisterStaffDTOTest {

    private RegisterStaffDTO registerStaffDTO;

    @BeforeEach
    void setUp() {
        registerStaffDTO = new RegisterStaffDTO();
    }

    @Test
    void testSettersAndGetters() {

        Date date = new Date();

        registerStaffDTO.setName("name");
        registerStaffDTO.setDob(date);
        registerStaffDTO.setEmail("email");
        registerStaffDTO.setPassword("password");
        registerStaffDTO.setPhone("123456789");
        registerStaffDTO.setAddress("address");
        registerStaffDTO.setCity("city");
        registerStaffDTO.setDepartment("department");
        registerStaffDTO.setTask("task");

        assertEquals("name", registerStaffDTO.getName());
        assertEquals(date, registerStaffDTO.getDob());
        assertEquals("email", registerStaffDTO.getEmail());
        assertEquals("password", registerStaffDTO.getPassword());
        assertEquals("123456789", registerStaffDTO.getPhone());
        assertEquals("address", registerStaffDTO.getAddress());
        assertEquals("city", registerStaffDTO.getCity());
        assertEquals("department", registerStaffDTO.getDepartment());
        assertEquals("task", registerStaffDTO.getTask());

    }        
    
}
