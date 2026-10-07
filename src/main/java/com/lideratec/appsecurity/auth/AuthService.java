package com.lideratec.appsecurity.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.lideratec.appsecurity.user.User;
import com.lideratec.appsecurity.user.UserRepository;

//cifra la contraseña con BCrypt antes de mandarla al repositorio.
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;

    public void registrar(RegisterRequest request){
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw  new RuntimeException("El correo ya esta registrado");
        }

        String passwordSeguro = passwordEncoder.encode(request.getPassword());

        User nuevoUsuario = new User();
        nuevoUsuario.setEmail(request.getEmail());
        nuevoUsuario.setPassword(passwordSeguro);

        userRepository.save(nuevoUsuario);
    }

    public String login(LoginRequest request) {
        //AuthenticationManager valida las credenciales
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        //si es correcto, cargamos los detalles del usuario
        UserDetails user = userDetailsService.loadUserByUsername(request.getEmail());

        //generamos y retornamos el token JWT
        return jwtService.generateToken(user);
    }
}
