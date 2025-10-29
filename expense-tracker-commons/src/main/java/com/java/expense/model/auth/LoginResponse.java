package com.java.expense.model.auth;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class LoginResponse {

    private String token;
    @Builder.Default
    private String type = "Bearer";

}
