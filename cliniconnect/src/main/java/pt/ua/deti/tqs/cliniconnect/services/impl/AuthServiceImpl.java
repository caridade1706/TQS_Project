package pt.ua.deti.tqs.cliniconnect.services.impl;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import pt.ua.deti.tqs.cliniconnect.Roles;
import pt.ua.deti.tqs.cliniconnect.Jwt.JwtService;
import pt.ua.deti.tqs.cliniconnect.dto.AuthResponse;
import pt.ua.deti.tqs.cliniconnect.dto.LoginDTO;
import pt.ua.deti.tqs.cliniconnect.dto.RegisterPatientDTO;
import pt.ua.deti.tqs.cliniconnect.dto.RegisterStaffDTO;
import pt.ua.deti.tqs.cliniconnect.models.Persona;
import pt.ua.deti.tqs.cliniconnect.models.Appointment;
import pt.ua.deti.tqs.cliniconnect.models.Hospital;
import pt.ua.deti.tqs.cliniconnect.models.Patient;
import pt.ua.deti.tqs.cliniconnect.models.Staff;
import pt.ua.deti.tqs.cliniconnect.repositories.PatientRepository;
import pt.ua.deti.tqs.cliniconnect.repositories.PersonaRepository;
import pt.ua.deti.tqs.cliniconnect.repositories.StaffRepository;
import pt.ua.deti.tqs.cliniconnect.services.AuthService;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final PersonaRepository personaRepository;
    private final StaffRepository staffRepository;
    private final PatientRepository patientRepository;

    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthResponse registerPatient(RegisterPatientDTO registerPatientDTO) {

        Set<Appointment> appointments = new HashSet<>();

        Patient patient = new Patient();
        patient.setName(registerPatientDTO.getName());
        patient.setDob(registerPatientDTO.getDob());
        patient.setEmail(registerPatientDTO.getEmail());
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPassword = encoder.encode(registerPatientDTO.getPassword());

        patient.setPassword(encodedPassword);
        patient.setPhone(registerPatientDTO.getPhone());
        patient.setAddress(registerPatientDTO.getAddress());
        patient.setCity(registerPatientDTO.getCity());
        patient.setRole(Roles.PATIENT);
        patient.setPatientNumber(registerPatientDTO.getPatientNumber());
        patient.setPreferredHospital(registerPatientDTO.getPreferredHospital());
        patient.setAppointments(appointments);
        patientRepository.save(patient);

        return AuthResponse.builder()
            .token(jwtService.getToken(patient))
            .build();
        
    }

    @Override
    public AuthResponse registerStaff(RegisterStaffDTO registerStaffDTO) {

        Staff staff = new Staff();

        Set<Hospital> hospitals = new HashSet<>();

        staff.setName(registerStaffDTO.getName());
        staff.setDob(registerStaffDTO.getDob());
        staff.setEmail(registerStaffDTO.getEmail());
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPassword = encoder.encode(registerStaffDTO.getPassword());

        staff.setPassword(encodedPassword);
        staff.setPhone(registerStaffDTO.getPhone());
        staff.setAddress(registerStaffDTO.getAddress());
        staff.setCity(registerStaffDTO.getCity());
        staff.setRole(Roles.STAFF);
        staff.setDepartment(registerStaffDTO.getDepartment());
        staff.setTask(registerStaffDTO.getTask());
        staff.setHospitals(hospitals);

        staffRepository.save(staff);

        return AuthResponse.builder()
            .token(jwtService.getToken(staff))
            .build();
    }

    @Override
    public AuthResponse loginPatient(LoginDTO loginDTO) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword()));
        UserDetails user = personaRepository.findByEmail(loginDTO.getEmail()).orElseThrow();
        
        if (user == null) {
            System.out.println("Invalid credentials");
            throw new RuntimeException("Invalid credentials");
        }
        Optional<Persona> p = personaRepository.findByEmail(loginDTO.getEmail());
        
        
        if (p.get().getRole() != Roles.PATIENT) {
            System.out.println("Invalid Permissions");
            throw new RuntimeException("Invalid Permissions");
        }
        
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        if (encoder.matches(loginDTO.getPassword(), user.getPassword())) {
            String token = jwtService.getToken(user);
            
            System.out.println(AuthResponse.builder()
                .token(token)
                .build());
            
            return AuthResponse.builder()
                .token(token)
                .build();
        } else {
            System.out.println("Invalid credentials");
            throw new RuntimeException("Invalid credentials");
        }
    }

    @Override
    public AuthResponse loginStaff(LoginDTO loginDTO) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword()));
        UserDetails user = personaRepository.findByEmail(loginDTO.getEmail()).orElseThrow();
        
        if (user == null) {
            System.out.println("Invalid credentials");
            throw new RuntimeException("Invalid credentials");
        }
        Optional<Persona> p = personaRepository.findByEmail(loginDTO.getEmail());
        
        
        if (p.get().getRole() != Roles.STAFF) {
            System.out.println("Invalid Permissions");
            throw new RuntimeException("Invalid Permissions");
        }
        
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        if (encoder.matches(loginDTO.getPassword(), user.getPassword())) {
            String token = jwtService.getToken(user);
            
            System.out.println(AuthResponse.builder()
                .token(token)
                .build());
            
            return AuthResponse.builder()
                .token(token)
                .build();
        } else {
            System.out.println("Invalid credentials");
            throw new RuntimeException("Invalid credentials");
        }
    }


}