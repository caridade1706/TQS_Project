package pt.ua.deti.tqs.cliniconnect.controllers;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;
import pt.ua.deti.tqs.cliniconnect.dto.CreateAppointmentDTO;
import pt.ua.deti.tqs.cliniconnect.models.Appointment;
import pt.ua.deti.tqs.cliniconnect.services.AppointmentService;

@RestController
@AllArgsConstructor
@RequestMapping("/api/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;

    @Operation(summary = "Create a new appointment")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully created the appointment"),
        @ApiResponse(responseCode = "404", description = "Appointment could not be created")
    })
    @PostMapping
    public ResponseEntity<Appointment> createAppointment(@RequestBody CreateAppointmentDTO createAppointmentDTO) {
        Appointment createdAppointment = appointmentService.bookAppointment(createAppointmentDTO);

        if (createdAppointment == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(createdAppointment);
    }

    @Operation(summary = "Cancel an appointment")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully canceled the appointment"),
        @ApiResponse(responseCode = "404", description = "Appointment not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelAppointment(@PathVariable UUID id) {
        boolean result = appointmentService.cancelAppointment(id);
        if (result) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Change appointment status")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully changed the appointment status"),
        @ApiResponse(responseCode = "404", description = "Appointment not found")
    })
    @PostMapping("/{id}/{status}")
    public ResponseEntity<Void> changeStatus(@PathVariable UUID id, @PathVariable String status) {
        boolean result = appointmentService.updateAppointmentStatus(id, status);
        if (result) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Get appointments by patient ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved the appointments"),
        @ApiResponse(responseCode = "404", description = "No appointments found for the patient")
    })
    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<Appointment>> getAppointmentsByPatient(@PathVariable UUID patientId) {
        List<Appointment> appointments = appointmentService.getAppointmentsByPatient(patientId);
        if (appointments != null && !appointments.isEmpty()) {
            return ResponseEntity.ok(appointments);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Get today's appointments")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved today's appointments"),
        @ApiResponse(responseCode = "204", description = "No appointments found for today")
    })
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

    @Operation(summary = "Get all appointments")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved all appointments"),
        @ApiResponse(responseCode = "204", description = "No appointments found")
    })
    @GetMapping("/all")
    public ResponseEntity<List<Appointment>> getAllAppointments() {
        List<Appointment> allAppointments = appointmentService.getAllAppointments();
        if (allAppointments.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(allAppointments);
    }

    @Operation(summary = "Get future appointments by email")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved future appointments"),
        @ApiResponse(responseCode = "204", description = "No future appointments found")
    })
    @GetMapping("/future/{email}")
    public ResponseEntity<List<Appointment>> getFutureAppointments(@PathVariable String email) {
        
        Date date = Date.from(Instant.now());
        
        List<Appointment> appointments = appointmentService.getFutureAppointmentsByUserId(email, date);
    
        if (appointments.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(appointments);
    }

    @Operation(summary = "Get past appointments by email")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved past appointments"),
        @ApiResponse(responseCode = "204", description = "No past appointments found")
    })
    @GetMapping("/history/{email}")
    public ResponseEntity<List<Appointment>> getPastAppointments(@PathVariable String email) {
        
        Date date = Date.from(Instant.now());
        List<Appointment> appointments = appointmentService.getPastAppointmentsByUserId(email, date);
        if (appointments.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(appointments);
    }
}
