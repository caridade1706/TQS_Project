package pt.ua.deti.tqs.cliniconnect.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import pt.ua.deti.tqs.cliniconnect.models.Persona;
import pt.ua.deti.tqs.cliniconnect.repositories.PersonaRepository;

@ExtendWith(MockitoExtension.class)
class ApplicationConfigTest {
    @Mock
    private PersonaRepository personaRepository;

    @InjectMocks
    private ApplicationConfig applicationConfig;

    @Test
    @DisplayName("Test UserDetailsService with existing user")
    void testUserDetailServiceWithExistingUser() {
        String email = "existing@user.com";

        Persona persona = new Persona();
        persona.setEmail(email);
        // set other fields of persona as necessary

        when(personaRepository.findByEmail(anyString())).thenReturn(Optional.of(persona));

        UserDetailsService userDetailsService = applicationConfig.userDetailService();
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);

        assertEquals(email, userDetails.getUsername());
        // add more assertions as necessary
    }

    @Test
    @DisplayName("Test UserDetailsService with non-existing user")
    void testUserDetailServiceWithNonExistingUser() {
        String email = "nonexisting@user.com";

        when(personaRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        UserDetailsService userDetailsService = applicationConfig.userDetailService();

        assertThrows(UsernameNotFoundException.class, () -> {
            userDetailsService.loadUserByUsername(email);
        });
    }
}
