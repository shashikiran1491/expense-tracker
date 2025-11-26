package com.java.expense.service;

import com.google.api.client.auth.openidconnect.IdToken;
import com.java.expense.entity.User;
import com.java.expense.enums.LoginProvider;
import com.java.expense.exception.UserLoginException;
import com.java.expense.exception.UserRegistrationException;
import com.java.expense.model.auth.*;
import com.java.expense.repository.AuthRepository;
import com.java.expense.utils.GoogleTokenVerifier;
import com.java.expense.utils.JwtUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Slf4j
@AllArgsConstructor
public class AuthService {

    private final AuthRepository authRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    public RegisterResponse register(RegistrationRequest registrationRequest) {
        boolean userExists = authRepository.existsByEmail(registrationRequest.getEmail());

        if (userExists) {
            throw new UserRegistrationException("User with given email already exists");
        } else {
            registerUser(registrationRequest);
            return RegisterResponse.builder()
                    .message("User Registered successfully")
                    .build();
        }
    }

    private void registerUser(RegistrationRequest registrationRequest) {
        User user = User.builder()
                .email(registrationRequest.getEmail())
                .password(passwordEncoder.encode(registrationRequest.getPassword()))
                .firstName(registrationRequest.getFirstName())
                .lastName(registrationRequest.getLastName())
                .loginProvider(LoginProvider.INTERNAL)
                .enabled(true)
                .build();
        authRepository.save(user);
    }

    public LoginResponse login(LoginRequest loginRequest) {
        try {
            authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),
                            loginRequest.getPassword()));

        } catch (AuthenticationException e) {
            throw new UserLoginException("Invalid email or password");
        }

        User user = authRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setLastLogin(LocalDateTime.now());
        authRepository.save(user);

        String token = jwtUtils.generateToken(loginRequest.getEmail());
        return LoginResponse.builder().token(token).build();
    }

    public GoogleLoginResponse googleLogin(GoogleLoginRequest loginRequest) {
        IdToken.Payload payload = GoogleTokenVerifier.verifyToken(loginRequest);

        String email = payload.get("email").toString();
        String firstName = payload.get("given_name").toString();
        String lastName = payload.getOrDefault("family_name", Strings.EMPTY).toString();
        String pictureUrl = payload.getOrDefault("picture", Strings.EMPTY).toString();

        boolean userExists = authRepository.existsByEmail(email);

        log.info("Google Login attempt for email: {}, userExists: {}", email, userExists);

        User user = authRepository.findByEmail(email).orElseGet(() -> User.builder()
                        .email(email)
                        .password(passwordEncoder.encode(UUID.randomUUID().toString()))
                        .firstName(firstName)
                        .lastName(lastName)
                        .pictureUrl(pictureUrl)
                        .enabled(true)
                        .loginProvider(LoginProvider.GOOGLE)
                        .build());

        user.setLastLogin(LocalDateTime.now());
        authRepository.save(user);

        String token = jwtUtils.generateToken(email);
        return GoogleLoginResponse.builder().token(token).build();
    }
}
