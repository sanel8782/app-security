package com.lideratec.appsecurity.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;

    @Bean
    public AuthenticationProvider authenticationProvider(
            UserDetailsService userDetailsService,
            PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }

    //método que le enseña a Spring cómo crear el AuthenticationManager
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            AuthenticationProvider authenticationProvider) throws Exception {
        return http
                .csrf(csrf -> csrf.disable()) // Desactivar CSRF para APIs REST
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/**").permitAll() // Rutas públicas de login/registro
                        .requestMatchers(HttpMethod.GET, "/products/**").hasAnyRole("USER", "ADMIN")// Ver uno o todos (USER y ADMIN)
                        .requestMatchers(HttpMethod.POST, "/products/**").hasRole("ADMIN")// Crear (Solo ADMIN)
                        .requestMatchers(HttpMethod.PUT, "/products/**").hasRole("ADMIN")// Actualizar (Solo ADMIN)
                        .requestMatchers(HttpMethod.DELETE, "/products/**").hasRole("ADMIN")// DELETE /products/{id} -> Solo ADMIN
                        .requestMatchers(HttpMethod.POST, "/orders/**").hasAnyRole("USER", "ADMIN")// POST /orders -> Permitido para USER y ADMIN

                        .anyRequest().authenticated() // Cualquier otra ruta requiere autenticación
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // API Stateless (sin sesiones HTTP)
                )
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class) // Agregamos nuestro filtro JWT
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}