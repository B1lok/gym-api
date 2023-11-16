package com.example.gymapi.web.controller;


import com.example.gymapi.service.UserService;
import com.example.gymapi.web.dto.ExceptionResponse;
import com.example.gymapi.web.dto.auth.AuthRequest;
import com.example.gymapi.web.dto.auth.AuthResponse;
import com.example.gymapi.web.dto.user.UserCreationDto;
import com.example.gymapi.web.dto.user.UserDto;
import com.example.gymapi.web.mapper.AuthMapper;
import com.example.gymapi.web.mapper.UserMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Authentication controller")
@RestController
@RequiredArgsConstructor
@CrossOrigin
public class AuthController {

    private final UserService userService;

    private final UserMapper userMapper;

    private final AuthMapper authMapper;

    @Operation(summary = "Create new user", responses = {
            @ApiResponse(responseCode = "200",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "400",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "404",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ExceptionResponse.class))),
    })
    @PostMapping("/sign-up")
    public ResponseEntity<Void> signUp(@Valid @RequestBody UserCreationDto userCreationDto) {
        userService.createUser(userMapper.toEntity(userCreationDto));
        return ResponseEntity.ok().build();
    }
    @Operation(summary = "Sign in user", responses = {
            @ApiResponse(responseCode = "200",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = AuthResponse.class))),
            @ApiResponse(responseCode = "400",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "404",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ExceptionResponse.class))),
    })
    @PostMapping("/sign-in")
    public ResponseEntity<AuthResponse> signIn(@Valid @RequestBody AuthRequest authRequest) {
        return ResponseEntity.of(userService.signIn(authRequest.getEmail(), authRequest.getPassword())
                .map(authMapper::toAuthResponse));
    }
}
