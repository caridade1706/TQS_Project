package pt.ua.deti.tqs.cliniconnect.controllers;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.RequestBody;
import lombok.AllArgsConstructor;
import pt.ua.deti.tqs.cliniconnect.dto.CreateAppointmentDTO;
import pt.ua.deti.tqs.cliniconnect.models.Appointment;
import pt.ua.deti.tqs.cliniconnect.services.AppointmentService;

@RestController
@AllArgsConstructor
@RequestMapping("/api/appointments")
public class AppointmentController {

    @Autowired
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
    public ResponseEntity<?> getAppointmentsByPatient(@PathVariable UUID patientId) {
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
}
