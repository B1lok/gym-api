package com.example.gymapi.web.dto.auth;


import jakarta.validation.constraints.NotBlank;
import lombok.Data;
@Data
public class AuthRequest {
    @NotBlank(message = "Specify Login")
    private String email;

    @NotBlank(message = "Specify Password")
    private String password;

}
