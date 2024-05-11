package pt.ua.deti.tqs.cliniconnect.services;

import pt.ua.deti.tqs.cliniconnect.dto.AuthResponse;
import pt.ua.deti.tqs.cliniconnect.dto.LoginDTO;
import pt.ua.deti.tqs.cliniconnect.dto.RegisterPatientDTO;
import pt.ua.deti.tqs.cliniconnect.dto.RegisterStaffDTO;

public interface AuthService {
    AuthResponse registerPatient(RegisterPatientDTO registerPatientDTO);
    AuthResponse registerStaff(RegisterStaffDTO registerStaffDTO);
    AuthResponse loginPatient(LoginDTO loginDTO);
    AuthResponse loginStaff(LoginDTO loginDTO);
}