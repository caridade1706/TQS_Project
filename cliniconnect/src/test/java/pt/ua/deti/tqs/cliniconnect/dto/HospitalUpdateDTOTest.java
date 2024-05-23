package pt.ua.deti.tqs.cliniconnect.dto;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class HospitalUpdateDTOTest {

    private HospitalUpdateDTO hospitalUpdateDTO;

    @BeforeEach
    void setUp() {
        hospitalUpdateDTO = new HospitalUpdateDTO();
    }

    @Test
    void testAllConstructer() {
        List<String> hospitals = new ArrayList<>();
        HospitalUpdateDTO hospitalUpdateDTO = new HospitalUpdateDTO(hospitals);
        assertEquals(hospitals.size(), hospitalUpdateDTO.getHospitals().size());
    }

    @Test
    void testGetterSetter() {
        List<String> hospitals = new ArrayList<>();
        hospitalUpdateDTO.setHospitals(hospitals);
        assertEquals(hospitals.size(), hospitalUpdateDTO.getHospitals().size());
    }
    
}
