package pt.ua.deti.tqs.cliniconnect.controllers;

import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import pt.ua.deti.tqs.cliniconnect.dto.CreateHospitalDTO;
import pt.ua.deti.tqs.cliniconnect.models.Hospital;
import pt.ua.deti.tqs.cliniconnect.services.HospitalService;

@RestController
@RequestMapping("/api/hospitals")
public class HospitalController {

    @Autowired
    private HospitalService hospitalService;

    @PostMapping(path = "/")
    public ResponseEntity<Hospital> createHospital(@RequestBody CreateHospitalDTO createHospitalDTO) {
        Hospital savedHospital = hospitalService.saveHospital(createHospitalDTO);
        
        if (savedHospital == null) {
            return ResponseEntity.notFound().build();
        }
        
        return ResponseEntity.ok(savedHospital);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Hospital> getHospitalById(@PathVariable String id) {
        return hospitalService.getHospitalById(UUID.fromString(id))
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/")
    public ResponseEntity<List<Hospital>> getAllHospitals() {
        List<Hospital> hospitals = hospitalService.getAllHospitals();

        if (hospitals == null || hospitals.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(hospitals);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHospital(@PathVariable UUID id) {
        hospitalService.deleteHospital(id);
        return ResponseEntity.ok().build();
    }
}