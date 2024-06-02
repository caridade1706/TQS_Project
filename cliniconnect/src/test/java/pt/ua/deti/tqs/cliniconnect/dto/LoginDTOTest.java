package pt.ua.deti.tqs.cliniconnect.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LoginDTOTest {

    private LoginDTO loginDTO;

    @BeforeEach
    void setUp() {
        loginDTO = new LoginDTO();
    }

    @Test
    void testSetters() {
        loginDTO.setEmail("email");
        loginDTO.setPassword("password");

        assertEquals("email", loginDTO.getEmail());
        assertEquals("password", loginDTO.getPassword());
    }

    @Test
    void testGetters() {
        loginDTO.setEmail("email");
        loginDTO.setPassword("password");

        assertEquals("email", loginDTO.getEmail());
        assertEquals("password", loginDTO.getPassword());
    }
    
    
}
