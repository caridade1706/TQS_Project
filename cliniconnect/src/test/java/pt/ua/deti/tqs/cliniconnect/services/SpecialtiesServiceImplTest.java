package pt.ua.deti.tqs.cliniconnect.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pt.ua.deti.tqs.cliniconnect.models.Patient;
import pt.ua.deti.tqs.cliniconnect.models.Specialties;
import pt.ua.deti.tqs.cliniconnect.repositories.SpecialtiesRepository;
import pt.ua.deti.tqs.cliniconnect.services.impl.SpecialtiesServiceImpl;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class SpecialtiesServiceImplTest {

    @Mock
    private SpecialtiesRepository specialtiesRepository;

    @InjectMocks
    private SpecialtiesServiceImpl specialtiesServiceImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Testa a obtenção de especialidades por paciente existente")
    void testGetByPatient() {
        Patient patient = new Patient();
        HashMap<String, Integer> specialties = new HashMap<>();
        specialties.put("Cardiology", 5);
        specialties.put("Neurology", 3);

        when(specialtiesRepository.findByPatient(patient)).thenReturn(specialties);

        HashMap<String, Integer> returnedSpecialties = specialtiesServiceImpl.getByPatient(patient);
        assertEquals(specialties, returnedSpecialties);

        verify(specialtiesRepository, times(1)).findByPatient(patient);
    }

    @Test
    @DisplayName("Testa a obtenção de especialidades por paciente inexistente")
    void testGetByPatientNotFound() {
        Patient patient = new Patient();

        when(specialtiesRepository.findByPatient(patient)).thenReturn(null);

        HashMap<String, Integer> returnedSpecialties = specialtiesServiceImpl.getByPatient(patient);
        assertNotNull(returnedSpecialties);
        assertTrue(returnedSpecialties.isEmpty());

        verify(specialtiesRepository, times(1)).findByPatient(patient);
    }

    @Test
    @DisplayName("Testa a adição de especialidades a um paciente com especialidades existentes")
    void testAddSpecialtyWithExistingSpecialties() {
        Patient patient = new Patient();
        HashMap<String, Integer> existingSpecialties = new HashMap<>();
        existingSpecialties.put("Neurology", 3);

        HashMap<String, Integer> newSpecialties = new HashMap<>();
        newSpecialties.put("Cardiology", 5);

        HashMap<String, Integer> combinedSpecialties = new HashMap<>(existingSpecialties);
        combinedSpecialties.putAll(newSpecialties);

        Specialties specialtiesObject = new Specialties(patient, combinedSpecialties);

        when(specialtiesRepository.findByPatient(patient)).thenReturn(existingSpecialties);
        when(specialtiesRepository.save(any(Specialties.class))).thenReturn(specialtiesObject);

        Specialties returnedSpecialties = specialtiesServiceImpl.addSpecialty(patient, newSpecialties);
        assertEquals(specialtiesObject, returnedSpecialties);

        verify(specialtiesRepository, times(1)).findByPatient(patient);
        verify(specialtiesRepository, times(1)).save(any(Specialties.class));
    }

    @Test
    @DisplayName("Testa a adição de especialidades a um paciente sem especialidades existentes")
    void testAddSpecialtyWithoutExistingSpecialties() {
        Patient patient = new Patient();
        HashMap<String, Integer> newSpecialties = new HashMap<>();
        newSpecialties.put("Cardiology", 5);

        Specialties specialtiesObject = new Specialties(patient, newSpecialties);

        when(specialtiesRepository.findByPatient(patient)).thenReturn(null);
        when(specialtiesRepository.save(any(Specialties.class))).thenReturn(specialtiesObject);

        Specialties returnedSpecialties = specialtiesServiceImpl.addSpecialty(patient, newSpecialties);
        assertEquals(specialtiesObject, returnedSpecialties);

        verify(specialtiesRepository, times(1)).findByPatient(patient);
        verify(specialtiesRepository, times(1)).save(any(Specialties.class));
    }

    @Test
    @DisplayName("Testa a remoção de uma especialidade de um paciente existente")
    void testDeleteSpecialty() {
        Patient patient = new Patient();
        String specialty = "Cardiology";
        HashMap<String, Integer> specialties = new HashMap<>();
        specialties.put(specialty, 5);

        Specialties specialtiesObject = new Specialties(patient, specialties);
        Specialties updatedSpecialtiesObject = new Specialties(patient, new HashMap<>());

        when(specialtiesRepository.findByPatient(patient)).thenReturn(specialties);
        when(specialtiesRepository.save(any(Specialties.class))).thenReturn(updatedSpecialtiesObject);

        Specialties returnedSpecialties = specialtiesServiceImpl.deleteSpecialty(patient, specialty);
        assertEquals(updatedSpecialtiesObject, returnedSpecialties);

        verify(specialtiesRepository, times(1)).findByPatient(patient);
        verify(specialtiesRepository, times(1)).save(any(Specialties.class));
    }

    @Test
    @DisplayName("Testa a remoção de uma especialidade que não existe de um paciente")
    void testDeleteNonExistingSpecialty() {
        Patient patient = new Patient();
        String specialty = "Cardiology";
        HashMap<String, Integer> specialties = new HashMap<>();

        Specialties specialtiesObject = new Specialties(patient, specialties);

        when(specialtiesRepository.findByPatient(patient)).thenReturn(specialties);
        when(specialtiesRepository.save(any(Specialties.class))).thenReturn(specialtiesObject);

        Specialties returnedSpecialties = specialtiesServiceImpl.deleteSpecialty(patient, specialty);
        assertEquals(specialtiesObject, returnedSpecialties);

        verify(specialtiesRepository, times(1)).findByPatient(patient);
        verify(specialtiesRepository, times(1)).save(any(Specialties.class));
    }

    @Test
    @DisplayName("Testa a remoção de uma especialidade de um paciente não existente")
    void testDeleteSpecialtyPatientNotFound() {
        Patient patient = new Patient();
        String specialty = "Cardiology";

        when(specialtiesRepository.findByPatient(patient)).thenReturn(null);

        Specialties returnedSpecialties = specialtiesServiceImpl.deleteSpecialty(patient, specialty);
        assertNotNull(returnedSpecialties);
        assertTrue(returnedSpecialties.getSpecialty().isEmpty());

        verify(specialtiesRepository, times(1)).findByPatient(patient);
        verify(specialtiesRepository, times(0)).save(any(Specialties.class));
    }
}
