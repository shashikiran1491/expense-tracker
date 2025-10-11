package com.java.expense.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class LoginResponse {

    private String token;
    @Builder.Default
    private String type = "Bearer";

}
