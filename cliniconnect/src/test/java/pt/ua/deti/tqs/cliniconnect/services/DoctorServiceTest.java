package pt.ua.deti.tqs.cliniconnect.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.List;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import pt.ua.deti.tqs.cliniconnect.models.Doctor;
import pt.ua.deti.tqs.cliniconnect.models.Hospital;
import pt.ua.deti.tqs.cliniconnect.models.Appointment;
import pt.ua.deti.tqs.cliniconnect.repositories.DoctorRepository;
import pt.ua.deti.tqs.cliniconnect.services.impl.DoctorServiceImpl;

@ExtendWith(MockitoExtension.class)
public class DoctorServiceTest {

    @Mock(lenient = true)
    DoctorRepository doctorRepository;

    @InjectMocks
    DoctorServiceImpl doctorService;

    @Test
    void testGetDoctorsBySpeciality() {

        String speciality = "Cardiologist";

        Set<Hospital> hospitals = new HashSet<>();
        Set<Appointment> appointments = new HashSet<>();

        Doctor doctor = new Doctor(UUID.randomUUID(), "Jaime Cordeiro", new Date(), "jaimecordeiro@ua.pt", "password",
                "989345890", "Rua do Hospital", "Porto", speciality, hospitals, appointments);

        List<Doctor> expectedDoctors = new ArrayList<>();
        expectedDoctors.add(doctor);

        when(doctorRepository.findBySpeciality(speciality)).thenReturn(expectedDoctors);

        List<Doctor> actualDoctors = doctorService.getDoctorsBySpeciality(speciality);

        assertEquals(expectedDoctors, actualDoctors);
        verify(doctorRepository, times(1)).findBySpeciality(speciality);
    }
}