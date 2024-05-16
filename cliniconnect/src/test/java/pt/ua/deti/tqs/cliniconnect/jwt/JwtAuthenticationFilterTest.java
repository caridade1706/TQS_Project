package pt.ua.deti.tqs.cliniconnect.jwt;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.io.IOException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import pt.ua.deti.tqs.cliniconnect.jwt.JwtAuthenticationFilter;
import pt.ua.deti.tqs.cliniconnect.jwt.JwtService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public class JwtAuthenticationFilterTest {

    @Mock
    private JwtService jwtService;

    @Mock
    private UserDetailsService userDetailsService;

    @InjectMocks
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Testa filtro sem token no cabeçalho")
    void testDoFilterInternalWithoutToken() throws ServletException, IOException {
        when(request.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn(null);

        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        verify(filterChain, times(1)).doFilter(request, response);
        verify(jwtService, times(0)).getUsernameFromToken(any());
    }

    @Test
    @DisplayName("Testa filtro com token válido")
    void testDoFilterInternalWithValidToken() throws ServletException, IOException {
        String token = "validToken";
        String authHeader = "Bearer " + token;
        String username = "testUser";

        when(request.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn(authHeader);
        when(jwtService.getUsernameFromToken(token)).thenReturn(username);

        UserDetails userDetails = mock(UserDetails.class);
        when(userDetailsService.loadUserByUsername(username)).thenReturn(userDetails);
        when(jwtService.isTokenValid(token, userDetails)).thenReturn(true);

        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        verify(jwtService, times(1)).getUsernameFromToken(token);
        verify(userDetailsService, times(1)).loadUserByUsername(username);
        verify(jwtService, times(1)).isTokenValid(token, userDetails);
        verify(filterChain, times(1)).doFilter(request, response);
        assertNotNull(SecurityContextHolder.getContext().getAuthentication());
        assertEquals(userDetails, SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }

    @Test
    @DisplayName("Testa filtro com token inválido")
    void testDoFilterInternalWithInvalidToken() throws ServletException, IOException {
        String token = "invalidToken";
        String authHeader = "Bearer " + token;
        String username = "testUser";

        when(request.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn(authHeader);
        when(jwtService.getUsernameFromToken(token)).thenReturn(username);

        UserDetails userDetails = mock(UserDetails.class);
        when(userDetailsService.loadUserByUsername(username)).thenReturn(userDetails);
        when(jwtService.isTokenValid(token, userDetails)).thenReturn(false);

        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        verify(jwtService, times(1)).getUsernameFromToken(token);
        verify(userDetailsService, times(1)).loadUserByUsername(username);
        verify(jwtService, times(1)).isTokenValid(token, userDetails);
        verify(filterChain, times(1)).doFilter(request, response);
        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }

    // @Test
    // @DisplayName("Testa filtro com token presente mas sem username")
    // void testDoFilterInternalWithTokenButNoUsername() throws ServletException, IOException {
    //     String token = "token";
    //     String authHeader = "Bearer " + token;

    //     when(request.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn(authHeader);
    //     when(jwtService.getUsernameFromToken(token)).thenReturn(null);

    //     jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

    //     verify(jwtService, times(1)).getUsernameFromToken(token);
    //     verify(userDetailsService, times(0)).loadUserByUsername(any());
    //     verify(jwtService, times(0)).isTokenValid(any(), any());
    //     verify(filterChain, times(1)).doFilter(request, response);
    //     assertNull(SecurityContextHolder.getContext().getAuthentication());
    // }
}
