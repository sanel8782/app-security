package com.lideratec.appsecurity.auth; // O simplemente ponlo dentro del paquete com.lideratec.appsecurity

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;
//si
    @PostMapping("/register")
    public ResponseEntity<String> register(
            @RequestBody
            RegisterRequest request){
        try {
            authService.registrar(request);
            return ResponseEntity.ok("Usuario registrado exitosamente con contrasenia cifrada");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(
            @RequestBody
            LoginRequest request) {
        String token = authService.login(request);
        return ResponseEntity.ok(Collections.singletonMap("token", token));
    }




}
