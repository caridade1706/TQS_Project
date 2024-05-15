package pt.ua.deti.tqs.cliniconnect.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AuthResponseTest {

    private AuthResponse authResponse;

    @BeforeEach
    void setUp() {
        authResponse = new AuthResponse();
    }

    @Test
    void testSetters() {
        authResponse.setToken("token");

        assertEquals("token", authResponse.getToken());
    }

    @Test
    void testGetters() {
        authResponse.setToken("token");

        assertEquals("token", authResponse.getToken());
    }

    
}
