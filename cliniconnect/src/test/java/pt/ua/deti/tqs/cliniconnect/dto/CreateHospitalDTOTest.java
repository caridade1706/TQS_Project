package pt.ua.deti.tqs.cliniconnect.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;

public class CreateHospitalDTOTest {

    private CreateHospitalDTO createHospitalDTO;

    @BeforeEach
    void setUp() {
        createHospitalDTO = new CreateHospitalDTO();
    }

    @Test
    void testAllConstructer() {
        CreateHospitalDTO createHospitalDTO = new CreateHospitalDTO("hospitalName", "address", "city");
        assertEquals("hospitalName", createHospitalDTO.getName());
        assertEquals("address", createHospitalDTO.getAddress());
        assertEquals("city", createHospitalDTO.getCity());
    }

    @Test
    void testSettersAndGetters() {
        createHospitalDTO.setName("hospitalName");
        createHospitalDTO.setAddress("address");
        createHospitalDTO.setCity("city");
        
        assertEquals("hospitalName", createHospitalDTO.getName());
        assertEquals("address", createHospitalDTO.getAddress());
        assertEquals("city", createHospitalDTO.getCity());
    }        
}