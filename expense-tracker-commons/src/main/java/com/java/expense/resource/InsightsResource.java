package com.java.expense.resource;

import com.java.expense.model.ExpenseListResponse;
import com.java.expense.model.InsightsResponse;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

public interface InsightsResource {

    @GetMapping("/api/expense-tracker/v1/insights")
    ResponseEntity<InsightsResponse> getInsights(@RequestHeader("Authorization") String authHeader,
                                                 @RequestParam int month,
                                                 @RequestParam int year);

}
