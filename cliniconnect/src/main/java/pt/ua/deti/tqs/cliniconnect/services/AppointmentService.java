package pt.ua.deti.tqs.cliniconnect.services;

import pt.ua.deti.tqs.cliniconnect.dto.CreateAppointmentDTO;
import pt.ua.deti.tqs.cliniconnect.models.Appointment;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface AppointmentService {
    Appointment bookAppointment(CreateAppointmentDTO createAppointmentDTO);
    boolean cancelAppointment(UUID id);
    List<Appointment> getAppointmentsByPatient(UUID patientId);
    boolean updateAppointmentStatus(UUID id, String status);
    List<Appointment> getAppointmentsByDate(Date date);
    List<Appointment> getAllAppointments();
    List<Appointment> getFutureAppointmentsByUserId(String email, Date currentDate);
    List<Appointment> getPastAppointmentsByUserId(String email, Date currentDate);
}