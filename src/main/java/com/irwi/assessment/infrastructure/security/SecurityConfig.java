package com.irwi.assessment.infrastructure.security;

import com.irwi.assessment.domain.enums.Roles;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
@AllArgsConstructor
public class SecurityConfig {
    @Autowired
    private final JwtFilter jwtFilter;

    @Autowired
    private final AuthenticationProvider authenticationProvider;

    private final String[] PUBLIC_ENDPOINT = {
            "/api/auth/login",
            "/api/auth/register/patient",
            "/api/appointments/**",
            "/swagger-ui/**",
            "/v3/api-docs/**"
    };

    private static final String[] ADMIN_ENDPOINTS = {
            "/api/audit-logs/**",
            "/api/users/**"
    };

    private static final String[] DOCTOR_ENDPOINTS = {

            "/api/doctors/schedule/**"
    };

    private static final String[] PATIENT_ENDPOINTS = {

    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(PUBLIC_ENDPOINT).permitAll()

                        .requestMatchers(ADMIN_ENDPOINTS).hasAuthority(Roles.ADMIN.name())
                        .requestMatchers(DOCTOR_ENDPOINTS).hasAuthority(Roles.DOCTOR.name())
                        .requestMatchers(PATIENT_ENDPOINTS).hasAuthority(Roles.PATIENT.name())

                        .anyRequest().authenticated()
                )
                .authenticationProvider(authenticationProvider)
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
