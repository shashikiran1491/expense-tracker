package com.java.expense.controller;

import com.java.expense.model.ExpenseRequest;
import com.java.expense.model.ExpenseListResponse;
import com.java.expense.model.ExpenseResponse;
import com.java.expense.resource.ExpenseTrackerResource;
import com.java.expense.service.ExpenseTrackerService;
import com.java.expense.utils.JwtUtils;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

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
    public ResponseEntity<ExpenseListResponse> getExpenses(String authHeader, LocalDate startDate, LocalDate endDate, String category, String type ,Integer page, Integer pageSize) {
        String email = extractEmailFromToken(authHeader);
        ExpenseListResponse expenseListResponse = expenseTrackerService.getExpenses(email, startDate, endDate, category, type, page, pageSize);
        return ResponseEntity.ok(expenseListResponse);
    }

    @Override
    public ResponseEntity<ExpenseResponse> editExpense(String authHeader, Long id, ExpenseRequest expenseRequest) {
        String email = extractEmailFromToken(authHeader);
        ExpenseResponse expenseResponse = expenseTrackerService.editExpense(id, email, expenseRequest);
        return ResponseEntity.ok(expenseResponse);
    }

    @Override
    public ResponseEntity<Void> deleteExpense(String authHeader, Long id) {
        String email = extractEmailFromToken(authHeader);
        expenseTrackerService.deleteExpense(email, id);
        return ResponseEntity.noContent().build();
    }

    public String extractEmailFromToken(String authHeader) {
        String token = authHeader.substring(7);
        return jwtUtils.getUsernameFromToken(token);
    }
}
