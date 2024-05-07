package pt.ua.deti.tqs.cliniconnect.services;

import pt.ua.deti.tqs.cliniconnect.models.Appointment;

import java.util.List;
import java.util.UUID;

public interface AppointmentService {
    Appointment bookAppointment(Appointment appointment);
    boolean cancelAppointment(UUID id);
    List<Appointment> getAppointmentsByPatient(UUID patientId);
}