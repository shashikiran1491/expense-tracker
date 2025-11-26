package com.java.expense.resource;

import com.java.expense.model.auth.*;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface AuthResource {

    @PostMapping("api/expense-tracker/v1/auth/register")
    ResponseEntity<RegisterResponse> register(@Valid @RequestBody RegistrationRequest registrationRequest);

    @PostMapping(value = "api/expense-tracker/v1/auth/login", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest userRequest);

    @PostMapping(value = "api/expense-tracker/v1/auth/login/google", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<GoogleLoginResponse> googleLogin(@Valid @RequestBody GoogleLoginRequest loginRequest);

    @GetMapping(value = "api/expense-tracker/v1/auth/me", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<UserDetailsResponse> getCurrentUser();
}
