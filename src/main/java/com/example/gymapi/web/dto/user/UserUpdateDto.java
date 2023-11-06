package com.example.gymapi.web.dto.user;


import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class UserUpdateDto {

    @NotNull
    @Size(min = 2, max = 50)
    @Pattern(regexp = "^[a-zA-Z\\s]*$")
    private String name;

    @NotNull
    @Size(min = 2, max = 50)
    @Pattern(regexp = "^[a-zA-Z\\s-]*$")
    private String surname;

    @Email(message = "Invalid email")
    private String email;

    @Size(min = 10, max = 15)
    private String phoneNumber;
}
