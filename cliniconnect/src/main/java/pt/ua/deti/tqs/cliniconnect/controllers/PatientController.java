package pt.ua.deti.tqs.cliniconnect.controllers;

import java.util.HashMap;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import pt.ua.deti.tqs.cliniconnect.dto.AuthResponse;
import pt.ua.deti.tqs.cliniconnect.dto.LoginDTO;
import pt.ua.deti.tqs.cliniconnect.dto.PatientDetailsDTO;
import pt.ua.deti.tqs.cliniconnect.dto.RegisterPatientDTO;
import pt.ua.deti.tqs.cliniconnect.models.Patient;
import pt.ua.deti.tqs.cliniconnect.models.Specialties;
import pt.ua.deti.tqs.cliniconnect.repositories.PatientRepository;
import pt.ua.deti.tqs.cliniconnect.services.PatientService;
import pt.ua.deti.tqs.cliniconnect.services.AuthService;
import pt.ua.deti.tqs.cliniconnect.services.SpecialtiesService;

@RestController
@AllArgsConstructor
@RequestMapping("/api/patients")
public class PatientController {

    private PatientService patientService;
    private AuthService authService;
    private SpecialtiesService specialtiesService;

    private PatientRepository patientRepository;

    @Operation(summary = "Register a new patient")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully canceled the appointment")
    })
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterPatientDTO registerPatientDTO) {
        return ResponseEntity.ok(authService.registerPatient(registerPatientDTO));
    }

    @Operation(summary = "Login a patient")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully loged in a patient")
    })
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginDTO loginDTO) {
        return ResponseEntity.ok(authService.loginPatient(loginDTO));
    }

    @Operation(summary = "Get patient by email")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrive a patient"),
            @ApiResponse(responseCode = "404", description = "Patient could not be retrived")
    })
    @GetMapping("/{email}")
    public ResponseEntity<PatientDetailsDTO> getPatientByEmail(@PathVariable String email) {

        Patient patient = patientService.getPatientByEmail(email);

        if (patient != null) {

            HashMap<String, Integer> specialties = specialtiesService.getByPatient(patient);
            PatientDetailsDTO patientDetails = new PatientDetailsDTO(patient, specialties);
            return ResponseEntity.ok(patientDetails);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Get all patients")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrive all patients"),
            @ApiResponse(responseCode = "404", description = "Patients could not be retrived")
    })
    @GetMapping("/")
    public ResponseEntity<List<Patient>> getAllPatients() {

        List<Patient> patients = patientRepository.findAll();

        if (patients.size() != 0) {
            return ResponseEntity.ok(patients);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Get a patient by specialty")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrive a patient"),
            @ApiResponse(responseCode = "404", description = "Patient could not be retrived")
    })
    @GetMapping("/{email}/specialties")
    public ResponseEntity<HashMap<String, Integer>> getSpecialties(@PathVariable String email) {
        Patient patient = patientService.getPatientByEmail(email);
        if (patient != null) {
            return ResponseEntity.ok(specialtiesService.getByPatient(patient));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Add a specialty to a patient")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully added a specialty"),
            @ApiResponse(responseCode = "404", description = "Patient could not be retrived")
    })
    @PostMapping("/{email}/specialties")
    public ResponseEntity<Specialties> addSpecialty(@PathVariable String email,
            @RequestBody HashMap<String, Integer> specialties) {
        Patient patient = patientService.getPatientByEmail(email);
        if (patient != null) {
            return ResponseEntity.ok(specialtiesService.addSpecialty(patient, specialties));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Delete a specialty from a patient")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully deleted a specialty"),
            @ApiResponse(responseCode = "404", description = "Patient could not be retrived")
    })
    @DeleteMapping("/{email}/specialties/{specialty}")
    public ResponseEntity<Specialties> deleteSpecialty(@PathVariable String email, @PathVariable String specialty) {
        Patient patient = patientService.getPatientByEmail(email);
        if (patient != null) {
            HashMap<String, Integer> specialties = specialtiesService.getByPatient(patient);
            if (specialties == null || !specialties.containsKey(specialty)) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(specialtiesService.deleteSpecialty(patient, specialty));
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}