package pt.ua.deti.tqs.cliniconnect.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;

class AddDoctorDTOTest {
    
    private AddDoctorDTO addDoctorDTO;

    @BeforeEach
    void setUp() {
        addDoctorDTO = new AddDoctorDTO();
    }

    @Test
    void testSettersAndGetters() {

        Date date = new Date();

        addDoctorDTO.setName("name");
        addDoctorDTO.setDob(date);
        addDoctorDTO.setEmail("email");
        addDoctorDTO.setPhone("123456789");
        addDoctorDTO.setAddress("address");
        addDoctorDTO.setCity("city");
        addDoctorDTO.setSpeciality("Cardiology");

        assertEquals("name", addDoctorDTO.getName());
        assertEquals(date, addDoctorDTO.getDob());
        assertEquals("email", addDoctorDTO.getEmail());
        assertEquals("123456789", addDoctorDTO.getPhone());
        assertEquals("address", addDoctorDTO.getAddress());
        assertEquals("city", addDoctorDTO.getCity());
        assertEquals("Cardiology", addDoctorDTO.getSpeciality());
    }

    @Test
    void testAllContructor() {
        Date date = new Date();
        AddDoctorDTO addDoctorDTO = new AddDoctorDTO("name", date, "email", "123456789", "address", "city", "Cardiology");

        assertEquals("name", addDoctorDTO.getName());
        assertEquals(date, addDoctorDTO.getDob());
        assertEquals("email", addDoctorDTO.getEmail());
        assertEquals("123456789", addDoctorDTO.getPhone());
        assertEquals("address", addDoctorDTO.getAddress());
        assertEquals("city", addDoctorDTO.getCity());
        assertEquals("Cardiology", addDoctorDTO.getSpeciality());
    }
}