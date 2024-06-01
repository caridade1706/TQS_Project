package pt.ua.deti.tqs.cliniconnect.controllers;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.AllArgsConstructor;
import pt.ua.deti.tqs.cliniconnect.dto.CreateAppointmentDTO;
import pt.ua.deti.tqs.cliniconnect.models.Appointment;
import pt.ua.deti.tqs.cliniconnect.services.AppointmentService;

@RestController
@AllArgsConstructor
@RequestMapping("/api/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;

    @PostMapping
    public ResponseEntity<Appointment> createAppointment(@RequestBody CreateAppointmentDTO createAppointmentDTO) {
        Appointment createdAppointment = appointmentService.bookAppointment(createAppointmentDTO);

        if (createdAppointment == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(createdAppointment);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelAppointment(@PathVariable UUID id) {
        boolean result = appointmentService.cancelAppointment(id);
        if (result) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<Appointment>> getAppointmentsByPatient(@PathVariable UUID patientId) {
        List<Appointment> appointments = appointmentService.getAppointmentsByPatient(patientId);
        if (appointments != null && !appointments.isEmpty()) {
            return ResponseEntity.ok(appointments);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/today")
    public ResponseEntity<List<Appointment>> getAppointmentsToday() {
        Date today = new Date();
        List<Appointment> todayAppointments = appointmentService.getAppointmentsByDate(today);
        if (todayAppointments.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(todayAppointments);
    }

    // Endpoint para consultas futuras
    @GetMapping("/future/{userId}")
    public ResponseEntity<List<Appointment>> getFutureAppointments(@PathVariable UUID userId) {
        List<Appointment> appointments = appointmentService.getFutureAppointmentsByUserId(userId, new Date());
        if (appointments.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(appointments);
    }

    // Endpoint para histÃ³rico de consultas
    @GetMapping("/history/{userId}")
    public ResponseEntity<List<Appointment>> getPastAppointments(@PathVariable UUID userId) {
        List<Appointment> appointments = appointmentService.getPastAppointmentsByUserId(userId, new Date());
        if (appointments.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(appointments);
    }
}
