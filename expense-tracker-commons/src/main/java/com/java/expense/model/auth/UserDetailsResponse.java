package com.java.expense.model.auth;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
public class UserDetailsResponse {

    private String email;
    private String firstName;
    private String lastName;
    private String pictureUrl;
    private LocalDateTime lastLogin;
}
