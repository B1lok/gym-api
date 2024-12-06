package com.example.gymapi.web;

import com.example.gymapi.config.TestContextInitializer;
import com.example.gymapi.data.UserRepository;
import com.example.gymapi.web.dto.auth.AuthRequest;
import com.example.gymapi.web.dto.user.UserCreationDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(initializers = TestContextInitializer.class)
@AutoConfigureMockMvc
public class AuthControllerIT {
  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private UserRepository userRepository;

  @Autowired
  private ObjectMapper objectMapper;

  @AfterEach
  void tearDown() {
    userRepository.deleteAll();
  }
  @Test
  void signUp_Successful() throws Exception {
    UserCreationDto userCreationDto = new UserCreationDto();
    userCreationDto.setFirstName("John");
    userCreationDto.setLastName("Doe");
    userCreationDto.setEmail("john.doe@example.com");
    userCreationDto.setPassword("StrongPass123");
    userCreationDto.setPhoneNumber("+38(097)123-45-67");

    mockMvc.perform(post("/sign-up")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(userCreationDto)))
        .andExpect(status().isOk());
  }

  @Test
  void signUp_InvalidData() throws Exception {
    UserCreationDto userCreationDto = new UserCreationDto();
    userCreationDto.setFirstName("J0hn"); // Invalid first name (contains a digit)
    userCreationDto.setLastName(""); // Empty last name
    userCreationDto.setEmail("invalid-email"); // Invalid email
    userCreationDto.setPassword("123"); // Too short
    userCreationDto.setPhoneNumber("12345"); // Invalid phone format

    mockMvc.perform(post("/sign-up")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(userCreationDto)))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.firstName[0]").value("Latin letters only"))
        .andExpect(jsonPath("$.email[0]").value("Invalid email"))
        .andExpect(jsonPath("$.password[0]").value("8 characters are required"))
        .andExpect(jsonPath("$.phoneNumber[0]").value("Invalid phone number"));
  }

  @Test
  @Sql("/sql/insert-users.sql")
  void signIn_Successful() throws Exception {
    AuthRequest authRequest = new AuthRequest();
    authRequest.setEmail("john.doe@example.com");
    authRequest.setPassword("12345678");

    mockMvc.perform(post("/sign-in")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(authRequest)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.token").isNotEmpty())
        .andExpect(jsonPath("$.type").value("JWT"))
        .andExpect(jsonPath("$.algorithm").value("HS256"));
  }

  @Test
  void signIn_InvalidData() throws Exception {
    AuthRequest authRequest = new AuthRequest();
    authRequest.setEmail(""); // Missing email
    authRequest.setPassword(""); // Missing password

    mockMvc.perform(post("/sign-in")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(authRequest)))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.email").value("Invalid Login"))
        .andExpect(jsonPath("$.password").value("Invalid Password"));
  }

  @Test
  void signIn_UserNotFound() throws Exception {
    AuthRequest authRequest = new AuthRequest();
    authRequest.setEmail("nonexistent@example.com");
    authRequest.setPassword("SomePassword");

    mockMvc.perform(post("/sign-in")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(authRequest)))
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.message").value("User with this email doesn't exist"));
  }
}
