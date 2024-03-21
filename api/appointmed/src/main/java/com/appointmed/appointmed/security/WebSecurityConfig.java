package com.appointmed.appointmed.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    private final JwtAuthConverter jwtAuthConverter;

    public static final String APPOINTMED_PATIENT = "APPOINTMED_PATIENT";
    public static final String APPOINTMED_DOCTOR = "APPOINTMED_DOCTOR";
    public static final String APPOINTMED_ADMIN = "APPOINTMED_ADMIN";

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests
                        .requestMatchers(HttpMethod.GET, "/api/appointments", "/api/movies/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/csrf").permitAll()
                        .requestMatchers("/api/patients").hasAnyRole(APPOINTMED_PATIENT, APPOINTMED_ADMIN)
                        .requestMatchers("/api/doctors", "/api/movies/**").hasRole(APPOINTMED_DOCTOR)
                        .anyRequest().authenticated())
                .oauth2ResourceServer(oauth2ResourceServer -> oauth2ResourceServer.jwt(
                        jwt -> jwt.jwtAuthenticationConverter(jwtAuthConverter)))
                .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .cors(Customizer.withDefaults());
        return http.build();
    }





}