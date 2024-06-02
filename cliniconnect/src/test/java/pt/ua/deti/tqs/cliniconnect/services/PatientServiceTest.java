package pt.ua.deti.tqs.cliniconnect.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import pt.ua.deti.tqs.cliniconnect.models.Patient;
import pt.ua.deti.tqs.cliniconnect.models.Appointment;
import pt.ua.deti.tqs.cliniconnect.repositories.PatientRepository;
import pt.ua.deti.tqs.cliniconnect.services.impl.PatientServiceImpl;

@ExtendWith(MockitoExtension.class)
class PatientServiceTest {

    @Mock(lenient = true)
    PatientRepository patientRepository;

    @InjectMocks
    PatientServiceImpl patientService;

    @Test
    void testGetPatientByEmail() {

        String email = "robertorcastro@ua.pt";
        Set<Appointment> appointments = new HashSet<>();

        Patient expectedPatient = new Patient(UUID.randomUUID(), "Roberto Castro", new Date(), email, "password",
                "989345890", "Rua do Hospital", "Porto", "232442343434", "Lisboa CliniConnect Hospital", appointments);

        when(patientRepository.findByEmail(email)).thenReturn(expectedPatient);

        Patient actualPatient = patientService.getPatientByEmail(email);

        assertEquals(expectedPatient, actualPatient);
        verify(patientRepository, times(1)).findByEmail(email);
    }
}