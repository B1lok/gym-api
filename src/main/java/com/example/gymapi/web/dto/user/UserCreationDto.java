package com.example.gymapi.web.dto.user;


import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class UserCreationDto {
    @NotNull
    @NotBlank(message = "FirstName is required")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "Latin letters only")
    @Size(max = 32, message = "32 characters are maximum")
    private String firstName;

    @NotNull
    @NotBlank(message = "LastName is required")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "Latin letters only")
    @Size(max = 32, message = "32 characters are maximum")
    private String lastName;

    @NotNull
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email")
    private String email;

    @NotNull
    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "8 characters are required")
    @Size(max = 32, message = "32 characters are maximum")
    private String password;

    @NotNull
    @Pattern(regexp = "^\\+38\\(0[0-9]{2}\\)[0-9]{3}-[0-9]{2}-[0-9]{2}$")
    private String phoneNumber;
}
