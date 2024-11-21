package com.example.gymapi.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.gymapi.config.TestContainerInitializer;
import com.example.gymapi.data.UserRepository;
import com.example.gymapi.web.dto.auth.AuthRequest;
import com.example.gymapi.web.dto.user.UserCreationDto;
import java.util.stream.Stream;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;


public class AuthControllerTest extends TestContainerInitializer {
    private final UserCreationDto VALID_USER_CREATION = createValidUser();
    private final AuthRequest AUTH_REQUEST = createAuthRequest();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserRepository userRepository;

    private static UserCreationDto createValidUser() {
        UserCreationDto userCreationDto = new UserCreationDto();
        userCreationDto.setFirstName("John");
        userCreationDto.setLastName("Doe");
        userCreationDto.setEmail("john.doe@example.com");
        userCreationDto.setPassword("StrongPass123");
        userCreationDto.setPhoneNumber("+38(067)123-45-67");
        return userCreationDto;
    }

    private static Stream<UserCreationDto> invalidUserCreationDto() {
        return Stream.of(
                new UserCreationDto() {{
                    setFirstName("");
                    setLastName("Doe");
                    setEmail("john.doe@example.com");
                    setPassword("StrongPass123");
                    setPhoneNumber("+38(067)123-45-67");
                }},
                new UserCreationDto() {{
                    setFirstName("John");
                    setLastName("");
                    setEmail("john.doe@example.com");
                    setPassword("StrongPass123");
                    setPhoneNumber("+38(067)123-45-67");
                }},
                new UserCreationDto() {{
                    setFirstName("John");
                    setLastName("Doe");
                    setEmail("invalid-email");
                    setPassword("StrongPass123");
                    setPhoneNumber("+38(067)123-45-67");
                }},
                new UserCreationDto() {{
                    setFirstName("John");
                    setLastName("Doe");
                    setEmail("john.doe@example.com");
                    setPassword("short");
                    setPhoneNumber("+38(067)123-45-67");
                }},
                new UserCreationDto() {{
                    setFirstName("John");
                    setLastName("Doe");
                    setEmail("john.doe@example.com");
                    setPassword("StrongPass123");
                    setPhoneNumber("12345");
                }}
        );
    }


    @Test
    @SneakyThrows
    public void shouldCreateUser() {
        mockMvc.perform(post("/sign-up").contentType(MediaType.APPLICATION_JSON).content(
                        objectMapper.writeValueAsString(VALID_USER_CREATION)))
                .andExpect(status().isOk());
        assertThat(userRepository.findByEmail("john.doe@example.com").get().getId()).isEqualTo(1);
    }

    @ParameterizedTest
    @SneakyThrows
    @MethodSource("invalidUserCreationDto")
    public void shouldNotCreateUser(UserCreationDto userCreationDto) {
        mockMvc.perform(post("/sign-up").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userCreationDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @SneakyThrows
    public void shouldSignIn() {
        //TODO
    }


    private AuthRequest createAuthRequest() {
        var request = new AuthRequest();
        request.setEmail("john.doe@example.com");
        request.setPassword("StrongPassword123");
        return request;
    }


}
