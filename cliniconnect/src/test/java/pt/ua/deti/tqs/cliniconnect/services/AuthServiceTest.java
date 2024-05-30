package pt.ua.deti.tqs.cliniconnect.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.Instant;
import java.util.Date;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import pt.ua.deti.tqs.cliniconnect.Roles;
import pt.ua.deti.tqs.cliniconnect.dto.AuthResponse;
import pt.ua.deti.tqs.cliniconnect.dto.LoginDTO;
import pt.ua.deti.tqs.cliniconnect.dto.RegisterPatientDTO;
import pt.ua.deti.tqs.cliniconnect.dto.RegisterStaffDTO;
import pt.ua.deti.tqs.cliniconnect.jwt.JwtService;
import pt.ua.deti.tqs.cliniconnect.models.Patient;
import pt.ua.deti.tqs.cliniconnect.models.Persona;
import pt.ua.deti.tqs.cliniconnect.models.Staff;
import pt.ua.deti.tqs.cliniconnect.repositories.PatientRepository;
import pt.ua.deti.tqs.cliniconnect.repositories.PersonaRepository;
import pt.ua.deti.tqs.cliniconnect.repositories.StaffRepository;
import pt.ua.deti.tqs.cliniconnect.services.impl.AuthServiceImpl;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @Mock(lenient = true)
    private PersonaRepository personaRepository;

    @Mock(lenient = true)
    private StaffRepository staffRepository;

    @Mock(lenient = true)
    private PatientRepository patientRepository;

    @Mock(lenient = true)
    private JwtService jwtService;

    @Mock(lenient = true)
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthServiceImpl authService;

    @Test
    public void testRegisterPatient() {
        RegisterPatientDTO dto = new RegisterPatientDTO(
                "John Doe",
                Date.from(Instant.parse("1990-07-10T00:00:00Z")),
                "johndoe@example.com",
                "password123",
                "1234567890",
                "123 Street",
                "Lisboa",
                "3456789456",
                "Lisboa CliniConnect Hospital");

        Patient savedPatient = new Patient();
        savedPatient.setEmail("johndoe@example.com");

        String expectedToken = "jwt-token";
        when(patientRepository.save(any(Patient.class))).thenReturn(savedPatient);
        when(jwtService.getToken(any(Patient.class))).thenReturn(expectedToken);

        // Act
        AuthResponse response = authService.registerPatient(dto);

        // Assert
        assertNotNull(response);
        assertEquals(expectedToken, response.getToken());
        verify(patientRepository).save(any(Patient.class));
        verify(jwtService).getToken(any(Patient.class));

    }

    @Test
    public void testRegisterStaff() {

        RegisterStaffDTO dto = new RegisterStaffDTO(
                "John Doe",
                Date.from(Instant.parse("1990-07-10T00:00:00Z")),
                "johndoe@example.com",
                "password123",
                "1234567890",
                "123 Street",
                "Lisboa",
                "Reception",
                "Receptionist");

        Staff savedStaff = new Staff();
        savedStaff.setEmail("johndoe@example.com");

        String expectedToken = "jwt-token";
        when(staffRepository.save(any(Staff.class))).thenReturn(savedStaff);
        when(jwtService.getToken(any(Staff.class))).thenReturn(expectedToken);

        // Act
        AuthResponse response = authService.registerStaff(dto);

        // Assert
        assertNotNull(response);
        assertEquals(expectedToken, response.getToken());
        verify(staffRepository).save(any(Staff.class));
        verify(jwtService).getToken(any(Staff.class));
    }

    @Test
    public void testLoginPatient_ValidCredentials_ReturnsToken() {
        // Arrange
        String email = "patient@example.com";
        String password = "password123";
        LoginDTO loginDTO = new LoginDTO(email, password);
        Patient patient = new Patient();
        patient.setRole(Roles.PATIENT);
        patient.setEmail(email);
        patient.setPassword(new BCryptPasswordEncoder().encode(password));
        Optional<Persona> optionalPatient = Optional.of(patient);

        Authentication auth = mock(Authentication.class);
        when(authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password)))
                .thenReturn(auth);
        when(personaRepository.findByEmail(email)).thenReturn(optionalPatient);
        when(jwtService.getToken(any(UserDetails.class))).thenReturn("token");

        // Act
        AuthResponse response = authService.loginPatient(loginDTO);

        // Assert
        assertNotNull(response);
        assertEquals("token", response.getToken());
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(personaRepository, times(2)).findByEmail(email);
        verify(jwtService).getToken(any(UserDetails.class));
    }

    @Test
    public void testLoginPatient_InvalidCredentials_ThrowsException() {
        // Arrange
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setEmail("test@example.com");
        loginDTO.setPassword("wrongpassword");

        when(authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword())))
                .thenThrow(new AuthenticationException("Invalid credentials") {});

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            authService.loginPatient(loginDTO);
        });

        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(personaRepository, times(0)).findByEmail(anyString());
    }

    @Test
    public void testLoginPatient_InvalidPermission_ThrowsException() {
        // Arrange
        String email = "patient@example.com";
        String password = "password123";
        LoginDTO loginDTO = new LoginDTO(email, password);
        Patient patient = new Patient();
        patient.setRole(Roles.STAFF);
        patient.setEmail(email);
        patient.setPassword(new BCryptPasswordEncoder().encode(password));
        Optional<Persona> optionalPatient = Optional.of(patient);

        when(authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword())))
                .thenReturn(null); // Simulate successful authentication

        when(personaRepository.findByEmail(loginDTO.getEmail())).thenReturn(optionalPatient);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            authService.loginPatient(loginDTO);
        }, "Invalid Permissions");

        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(personaRepository, times(2)).findByEmail(email);
    }

    @Test
    public void testLoginStaff_ValidCredentials_ReturnsToken() {
        // Arrange
        String email = "patient@example.com";
        String password = "password123";
        LoginDTO loginDTO = new LoginDTO(email, password);
        Staff staff = new Staff();
        staff.setRole(Roles.STAFF);
        staff.setEmail(email);
        staff.setPassword(new BCryptPasswordEncoder().encode(password));
        Optional<Persona> optionalPatient = Optional.of(staff);

        Authentication auth = mock(Authentication.class);
        when(authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password)))
                .thenReturn(auth);
        when(personaRepository.findByEmail(email)).thenReturn(optionalPatient);
        when(jwtService.getToken(any(UserDetails.class))).thenReturn("token");

        // Act
        AuthResponse response = authService.loginStaff(loginDTO);

        // Assert
        assertNotNull(response);
        assertEquals("token", response.getToken());
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(personaRepository, times(2)).findByEmail(email);
        verify(jwtService).getToken(any(UserDetails.class));
    }

    @Test
    public void testLoginStaff_InvalidCredentials_ThrowsException() {
        // Arrange
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setEmail("test@example.com");
        loginDTO.setPassword("wrongpassword");

        when(authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword())))
                .thenThrow(new AuthenticationException("Invalid credentials") {});

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            authService.loginStaff(loginDTO);
        });

        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(personaRepository, times(0)).findByEmail(anyString());
    }

    @Test
    public void testLoginStaff_InvalidPermission_ThrowsException() {
        // Arrange
        String email = "patient@example.com";
        String password = "password123";
        LoginDTO loginDTO = new LoginDTO(email, password);
        Staff staff = new Staff();
        staff.setRole(Roles.PATIENT);
        staff.setEmail(email);
        staff.setPassword(new BCryptPasswordEncoder().encode(password));
        Optional<Persona> optionalPatient = Optional.of(staff);

        when(authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword())))
                .thenReturn(null); // Simulate successful authentication

        when(personaRepository.findByEmail(loginDTO.getEmail())).thenReturn(optionalPatient);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            authService.loginStaff(loginDTO);
        }, "Invalid Permissions");

        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(personaRepository, times(2)).findByEmail(email);
    }
}
