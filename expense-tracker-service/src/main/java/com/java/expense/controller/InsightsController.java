package com.java.expense.controller;

import com.java.expense.model.InsightsResponse;
import com.java.expense.resource.InsightsResource;
import com.java.expense.service.InsightsService;
import com.java.expense.utils.JwtUtils;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class InsightsController implements InsightsResource {

    private final InsightsService insightsService;
    private final JwtUtils jwtUtils;

    @Override
    public ResponseEntity<InsightsResponse> getInsights(String authHeader, int month, int year) {
        String email = extractEmailFromToken(authHeader);
        return ResponseEntity.ok(insightsService.getInsights(email, month, year));
    }

    public String extractEmailFromToken(String authHeader) {
        String token = authHeader.substring(7);
        return jwtUtils.getUsernameFromToken(token);
    }
}
