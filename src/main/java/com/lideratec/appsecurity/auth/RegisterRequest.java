package com.lideratec.appsecurity.auth;

import lombok.Getter;
import lombok.Setter;

//DTO q recibe los datos q el cliente manda en formato JSON
@Getter
@Setter
public class RegisterRequest {

    private String email;
    private String password;

}
