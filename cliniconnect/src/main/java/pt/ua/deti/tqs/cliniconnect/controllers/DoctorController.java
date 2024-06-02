package pt.ua.deti.tqs.cliniconnect.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import pt.ua.deti.tqs.cliniconnect.dto.AddDoctorDTO;
import pt.ua.deti.tqs.cliniconnect.models.Doctor;
import pt.ua.deti.tqs.cliniconnect.services.DoctorService;

@RestController
@AllArgsConstructor
@RequestMapping("/api/doctors")
public class DoctorController {
    
    private DoctorService doctorService;

    @PostMapping(path = "/")
    public ResponseEntity<Doctor> addDoctor(@RequestBody AddDoctorDTO addDoctorDTO) {
        Doctor doctor = doctorService.addDoctor(addDoctorDTO);

        if (doctor != null) {
            return ResponseEntity.ok(doctor);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/")
    public ResponseEntity<List<Doctor>> getAllDoctors() {
        List<Doctor> doctors = doctorService.getAllDoctors();
    
        if (doctors == null || doctors.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
    
        return ResponseEntity.ok(doctors);
    }
    

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
