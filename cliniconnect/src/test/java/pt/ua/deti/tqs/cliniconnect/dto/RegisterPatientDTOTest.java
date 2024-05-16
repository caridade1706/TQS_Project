package pt.ua.deti.tqs.cliniconnect.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;

class RegisterPatientDTOTest {
    
    private RegisterPatientDTO registerPatientDTO;

    @BeforeEach
    void setUp() {
        registerPatientDTO = new RegisterPatientDTO();
    }

    @Test
    void testSettersAndGetters() {

        Date date = new Date();

        registerPatientDTO.setName("name");
        registerPatientDTO.setDob(date);
        registerPatientDTO.setEmail("email");
        registerPatientDTO.setPassword("password");
        registerPatientDTO.setPhone("123456789");
        registerPatientDTO.setAddress("address");
        registerPatientDTO.setCity("city");
        registerPatientDTO.setPreferredHospital("Braga");

        assertEquals("name", registerPatientDTO.getName());
        assertEquals(date, registerPatientDTO.getDob());
        assertEquals("email", registerPatientDTO.getEmail());
        assertEquals("password", registerPatientDTO.getPassword());
        assertEquals("123456789", registerPatientDTO.getPhone());
        assertEquals("address", registerPatientDTO.getAddress());
        assertEquals("city", registerPatientDTO.getCity());
        assertEquals("Braga", registerPatientDTO.getPreferredHospital());
    }



   
}
