package com.java.expense.controller;

import com.java.expense.model.expense.ExpenseParams;
import com.java.expense.model.expense.ExpenseRequest;
import com.java.expense.model.expense.ExpenseResponse;
import com.java.expense.resource.ExpenseTrackerResource;
import com.java.expense.service.ExpenseTrackerService;
import com.java.expense.utils.JwtUtils;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@AllArgsConstructor
public class ExpenseTrackerController implements ExpenseTrackerResource {

    private final ExpenseTrackerService expenseTrackerService;
    private final JwtUtils jwtUtils;

    @Override
    public ResponseEntity<Void> addExpense(String authHeader, ExpenseRequest expenseRequest) {
        String email = extractEmailFromToken(authHeader);
        expenseTrackerService.addExpense(expenseRequest, email);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<ExpenseResponse> getExpenses(String authHeader, ExpenseParams expenseParams) {
        String email = extractEmailFromToken(authHeader);
        ExpenseResponse expenseResponse = expenseTrackerService.getExpenses(email, expenseParams);
        return ResponseEntity.ok(expenseResponse);
    }

    @Override
    public ResponseEntity<Void> editExpense(String authHeader, ExpenseParams expenseParams) {
        return null;
    }

    private String extractEmailFromToken(String authHeader) {
        String token = authHeader.substring(7);
        return jwtUtils.getUsernameFromToken(token);
    }
}
