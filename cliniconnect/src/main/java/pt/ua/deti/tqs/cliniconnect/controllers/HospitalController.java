package pt.ua.deti.tqs.cliniconnect.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import pt.ua.deti.tqs.cliniconnect.dto.CreateHospitalDTO;
import pt.ua.deti.tqs.cliniconnect.models.Hospital;
import pt.ua.deti.tqs.cliniconnect.services.HospitalService;

@RestController
@AllArgsConstructor
@RequestMapping("/api/hospitals")
public class HospitalController {

    private HospitalService hospitalService;

    @Operation(summary = "Creating a new hospital")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully created the hospital"),
        @ApiResponse(responseCode = "404", description = "Hospital could not be created")
    })
    @PostMapping(path = "/")
    public ResponseEntity<Hospital> createHospital(@RequestBody CreateHospitalDTO createHospitalDTO) {
        Hospital savedHospital = hospitalService.saveHospital(createHospitalDTO);
        
        if (savedHospital == null) {
            return ResponseEntity.notFound().build();
        }
        
        return ResponseEntity.ok(savedHospital);
    }

    @Operation(summary = "Get hospital by id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrive a hospital"),
        @ApiResponse(responseCode = "404", description = "Hospital could not be retrived")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Hospital> getHospitalById(@PathVariable String id) {
        return hospitalService.getHospitalById(UUID.fromString(id))
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Get all hospitals")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrive all hospitals"),
        @ApiResponse(responseCode = "404", description = "Hospital could not be retrived")
    })
    @GetMapping("/")
    public ResponseEntity<List<Hospital>> getAllHospitals() {
        List<Hospital> hospitals = hospitalService.getAllHospitals();

        if (hospitals == null || hospitals.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(hospitals);
    }

    @Operation(summary = "Delete a hospital by id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully deleted a hospital")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHospital(@PathVariable UUID id) {
        hospitalService.deleteHospital(id);
        return ResponseEntity.ok().build();
    }
}