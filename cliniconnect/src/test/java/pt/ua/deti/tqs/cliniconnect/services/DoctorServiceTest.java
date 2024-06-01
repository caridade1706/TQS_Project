package pt.ua.deti.tqs.cliniconnect.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.List;
import java.util.ArrayList;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import pt.ua.deti.tqs.cliniconnect.models.Doctor;
import pt.ua.deti.tqs.cliniconnect.models.Hospital;
import pt.ua.deti.tqs.cliniconnect.dto.AddDoctorDTO;
import pt.ua.deti.tqs.cliniconnect.models.Appointment;
import pt.ua.deti.tqs.cliniconnect.repositories.DoctorRepository;
import pt.ua.deti.tqs.cliniconnect.repositories.HospitalRepository;
import pt.ua.deti.tqs.cliniconnect.services.impl.DoctorServiceImpl;

@ExtendWith(MockitoExtension.class)
class DoctorServiceTest {

    @Mock(lenient = true)
    DoctorRepository doctorRepository;

    @Mock(lenient = true)
    HospitalRepository hospitalRepository;

    @InjectMocks
    DoctorServiceImpl doctorService;

    @Test
    @DisplayName("Test save doctor")
    void testSaveDoctor() {
        UUID doctorUuid = UUID.randomUUID();

        AddDoctorDTO addDoctorDTO = new AddDoctorDTO();
        addDoctorDTO.setName("DoctorName");
        addDoctorDTO.setDob(new Date());
        addDoctorDTO.setEmail("d@ua.pt");
        addDoctorDTO.setPhone("123456789");
        addDoctorDTO.setAddress("Rua");
        addDoctorDTO.setCity("Aveiro");
        addDoctorDTO.setSpeciality("Cardiology");
        addDoctorDTO.setHospital("Hospital");

        Hospital hospital = new Hospital();
        hospital.setId(UUID.randomUUID());
        hospital.setName(addDoctorDTO.getHospital());
        hospital.setAddress("Hospital Address");
        hospital.setCity("Hospital City");


        Set<Hospital> hospitals = new HashSet<>();
        hospitals.add(hospital);

        Doctor expectedDoctor = new Doctor();
        expectedDoctor.setId(doctorUuid);
        expectedDoctor.setName(addDoctorDTO.getName());
        expectedDoctor.setAddress(addDoctorDTO.getAddress());
        expectedDoctor.setCity(addDoctorDTO.getCity());
        expectedDoctor.setAppointments(null);
        expectedDoctor.setHospitals(hospitals);

        // Mock the save operation
        when(hospitalRepository.findByName(addDoctorDTO.getHospital())).thenReturn(java.util.Optional.of(hospital));
        when(doctorRepository.save(any(Doctor.class))).thenReturn(expectedDoctor);

        Doctor actualDoctor = doctorService.addDoctor(addDoctorDTO);

        assertEquals(expectedDoctor.getName(), actualDoctor.getName());
        assertEquals(expectedDoctor.getAddress(), expectedDoctor.getAddress());
        assertEquals(expectedDoctor.getCity(), expectedDoctor.getCity());

        verify(doctorRepository, times(1)).save(any(Doctor.class));
    }

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