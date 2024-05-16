package pt.ua.deti.tqs.cliniconnect.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import pt.ua.deti.tqs.cliniconnect.Roles;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class PersonaTest {

    private Persona persona;

    @BeforeEach
    public void setUp() {
        persona = new Persona();
    }

    //Persona(UUID, String, Date, String, String, String, String, String, Roles)

    @Test
    void createPersonaTest() {
        assertNotNull(persona);

        UUID id = UUID.randomUUID();
        Date date = new Date();

        persona = new Persona(id, "name", date, "email", "password", "123456789", "address", "city", Roles.PATIENT);

        
        assertEquals(id, persona.getId());
        assertEquals("name", persona.getName());
        assertEquals("email", persona.getEmail());
        assertEquals("password", persona.getPassword());
        assertEquals("123456789", persona.getPhone());
        assertEquals("address", persona.getAddress());
        assertEquals("city", persona.getCity());
        assertEquals(Roles.PATIENT, persona.getRole());
    }

    @Test
    void testSetters() {
        UUID id = UUID.randomUUID();
        Date date = new Date();

        persona.setId(id);
        persona.setName("name");
        persona.setDob(date);
        persona.setEmail("email");
        persona.setPassword("password");
        persona.setPhone("123456789");
        persona.setAddress("address");
        persona.setCity("city");
        persona.setRole(Roles.PATIENT);

        assertEquals(id, persona.getId());
        assertEquals("name", persona.getName());
        assertEquals(date, persona.getDob());
        assertEquals("email", persona.getEmail());
        assertEquals("password", persona.getPassword());
        assertEquals("123456789", persona.getPhone());
        assertEquals("address", persona.getAddress());
        assertEquals("city", persona.getCity());
        assertEquals(Roles.PATIENT, persona.getRole());
    }

    @Test
    void testGetAuthorities() {
        UUID id = UUID.randomUUID();
        Date date = new Date();

        persona = new Persona(id, "name", date, "email", "password", "123456789", "address", "city", Roles.PATIENT);

        Collection<? extends GrantedAuthority> authorities = persona.getAuthorities();
        assertEquals(1, authorities.size());
        assertTrue(authorities.contains(new SimpleGrantedAuthority(Roles.PATIENT.name())));
    }

    @Test
    void testIsAccountNonExpired() {
        assertTrue(persona.isAccountNonExpired());
    }

    @Test
    void testIsAccountNonLocked() {
        assertTrue(persona.isAccountNonLocked());
    }

    @Test
    void testIsCredentialsNonExpired() {
        assertTrue(persona.isCredentialsNonExpired());
    }

    @Test
    void testIsEnabled() {
        assertTrue(persona.isEnabled());
    }

    @Test
    void testGetUsername() {
        UUID id = UUID.randomUUID();
        Date date = new Date();

        persona = new Persona(id, "name", date, "email", "password", "123456789", "address", "city", Roles.PATIENT);

        assertEquals("email", persona.getUsername());
    }
    
}
