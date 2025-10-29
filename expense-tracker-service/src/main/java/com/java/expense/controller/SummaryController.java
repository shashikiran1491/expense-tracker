package com.java.expense.controller;

import com.java.expense.model.CategorySummaryResponse;
import com.java.expense.model.SummaryResponse;
import com.java.expense.resource.SummaryResource;
import com.java.expense.service.SummaryService;
import com.java.expense.utils.JwtUtils;
import com.java.expense.utils.ValidatorUtils;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@AllArgsConstructor
public class SummaryController implements SummaryResource {

    private final JwtUtils jwtUtils;
    private final SummaryService summaryService;

    @Override
    public ResponseEntity<SummaryResponse> transactionSummary(String authHeader,
                                                              LocalDate startDate,
                                                              LocalDate endDate) {

        ValidatorUtils.validateStartDateAndEndDate(startDate, endDate);
        String email = extractEmailFromToken(authHeader);
        return ResponseEntity.ok(summaryService.transactionSummary(startDate, endDate, email));
    }

    @Override
    public ResponseEntity<List<CategorySummaryResponse>> getCategoryWiseSummary(String authHeader,
                                                                                LocalDate startDate,
                                                                                LocalDate endDate) {
        ValidatorUtils.validateStartDateAndEndDate(startDate, endDate);
        String email = extractEmailFromToken(authHeader);
        return ResponseEntity.ok(summaryService.getCategoryWiseSummary(startDate, endDate, email));
    }

    public String extractEmailFromToken(String authHeader) {
        String token = authHeader.substring(7);
        return jwtUtils.getUsernameFromToken(token);
    }
}
