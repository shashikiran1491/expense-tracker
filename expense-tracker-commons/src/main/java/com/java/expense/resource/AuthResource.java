package com.java.expense.resource;

import com.java.expense.model.UserRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface AuthResource {

    @PostMapping("api/expense-tracker/v1/auth/register")
    ResponseEntity<String> register(@Valid @RequestBody UserRequest userRequest);
}
