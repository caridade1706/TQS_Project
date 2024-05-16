package pt.ua.deti.tqs.cliniconnect.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import pt.ua.deti.tqs.cliniconnect.models.Patient;

public class PatientDetailsDTOTest {

    private PatientDetailsDTO patientDetailsDTO;

    @BeforeEach
    void setUp() {
        patientDetailsDTO = new PatientDetailsDTO();
    }

    @Test
    void testGetPatient() {
        Patient patient = new Patient();
        patientDetailsDTO.setPatient(patient);
        assertEquals(patient, patientDetailsDTO.getPatient());
    }

    @Test
    void testSetPatient() {
        Patient patient = new Patient();
        patientDetailsDTO.setPatient(patient);
        assertEquals(patient, patientDetailsDTO.getPatient());
    }

    @Test
    void testGetSpecialties() {
        assertEquals(null, patientDetailsDTO.getSpecialties());
    }

    @Test
    void testSetSpecialties() {
        patientDetailsDTO.setSpecialties(null);
        assertEquals(null, patientDetailsDTO.getSpecialties());
    }

    @Test
    void testAllArgsConstructor() {
        Patient patient = new Patient();
        patientDetailsDTO = new PatientDetailsDTO(patient, null);
        assertEquals(patient, patientDetailsDTO.getPatient());
        assertEquals(null, patientDetailsDTO.getSpecialties());
    }
    
}
