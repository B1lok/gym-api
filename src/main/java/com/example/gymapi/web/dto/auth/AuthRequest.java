package com.example.gymapi.web.dto.auth;


import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AuthRequest {
    @NotBlank(message = "Invalid Login")
    private String email;

    @NotBlank(message = "Invalid Password")
    private String password;
}
