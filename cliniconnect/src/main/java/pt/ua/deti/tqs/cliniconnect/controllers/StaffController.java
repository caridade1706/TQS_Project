package pt.ua.deti.tqs.cliniconnect.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;
import pt.ua.deti.tqs.cliniconnect.dto.AuthResponse;
import pt.ua.deti.tqs.cliniconnect.dto.HospitalUpdateDTO;
import pt.ua.deti.tqs.cliniconnect.dto.LoginDTO;
import pt.ua.deti.tqs.cliniconnect.dto.RegisterStaffDTO;
import pt.ua.deti.tqs.cliniconnect.models.Staff;
import pt.ua.deti.tqs.cliniconnect.services.AuthService;
import pt.ua.deti.tqs.cliniconnect.services.StaffService;

@RestController
@AllArgsConstructor
@RequestMapping("/api/staff")
public class StaffController {

    private StaffService staffService;
    private AuthService authService;

    @Operation(summary = "Register a new staff")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully register a new staff"),
    })
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterStaffDTO registerStaffDTO) {
        return ResponseEntity.ok(authService.registerStaff(registerStaffDTO));
    }

    @Operation(summary = "Login a staff")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully loged in a staff")
    })
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginDTO loginDTO) {
        return ResponseEntity.ok(authService.loginStaff(loginDTO));
    }

    @Operation(summary = "Get staff by email")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrive a staff"),
            @ApiResponse(responseCode = "404", description = "Staff could not be retrived")
    })
    @GetMapping("/{email}")
    public ResponseEntity<Staff> getPatientByEmail(@PathVariable String email) {

        Staff staff = staffService.getPatientByEmail(email);
        
        if (staff != null) {
            return ResponseEntity.ok(staff);
        } else {
            return ResponseEntity.notFound().build();
        }
    } 

    @Operation(summary = "Add hospitals to staff")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully added hospitals to staff"),
            @ApiResponse(responseCode = "400", description = "Hospitals could not be added to staff")
    })
    @PostMapping("/addHospitals")
    public ResponseEntity<?> addHospital(@RequestBody HospitalUpdateDTO hospitalUpdateDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        boolean success = staffService.updateStaffHospitals(email, hospitalUpdateDTO.getHospitals());
        if (success) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
}