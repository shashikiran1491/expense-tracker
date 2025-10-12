package com.java.expense.controller;

import com.java.expense.model.auth.LoginRequest;
import com.java.expense.model.auth.LoginResponse;
import com.java.expense.model.auth.RegistrationRequest;
import com.java.expense.resource.AuthResource;
import com.java.expense.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class AuthController implements AuthResource {

    private final AuthService authService;

    @Override
    public ResponseEntity<String> register(RegistrationRequest registrationRequest) {
        authService.register(registrationRequest);
        return ResponseEntity.ok("User Registered successfully");
    }

    @Override
    public ResponseEntity<LoginResponse> login(LoginRequest LoginRequest) {
        LoginResponse loginResponse = authService.login(LoginRequest);
        return ResponseEntity.ok(loginResponse);
    }
}
