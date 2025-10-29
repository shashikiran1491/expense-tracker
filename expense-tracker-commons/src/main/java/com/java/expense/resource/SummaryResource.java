package com.java.expense.resource;

import com.java.expense.model.CategorySummaryResponse;
import com.java.expense.model.SummaryResponse;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

public interface SummaryResource {

    @GetMapping("/api/expense-tracker/v1/transactions/summary")
    ResponseEntity<SummaryResponse> transactionSummary(@RequestHeader("Authorization") String authHeader,
                                                       @RequestParam  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                                       @RequestParam  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate);



    @GetMapping("/api/expense-tracker/v1/transactions/summary/category")
    ResponseEntity<List<CategorySummaryResponse>> getCategoryWiseSummary(@RequestHeader("Authorization") String authHeader,
                                                                         @RequestParam  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                                                         @RequestParam  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate);


}
