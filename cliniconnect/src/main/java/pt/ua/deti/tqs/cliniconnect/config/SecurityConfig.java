package pt.ua.deti.tqs.cliniconnect.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.authentication.AuthenticationProvider;

import lombok.RequiredArgsConstructor;
import pt.ua.deti.tqs.cliniconnect.jwt.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenciationFilter;
    private final AuthenticationProvider authProvider;
   
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        
        return http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(authRequest ->
                authRequest
                    .anyRequest().permitAll() 
                    // .requestMatchers("/api/patients/register").permitAll()
                    // .requestMatchers("/api/patients/login").permitAll()
                    // .anyRequest().authenticated()
            )
            .sessionManagement(sessionManagement ->
                sessionManagement
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authenticationProvider(authProvider)
            .addFilterBefore(jwtAuthenciationFilter, UsernamePasswordAuthenticationFilter.class)
            .build();
    }
}