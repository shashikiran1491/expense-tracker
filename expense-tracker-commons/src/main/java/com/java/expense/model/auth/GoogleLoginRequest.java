package com.java.expense.model.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class GoogleLoginRequest {

    @NotBlank(message = "token is required")
    private String idToken;

}
