package com.java.expense.resource;

import com.java.expense.model.expense.ExpenseParams;
import com.java.expense.model.expense.ExpenseRequest;
import com.java.expense.model.expense.ExpenseListResponse;
import com.java.expense.model.expense.ExpenseResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


public interface ExpenseTrackerResource {

    @PostMapping("/api/expense-tracker/v1/expenses")
    ResponseEntity<Void> addExpense(@RequestHeader("Authorization") String authHeader,
                                    @Valid @RequestBody ExpenseRequest ExpenseRequest);

    @GetMapping("/api/expense-tracker/v1/expenses")
    ResponseEntity<ExpenseListResponse> getExpenses(@RequestHeader("Authorization") String authHeader,
                                                    @RequestBody ExpenseParams expenseParams);

    @PutMapping("/api/expense-tracker/v1/expenses/{id}")
    ResponseEntity<ExpenseResponse> editExpense(@RequestHeader("Authorization") String authHeader,
                                                @PathVariable Long id,
                                                @RequestBody @Valid ExpenseRequest ExpenseRequest);


    @DeleteMapping("/api/expense-tracker/v1/expenses/{id}")
    ResponseEntity<Void> deleteExpense(@RequestHeader("Authorization") String authHeader,
                                                @PathVariable Long id);


}
