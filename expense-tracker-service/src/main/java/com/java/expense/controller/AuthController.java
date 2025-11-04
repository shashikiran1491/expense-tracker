package com.java.expense.controller;

import com.google.api.client.auth.openidconnect.IdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.java.expense.exception.UserLoginException;
import com.java.expense.model.auth.*;
import com.java.expense.resource.AuthResource;
import com.java.expense.service.AuthService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@Slf4j
@RestController
@AllArgsConstructor
public class AuthController implements AuthResource {

    private final AuthService authService;

    @Override
    public ResponseEntity<RegisterResponse> register(RegistrationRequest registrationRequest) {
        RegisterResponse registerResponse = authService.register(registrationRequest);
        return ResponseEntity.ok(registerResponse);
    }

    @Override
    public ResponseEntity<LoginResponse> login(LoginRequest LoginRequest) {
        LoginResponse loginResponse = authService.login(LoginRequest);
        return ResponseEntity.ok(loginResponse);
    }

    @Override
    public ResponseEntity<GoogleLoginResponse> googleLogin(GoogleLoginRequest loginRequest) {
        GoogleLoginResponse googleLoginResponse = authService.googleLogin(loginRequest);
        return ResponseEntity.ok(googleLoginResponse);
    }
}
