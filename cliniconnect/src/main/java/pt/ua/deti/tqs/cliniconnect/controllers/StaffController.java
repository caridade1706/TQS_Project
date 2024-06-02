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

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterStaffDTO registerStaffDTO) {
        return ResponseEntity.ok(authService.registerStaff(registerStaffDTO));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginDTO loginDTO) {
        return ResponseEntity.ok(authService.loginStaff(loginDTO));
    }

    @GetMapping("/{email}")
    public ResponseEntity<Staff> getPatientByEmail(@PathVariable String email) {

        Staff staff = staffService.getPatientByEmail(email);
        
        if (staff != null) {
            return ResponseEntity.ok(staff);
        } else {
            return ResponseEntity.notFound().build();
        }
    } 

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