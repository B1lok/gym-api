package com.example.gymapi.web.dto.user;



import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class UserCreationDto {

    @NotBlank
    @NotNull
    @Size(min = 2, max = 50)
    @Pattern(regexp = "^[a-zA-Z\\s]*$")
    private String name;

    @NotBlank
    @NotNull
    @Size(min = 2, max = 50)
    @Pattern(regexp = "^[a-zA-Z\\s-]*$")
    private String surname;

    @NotBlank(message = "Specify email")
    @Email(message = "Invalid email")
    private String email;

    @NotBlank(message = "Specify password")
    @Size(min = 4, max = 32, message = "Enter at least 4 and less than 32 characters")
    private String password;

    @Size(min = 10, max = 15)
    @Pattern(regexp = "^(\\+\\d{1,3})?\\d{10,14}$")
    private String phoneNumber;
}
