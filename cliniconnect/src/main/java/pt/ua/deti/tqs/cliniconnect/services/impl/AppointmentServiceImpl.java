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
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
        appointment.setStatus("Created");
        appointment.setPrice(createAppointmentDTO.getPrice());
        appointment.setType(createAppointmentDTO.getType());
        appointment.setCurrency("EUR");
        Persona p = personaRepository.findByName(createAppointmentDTO.getPatientName());
        Optional<Patient> patient = patientRepository.findById(p.getId());
        appointment.setPatient(patient.get());

        Optional<Doctor> doctor = doctorRepository.findByName(createAppointmentDTO.getDoctorName());
        // Doctor doctor = new Doctor(UUID.randomUUID(), "DoctorName", Date.from(Instant.now()), "email@ua.pt", "password", "9494949", "RUA", "Aveiro", "Cardiology", null, null);
        // doctorRepository.save(doctor);

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
        return appointmentRepository.findByDate(date);
    }

    @Override
    public List<Appointment> getFutureAppointmentsByUserId(UUID userId, Date currentDate) {
        return null;
    }

    @Override
    public List<Appointment> getPastAppointmentsByUserId(UUID userId, Date currentDate) {
        // Convert the current date to Instant to match the database type
        Instant now = Instant.ofEpochMilli(currentDate.getTime());

        // Fetch all appointments of the user that have already happened
        return appointmentRepository.findPastAppointmentsByUserIdAndDateBefore(userId, now);
    }

}