package pt.ua.deti.tqs.cliniconnect.controllers;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.AllArgsConstructor;
import pt.ua.deti.tqs.cliniconnect.models.Appointment;
import pt.ua.deti.tqs.cliniconnect.services.AppointmentService;

@RestController
@AllArgsConstructor
@RequestMapping("/api/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;

    @PostMapping
    public ResponseEntity<Appointment> createAppointment(@RequestBody Appointment appointment) {
        Appointment createdAppointment = appointmentService.bookAppointment(appointment);
        
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

    @PostMapping("/{id}/{status}")
    public ResponseEntity<Void> changeStatus(@PathVariable UUID id, @PathVariable String status) {
        boolean result = appointmentService.updateAppointmentStatus(id, status);
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
        LocalDate todayDate = LocalDate.now();
        Date today = Date.from(todayDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

        List<Appointment> todayAppointments = appointmentService.getAppointmentsByDate(today);

        if (todayAppointments.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(todayAppointments);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Appointment>> getAllAppointments() {
        List<Appointment> allAppointments = appointmentService.getAllAppointments();
        if (allAppointments.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(allAppointments);
    }
}
