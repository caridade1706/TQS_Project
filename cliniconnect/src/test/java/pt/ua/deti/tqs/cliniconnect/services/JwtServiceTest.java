package pt.ua.deti.tqs.cliniconnect.services;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

import pt.ua.deti.tqs.cliniconnect.Jwt.JwtService;

@ExtendWith(MockitoExtension.class)
public class JwtServiceTest {

    private String SECRET_KEY = "586E3272357538782F413F4428472B4B6250655368566B597033733676397924";

    @Mock()
    private UserDetails userDetails;

    @Mock()
    private UserDetails userDetails2;

    @Spy
    @InjectMocks
    private JwtService jwtService;

    @BeforeEach
    void setUp() {
        jwtService.setSecretKey(SECRET_KEY);
    }

    @Test
    void testGetToken() {
        String expectedUsername = "user@example.com";

        when(userDetails.getUsername()).thenReturn(expectedUsername);

        String token = jwtService.getToken(userDetails);

        assertNotNull(token);
        assertEquals(expectedUsername, jwtService.getUsernameFromToken(token));

        verify(userDetails, times(1)).getUsername();
    }

    @Test
    void testTokenValidityWithCorrectUsernameAndNotExpired() {
        // Setup
        String username = "testuser";
        when(userDetails.getUsername()).thenReturn(username);
        String token = jwtService.getToken(userDetails);
        when(jwtService.getUsernameFromToken(token)).thenReturn(username);
        when(jwtService.isTokenExpired(token)).thenReturn(false);

        // Act
        boolean isValid = jwtService.isTokenValid(token, userDetails);

        // Assert
        assertTrue(isValid, "Token should be valid when username matches and token is not expired.");
    }

    @Test
    void testTokenValidityWithCorrectUsernameButExpired() {
        // Setup
        String username = "testuser";
        when(userDetails.getUsername()).thenReturn(username);
        String token = jwtService.getToken(userDetails);
        when(jwtService.getUsernameFromToken(token)).thenReturn(username);
        when(jwtService.isTokenExpired(token)).thenReturn(true);

        // Act
        boolean isValid = jwtService.isTokenValid(token, userDetails);

        // Assert
        assertFalse(isValid, "Token should be invalid when it is expired.");
    }

    @Test
    void testTokenValidityWithIncorrectUsername() {
        // Setup
        String correctUsername = "testuser";
        String incorrectUsername = "wronguser";
        String token = jwtService.getToken(userDetails);
        when(userDetails.getUsername()).thenReturn(correctUsername);
        when(jwtService.getUsernameFromToken(token)).thenReturn(incorrectUsername);
        // Act
        boolean isValid = jwtService.isTokenValid(token, userDetails);

        // Assert
        assertFalse(isValid, "Token should be invalid when username does not match.");
    }
}