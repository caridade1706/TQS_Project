package pt.ua.deti.tqs.cliniconnect.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class SpecialtiesTest {

    private Specialties specialties;

    @BeforeEach
    void setUp() {
        specialties = new Specialties();
    }
    
    @Test
    void testGetId() {
        specialties.setId(1L);
        assertEquals(1L, specialties.getId());
    }

    @Test
    void testGetPatient() {
        Patient patient = new Patient();
        specialties.setPatient(patient);
        assertEquals(patient, specialties.getPatient());
    }

    @Test
    void testGetSpecialties() {
        HashMap<String, Integer> specialtiesMap = new HashMap<>();
        specialties.setSpecialties(specialtiesMap);
        assertEquals(specialtiesMap, specialties.getSpecialties());
    }

    @Test
    void testAllArgsConstructor() {
        Patient patient = new Patient();
        HashMap<String, Integer> specialtiesMap = new HashMap<>();
        specialties = new Specialties(patient, specialtiesMap);
        assertEquals(patient, specialties.getPatient());
        assertEquals(specialtiesMap, specialties.getSpecialties());
    }
    
}
