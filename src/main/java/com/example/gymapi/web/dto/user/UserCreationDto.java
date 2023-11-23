package com.example.gymapi.web.dto.user;



import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class UserCreationDto {

    @NotBlank(message = "Specify firstName")
    @NotNull
    @Size(min = 2, max = 50, message = "Too short")
    @Pattern(regexp = "^[a-zA-Z\\s]*$")
    private String firstName;

    @NotBlank(message = "Specify lastName")
    @NotNull
    @Size(min = 2, max = 50)
    @Pattern(regexp = "^[a-zA-Z\\s-]*$")
    private String lastName;

    @NotBlank(message = "Specify email")
    @Email(message = "Invalid email")
    private String email;

    @NotBlank(message = "Specify password")
    @Size(min = 4, max = 32, message = "Enter at least 4 and less than 32 characters")
    private String password;


    private String phoneNumber;
}
