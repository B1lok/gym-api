package com.example.gymapi.service.impl;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.gymapi.data.UserRepository;
import com.example.gymapi.domain.Role;
import com.example.gymapi.domain.User;
import com.example.gymapi.exception.InvalidPasswordException;
import com.example.gymapi.exception.UserAlreadyExistException;
import com.example.gymapi.exception.UserNotFoundException;
import com.example.gymapi.security.JwtService;
import com.example.gymapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    @Override
    @Transactional
    public User createUser(User user) {
        if (userRepository.existsByPhoneNumber(user.getPhoneNumber())) {
            throw new UserAlreadyExistException("User with phone number %s already exist".formatted(user.getPhoneNumber()));
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new UserAlreadyExistException("User with email %s already exist".formatted(user.getEmail()));
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.getRoles().add(Role.USER);
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    @Transactional
    public Optional<DecodedJWT> signIn(String email, String password) {
        var user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User with this email doesn't exist"));
        if (!passwordEncoder.matches(password, user.getPassword()))
            throw new InvalidPasswordException("Invalid password");
        return jwtService.verifyAccessToken(jwtService.createJwtToken(user));
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    @Transactional
    public User updateUser(User user) {
        if (userRepository.isEmailInUse(user.getEmail(), user.getId()).isPresent()) {
            throw new UserAlreadyExistException("User with email %s already exist".formatted(user.getEmail()));
        }
        if (userRepository.isPhoneNumberInUse(user.getPhoneNumber(), user.getId()).isPresent()) {
            throw new UserAlreadyExistException("User with phone number %s already exist".formatted(user.getPhoneNumber()));
        }
        return userRepository.save(user);
    }
}
