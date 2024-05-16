package pt.ua.deti.tqs.cliniconnect.controllers;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import pt.ua.deti.tqs.cliniconnect.dto.AuthResponse;
import pt.ua.deti.tqs.cliniconnect.dto.LoginDTO;
import pt.ua.deti.tqs.cliniconnect.dto.PatientDetailsDTO;
import pt.ua.deti.tqs.cliniconnect.dto.RegisterPatientDTO;
import pt.ua.deti.tqs.cliniconnect.models.Patient;
import pt.ua.deti.tqs.cliniconnect.models.Specialties;
import pt.ua.deti.tqs.cliniconnect.services.PatientService;
import pt.ua.deti.tqs.cliniconnect.services.AuthService;
import pt.ua.deti.tqs.cliniconnect.services.SpecialtiesService;

@RestController
@AllArgsConstructor
@RequestMapping("/api/patients")
public class PatientController {

    @Autowired
    private PatientService patientService;
    private AuthService authService;
    private SpecialtiesService specialtiesService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterPatientDTO registerPatientDTO) {
        return ResponseEntity.ok(authService.registerPatient(registerPatientDTO));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginDTO loginDTO) {
        return ResponseEntity.ok(authService.loginPatient(loginDTO));
    }

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

    @GetMapping("/{email}/specialties")
    public ResponseEntity<HashMap<String, Integer>> getSpecialties(@PathVariable String email) {
        Patient patient = patientService.getPatientByEmail(email);
        if (patient != null) {
            return ResponseEntity.ok(specialtiesService.getByPatient(patient));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

        @PostMapping("/{email}/specialties")
        public ResponseEntity<Specialties> addSpecialty(@PathVariable String email, @RequestBody HashMap<String, Integer> specialties) {
            Patient patient = patientService.getPatientByEmail(email);
            if (patient != null) {
                return ResponseEntity.ok(specialtiesService.addSpecialty(patient, specialties));
            } else {
                return ResponseEntity.notFound().build();
            }
        }

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