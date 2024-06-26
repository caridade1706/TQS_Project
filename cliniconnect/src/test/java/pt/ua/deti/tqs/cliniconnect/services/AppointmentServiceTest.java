package pt.ua.deti.tqs.cliniconnect.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.Instant;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.UUID;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import pt.ua.deti.tqs.cliniconnect.Roles;
import pt.ua.deti.tqs.cliniconnect.dto.CreateAppointmentDTO;
import pt.ua.deti.tqs.cliniconnect.models.Appointment;
import pt.ua.deti.tqs.cliniconnect.models.Doctor;
import pt.ua.deti.tqs.cliniconnect.models.Hospital;
import pt.ua.deti.tqs.cliniconnect.models.Patient;
import pt.ua.deti.tqs.cliniconnect.models.Persona;
import pt.ua.deti.tqs.cliniconnect.repositories.AppointmentRepository;
import pt.ua.deti.tqs.cliniconnect.repositories.DoctorRepository;
import pt.ua.deti.tqs.cliniconnect.repositories.HospitalRepository;
import pt.ua.deti.tqs.cliniconnect.repositories.PatientRepository;
import pt.ua.deti.tqs.cliniconnect.repositories.PersonaRepository;
import pt.ua.deti.tqs.cliniconnect.services.impl.AppointmentServiceImpl;

@ExtendWith(MockitoExtension.class)
class AppointmentServiceTest {

    @Mock(lenient = true)
    AppointmentRepository appointmentRepository;

    @Mock(lenient = true)
    PersonaRepository personaRepository;

    @Mock(lenient = true)
    PatientRepository patientRepository;

    @Mock(lenient = true)
    DoctorRepository doctorRepository;

    @Mock(lenient = true)
    HospitalRepository hospitalRepository;

    @InjectMocks
    AppointmentServiceImpl appointmentService;

    @Test
    void testBookAppointment() {

        UUID hospitalId = UUID.randomUUID();
        UUID doctorId = UUID.randomUUID();
        UUID patientId = UUID.randomUUID();
        UUID appointmentId = UUID.randomUUID();
        UUID personaId = UUID.randomUUID();

        // Setup entities
        Hospital hospital = new Hospital();
        hospital.setId(hospitalId);
        hospital.setName("hospitalName");
        hospital.setAddress("address");
        hospital.setCity("city");

        Doctor doctor = new Doctor();
        doctor.setId(doctorId);
        doctor.setName("doctorName");
        doctor.setSpeciality("specialty");
        Set<Hospital> hospitals = new HashSet<>();
        hospitals.add(hospital);
        doctor.setHospitals(hospitals);

        Patient patient = new Patient();
        patient.setId(patientId);
        patient.setName("patientName");
        patient.setAddress("address");
        patient.setCity("city");

        Persona persona = new Persona();
        persona.setId(personaId);
        persona.setName("patientName");

        // Setup DTO
        CreateAppointmentDTO createAppointmentDTO = new CreateAppointmentDTO();
        createAppointmentDTO.setDate(Date.from(Instant.parse("2020-02-10T00:00:00Z")));
        createAppointmentDTO.setTime("10:30"); // Ensure valid time format
        createAppointmentDTO.setPrice(10.0);
        createAppointmentDTO.setType("type");
        createAppointmentDTO.setPatientName(patient.getName());
        createAppointmentDTO.setDoctorName(doctor.getName());
        createAppointmentDTO.setHospitalName(hospital.getName());

        // Setup Appointment
        Appointment expectedAppointment = new Appointment();
        expectedAppointment.setId(appointmentId);
        expectedAppointment.setType("type");
        expectedAppointment.setPatient(patient);
        expectedAppointment.setDoctor(doctor);
        expectedAppointment.setHospital(hospital);
        expectedAppointment.setStatus("Created");
        expectedAppointment.setDate(Date.from(Instant.parse("2020-02-10T00:00:00Z")));
        expectedAppointment.setPrice(10.0);

        // Mock repository methods
        when(personaRepository.findByName(createAppointmentDTO.getPatientName())).thenReturn(persona);
        when(patientRepository.findById(persona.getId())).thenReturn(Optional.of(patient));
        when(doctorRepository.findByName(createAppointmentDTO.getDoctorName())).thenReturn(Optional.of(doctor));
        when(hospitalRepository.findByName(createAppointmentDTO.getHospitalName())).thenReturn(Optional.of(hospital));
        when(appointmentRepository.save(any(Appointment.class))).thenReturn(expectedAppointment);

        Appointment actualAppointment = appointmentService.bookAppointment(createAppointmentDTO);

        assertEquals(expectedAppointment.getId(), actualAppointment.getId());
        verify(appointmentRepository, times(1)).save(any(Appointment.class));
    }

