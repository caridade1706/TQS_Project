package pt.ua.deti.tqs.cliniconnect.services.impl;

import pt.ua.deti.tqs.cliniconnect.dto.CreateAppointmentDTO;
import pt.ua.deti.tqs.cliniconnect.models.Appointment;
import pt.ua.deti.tqs.cliniconnect.models.Doctor;
import pt.ua.deti.tqs.cliniconnect.models.Hospital;
import pt.ua.deti.tqs.cliniconnect.models.Patient;
import pt.ua.deti.tqs.cliniconnect.models.Persona;
import pt.ua.deti.tqs.cliniconnect.repositories.*;

import java.time.Instant;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.checkerframework.checker.units.qual.s;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AppointmentServiceImpl implements pt.ua.deti.tqs.cliniconnect.services.AppointmentService {

    private AppointmentRepository appointmentRepository;
    private PatientRepository patientRepository;
    private PersonaRepository personaRepository;
    private HospitalRepository hospitalRepository;
    private DoctorRepository doctorRepository;

    @Override
    public Appointment bookAppointment(CreateAppointmentDTO createAppointmentDTO) {

        Appointment appointment = new Appointment();
        appointment.setDate(createAppointmentDTO.getDate());

        LocalTime lt = LocalTime.parse(createAppointmentDTO.getTime());

        appointment.setTime(lt);
        appointment.setStatus("CREATED");
        appointment.setPrice(createAppointmentDTO.getPrice());
        appointment.setType(createAppointmentDTO.getType());
        appointment.setCurrency("EUR");
        Persona p = personaRepository.findByName(createAppointmentDTO.getPatientName());
        Optional<Patient> patient = patientRepository.findById(p.getId());
        appointment.setPatient(patient.get());

        Optional<Doctor> doctor = doctorRepository.findByName(createAppointmentDTO.getDoctorName());

        appointment.setDoctor(doctor.get());

        Optional<Hospital> hospital = hospitalRepository.findByName(createAppointmentDTO.getHospitalName());
        appointment.setHospital(hospital.get());

        return appointmentRepository.save(appointment);
    }

    @Override
    public boolean cancelAppointment(UUID id) {
        if (appointmentRepository.existsById(id)) {
            appointmentRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public List<Appointment> getAppointmentsByPatient(UUID patientId) {
        return appointmentRepository.findByPatient_Id(patientId);
    }

    @Override
    public boolean updateAppointmentStatus(UUID id, String status) {
        Optional<Appointment> optionalAppointment = appointmentRepository.findById(id);
        if (optionalAppointment.isPresent()) {
            Appointment appointment = optionalAppointment.get();
            appointment.setStatus(status);
            appointmentRepository.save(appointment);
            return true;
        }
        return false;
    }

    @Override
    public List<Appointment> getAppointmentsByDate(Date date) {

        Instant startOfDay = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
                .atStartOfDay(ZoneId.systemDefault()).toInstant();
        Instant endOfDay = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().plusDays(1)
                .atStartOfDay(ZoneId.systemDefault()).toInstant().minusSeconds(1);

        Date startDate = Date.from(startOfDay);
        Date endDate = Date.from(endOfDay);

        List<Appointment> appointments = appointmentRepository.findByDateBetween(startDate, endDate);

        return appointments;
    }

    @Override
    public List<Appointment> getFutureAppointmentsByUserId(String email, Date currentDate) {
        UUID userId = personaRepository.findByEmail(email).get().getId();
        List<Appointment> appointments = appointmentRepository.findByPatient_IdAndDateAfter(userId, currentDate);
        System.out.println("Appointments: " + appointments);
        return appointments;
    }

    @Override
    public List<Appointment> getPastAppointmentsByUserId(String email, Date currentDate) {
        UUID userId = personaRepository.findByEmail(email).get().getId();
        return appointmentRepository.findByPatient_IdAndDateBefore(userId, currentDate);
    }

    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }

}