package pt.ua.deti.tqs.cliniconnect.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.AllArgsConstructor;
import pt.ua.deti.tqs.cliniconnect.models.Doctor;
import pt.ua.deti.tqs.cliniconnect.services.DoctorService;

@RestController
@AllArgsConstructor
@RequestMapping("/api/doctors")
public class DoctorController {
    
    @Autowired
    private DoctorService doctorService;

    @GetMapping("/speciality/{speciality}")
    public ResponseEntity<List<Doctor>> getDoctorsBySpeciality(@PathVariable String speciality) {
        
        List<Doctor> doctors = doctorService.getDoctorsBySpeciality(speciality);
        
        if (!doctors.isEmpty()) {
            return ResponseEntity.ok(doctors);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}