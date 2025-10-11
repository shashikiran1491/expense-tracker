package com.java.expense.resource;

import com.java.expense.model.LoginRequest;
import com.java.expense.model.LoginResponse;
import com.java.expense.model.RegistrationRequest;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface AuthResource {

    @PostMapping("api/expense-tracker/v1/auth/register")
    ResponseEntity<String> register(@Valid @RequestBody RegistrationRequest registrationRequest);

    @PostMapping(value = "api/expense-tracker/v1/auth/login", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest userRequest);
}
