package pt.ua.deti.tqs.cliniconnect.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import pt.ua.deti.tqs.cliniconnect.dto.AddDoctorDTO;
import pt.ua.deti.tqs.cliniconnect.models.Doctor;
import pt.ua.deti.tqs.cliniconnect.services.DoctorService;

@RestController
@AllArgsConstructor
@RequestMapping("/api/doctors")
public class DoctorController {
    
    private DoctorService doctorService;

    @Operation(summary = "Add a new doctor")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully added a new doctor"),
        @ApiResponse(responseCode = "404", description = "Doctor could not be added")
    })
    @PostMapping("/")
    public ResponseEntity<Doctor> addDoctor(@RequestBody AddDoctorDTO addDoctorDTO) {
        Doctor doctor = doctorService.addDoctor(addDoctorDTO);

        if (doctor != null) {
            return ResponseEntity.ok(doctor);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @Operation(summary = "Get all doctors")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrive the doctors"),
        @ApiResponse(responseCode = "404", description = "No doctors found")
    })
    @GetMapping("/")
    public ResponseEntity<List<Doctor>> getAllDoctors() {
        List<Doctor> doctors = doctorService.getAllDoctors();
    
        if (doctors == null || doctors.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
    
        return ResponseEntity.ok(doctors);
    }
    

    @Operation(summary = "Get doctors by speciality")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrive the doctors by speciality"),
        @ApiResponse(responseCode = "404", description = "No doctors found with the given speciality")
    })
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