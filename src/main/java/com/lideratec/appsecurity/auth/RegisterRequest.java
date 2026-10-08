package com.lideratec.appsecurity.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

//DTO q recibe los datos q el cliente manda en formato JSON
@Getter
@Setter
@Data
public class RegisterRequest {

    @NotBlank
    private String email;

    @NotBlank
    @Size(min = 6, max = 50)
    private String password;

}