    @Test
    @DisplayName("Test Get All Appointments")
    void testGetAllAppointments() {

        Appointment appointment = new Appointment();
        appointment.setId(UUID.randomUUID());
        appointment.setType("type");
        appointment.setPatient(null);
        appointment.setDoctor(null);
        appointment.setHospital(null);
        appointment.setStatus("Created");
        appointment.setDate(Date.from(Instant.parse("2020-02-10T00:00:00Z")));
        appointment.setPrice(10.0);

        List<Appointment> expectedAppointmentList = new ArrayList<Appointment>();
        expectedAppointmentList.add(appointment);

        when(appointmentRepository.findAll()).thenReturn(expectedAppointmentList);

        List<Appointment> actualAppointmentList = appointmentService.getAllAppointments();

        assertEquals(expectedAppointmentList, actualAppointmentList);
        verify(appointmentRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Teste Cancel Appointment Existing")
    void testCancelAppointmentExist() {

        Appointment expectedAppointment = new Appointment();
        expectedAppointment.setId(UUID.randomUUID());
        expectedAppointment.setType("type");
        expectedAppointment.setPatient(null);
        expectedAppointment.setDoctor(null);
        expectedAppointment.setHospital(null);
        expectedAppointment.setStatus("Created");
        expectedAppointment.setDate(Date.from(Instant.parse("2020-02-10T00:00:00Z")));
        expectedAppointment.setPrice(10.0);

        when(appointmentRepository.existsById(expectedAppointment.getId())).thenReturn(true);
        doNothing().when(appointmentRepository).deleteById(expectedAppointment.getId());

        appointmentService.cancelAppointment(expectedAppointment.getId());

        verify(appointmentRepository, times(1)).existsById(expectedAppointment.getId());
        verify(appointmentRepository, times(1)).deleteById(expectedAppointment.getId());

    }

    @Test
    @DisplayName("Teste Cancel Appointment Not Existing")
    void testCancelAppointmentNotExist() {

        Appointment expectedAppointment = new Appointment();
        expectedAppointment.setId(UUID.randomUUID());
        expectedAppointment.setType("type");
        expectedAppointment.setPatient(null);
        expectedAppointment.setDoctor(null);
        expectedAppointment.setHospital(null);
        expectedAppointment.setStatus("Created");
        expectedAppointment.setDate(Date.from(Instant.parse("2020-02-10T00:00:00Z")));
        expectedAppointment.setPrice(10.0);

        when(appointmentRepository.existsById(expectedAppointment.getId())).thenReturn(false);

        appointmentService.cancelAppointment(expectedAppointment.getId());

        verify(appointmentRepository, times(1)).existsById(expectedAppointment.getId());
        verify(appointmentRepository, times(0)).deleteById(expectedAppointment.getId());
    }

    @Test
    @DisplayName("Test Get Appointments By Patient")
    void testGetAppointmentsByPatient() {

        Patient patient = new Patient(UUID.randomUUID(), "Roberto Castro", new Date(), "robertorcasto@ua.pt",
                "password",
                "989345890", "Rua do Hospital", "Porto", "3455533434433434","Lisboa CliniConnect Hospital", null);

        Appointment expectedAppointment = new Appointment(UUID.randomUUID(),
                Date.from(Instant.parse("2024-07-10T00:00:00Z")),
                LocalTime.of(15, 30, 0), "confirmed", 50.0, "Cardioligy", "EUR", patient, null, null);

        List<Appointment> expectedAppointmentList = new ArrayList<Appointment>();
        expectedAppointmentList.add(expectedAppointment);

        when(appointmentRepository.findByPatient_Id(patient.getId())).thenReturn(expectedAppointmentList);

        List<Appointment> actualAppointmentList = appointmentService.getAppointmentsByPatient(patient.getId());

        assertEquals(expectedAppointmentList, actualAppointmentList);
        verify(appointmentRepository, times(1)).findByPatient_Id(patient.getId());
    }

    @Test
    @DisplayName("Test update appointment status when appointment exists")
    void testUpdateAppointmentStatusExist() {
        UUID appointmentId = UUID.randomUUID();
        String newStatus = "Completed";

        Appointment appointment = new Appointment();
        appointment.setId(appointmentId);
        appointment.setStatus("Created");

        when(appointmentRepository.findById(appointmentId)).thenReturn(Optional.of(appointment));
        when(appointmentRepository.save(any(Appointment.class))).thenReturn(appointment);

        boolean result = appointmentService.updateAppointmentStatus(appointmentId, newStatus);

        assertTrue(result);
        assertEquals(newStatus, appointment.getStatus());
        verify(appointmentRepository, times(1)).findById(appointmentId);
        verify(appointmentRepository, times(1)).save(appointment);
    }

    @Test
    @DisplayName("Test update appointment status when appointment does not exist")
    void testUpdateAppointmentStatusNotExist() {
        UUID appointmentId = UUID.randomUUID();
        String newStatus = "Completed";

        when(appointmentRepository.findById(appointmentId)).thenReturn(Optional.empty());

        boolean result = appointmentService.updateAppointmentStatus(appointmentId, newStatus);

        assertFalse(result);
        verify(appointmentRepository, times(1)).findById(appointmentId);
        verify(appointmentRepository, times(0)).save(any(Appointment.class));
    }

    @Test
    @DisplayName("Test get appointments by date")
    void testGetAppointmentsByDate() {
        
        Date date = Date.from(Instant.parse("2024-07-10T00:00:00Z"));
        Date dateappoint = Date.from(Instant.parse("2024-07-10T09:00:00Z"));

        Instant startOfDay = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
                .atStartOfDay(ZoneId.systemDefault()).toInstant();
        Instant endOfDay = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().plusDays(1)
                .atStartOfDay(ZoneId.systemDefault()).toInstant().minusSeconds(1);

        Date startDate = Date.from(startOfDay);
        Date endDate = Date.from(endOfDay);

        List<Appointment> expectedAppointments = new ArrayList<>();
        expectedAppointments.add(new Appointment(UUID.randomUUID(), dateappoint, null, "confirmed", 50.0, "Cardiology", "EUR", null, null, null));

        when(appointmentRepository.findByDateBetween(startDate, endDate)).thenReturn(expectedAppointments);

        List<Appointment> actualAppointments = appointmentService.getAppointmentsByDate(date);

        assertEquals(expectedAppointments, actualAppointments);
        verify(appointmentRepository, times(1)).findByDateBetween(startDate, endDate);
    }

    @Test
    @DisplayName("Test Get Future Appointments by email Succefully")
    void testGetFutureAppointmentsByEmailSuccefully() {
        
        String email = "email@ua.pt";

        UUID appointmentId = UUID.randomUUID();

        Patient patient = new Patient();
        patient.setId(UUID.randomUUID());
        patient.setEmail(email);
        patient.setName("patientName");
        patient.setAddress("address");
        patient.setCity("city");
        patient.setRole(Roles.PATIENT);
        patient.setAppointments(new HashSet<>());

        // Setup mock data
        Appointment appointment = new Appointment();
        appointment.setId(appointmentId);
        appointment.setType("type");
        appointment.setStatus("Created");
        appointment.setDate(Date.from(Instant.parse("2024-06-10T00:00:00Z")));
        appointment.setTime(LocalTime.parse("10:30"));
        appointment.setPrice(10.0);
        appointment.setPatient(patient);
        appointment.setDoctor(null);
        appointment.setHospital(null);

        List<Appointment> expectedAppointments = List.of(appointment);

        Date date = Date.from(Instant.now());

        when(personaRepository.findByEmail(email)).thenReturn(Optional.of(patient));
        when(appointmentRepository.findByPatient_IdAndDateAfter(patient.getId(), date)).thenReturn(expectedAppointments);

        List<Appointment> actualAppointments = appointmentService.getFutureAppointmentsByUserId(patient.getEmail(), date);
        assertEquals(expectedAppointments.get(0).getId(), actualAppointments.get(0).getId());
        
        verify(personaRepository, times(1)).findByEmail(any(String.class));
        verify(appointmentRepository, times(1)).findByPatient_IdAndDateAfter(any(UUID.class), any(Date.class));
    }

    @Test
    @DisplayName("Test Get Past Appointments by email Succefully")
    void testGetPastAppointmentsByEmailSuccefully() {
        
        String email = "email@ua.pt";

        UUID appointmentId = UUID.randomUUID();

        Patient patient = new Patient();
        patient.setId(UUID.randomUUID());
        patient.setEmail(email);
        patient.setName("patientName");
        patient.setAddress("address");
        patient.setCity("city");
        patient.setRole(Roles.PATIENT);
        patient.setAppointments(new HashSet<>());

        // Setup mock data
        Appointment appointment = new Appointment();
        appointment.setId(appointmentId);
        appointment.setType("type");
        appointment.setStatus("Created");
        appointment.setDate(Date.from(Instant.parse("2024-06-01T00:00:00Z")));
        appointment.setTime(LocalTime.parse("10:30"));
        appointment.setPrice(10.0);
        appointment.setPatient(patient);
        appointment.setDoctor(null);
        appointment.setHospital(null);

        List<Appointment> expectedAppointments = List.of(appointment);

        Date date = Date.from(Instant.now());

        when(personaRepository.findByEmail(email)).thenReturn(Optional.of(patient));
        when(appointmentRepository.findByPatient_IdAndDateBefore(patient.getId(), date)).thenReturn(expectedAppointments);

        List<Appointment> actualAppointments = appointmentService.getPastAppointmentsByUserId(patient.getEmail(), date);
        assertEquals(expectedAppointments.get(0).getId(), actualAppointments.get(0).getId());
        
        verify(personaRepository, times(1)).findByEmail(any(String.class));
        verify(appointmentRepository, times(1)).findByPatient_IdAndDateBefore(any(UUID.class), any(Date.class));
    }

}