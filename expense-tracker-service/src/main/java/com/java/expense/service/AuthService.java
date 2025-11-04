package com.java.expense.service;

import com.google.api.client.auth.openidconnect.IdToken;
import com.java.expense.entity.User;
import com.java.expense.exception.UserLoginException;
import com.java.expense.exception.UserRegistrationException;
import com.java.expense.model.auth.*;
import com.java.expense.repository.AuthRepository;
import com.java.expense.utils.GoogleTokenVerifier;
import com.java.expense.utils.JwtUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

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
        String token = jwtUtils.generateToken(loginRequest.getEmail());
        return LoginResponse.builder().token(token).build();
    }

    public GoogleLoginResponse googleLogin(GoogleLoginRequest loginRequest) {
        IdToken.Payload payload = GoogleTokenVerifier.verifyToken(loginRequest);
        String email = payload.get("email").toString();

        boolean userExists = authRepository.existsByEmail(email);

        if(!userExists) {
            User user = User.builder()
                    .email(email)
                    .password("default_password")
                    .enabled(true)
                    .build();
            authRepository.save(user);
        }

        String token = jwtUtils.generateToken(email);
        return GoogleLoginResponse.builder().token(token).build();
    }
}
