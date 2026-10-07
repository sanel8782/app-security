package com.lideratec.appsecurity.user;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    //Métodos obligatorios de la interfaz UserDetails
    @Override
    public String getUsername() {
        return email;  //spring pide "username", tú le devuelves tu "email"
    }

    @Override
    public String getPassword() {
        return password; // Retorna tu contraseña encriptada
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Retornaria los roles o permisos del usuario (por ahora vacío o roles básicos)
        return Collections.emptyList();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // True si la cuenta no ha expirado
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // True si la cuenta no está bloqueada
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // True si las credenciales no han expirado
    }

    @Override
    public boolean isEnabled() {
        return true; // True si el usuario está activo
    }
}