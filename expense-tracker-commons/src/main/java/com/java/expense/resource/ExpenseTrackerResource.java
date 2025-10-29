package com.java.expense.resource;

import com.java.expense.model.ExpenseRequest;
import com.java.expense.model.ExpenseListResponse;
import com.java.expense.model.ExpenseResponse;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;


public interface ExpenseTrackerResource {

    @PostMapping("/api/expense-tracker/v1/expenses")
    ResponseEntity<Void> addExpense(@RequestHeader("Authorization") String authHeader,
                                    @Valid @RequestBody ExpenseRequest ExpenseRequest);

    @GetMapping("/api/expense-tracker/v1/expenses")
    ResponseEntity<ExpenseListResponse> getExpenses(@RequestHeader("Authorization") String authHeader,
                                                    @RequestParam  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                                    @RequestParam(required = false)  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
                                                    @RequestParam(required = false) String category,
                                                    @RequestParam(required = false) String type,
                                                    @RequestParam(required = false, defaultValue = "0") Integer page,
                                                    @RequestParam(required = false, defaultValue = "100") Integer pageSize
                                                    );

    @PutMapping("/api/expense-tracker/v1/expenses/{id}")
    ResponseEntity<ExpenseResponse> editExpense(@RequestHeader("Authorization") String authHeader,
                                                @PathVariable Long id,
                                                @RequestBody @Valid ExpenseRequest ExpenseRequest);


    @DeleteMapping("/api/expense-tracker/v1/expenses/{id}")
    ResponseEntity<Void> deleteExpense(@RequestHeader("Authorization") String authHeader,
                                                @PathVariable Long id);


}
