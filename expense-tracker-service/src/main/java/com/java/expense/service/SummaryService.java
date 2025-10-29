package com.java.expense.service;

import com.java.expense.entity.User;
import com.java.expense.model.CategorySummary;
import com.java.expense.model.CategorySummaryResponse;
import com.java.expense.model.SummaryResponse;
import com.java.expense.repository.ExpenseRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class SummaryService {

    private final ExpenseRepository expenseRepository;
    private final UserService userService;

    public SummaryResponse transactionSummary(LocalDate startDate, LocalDate endDate, String userEmail) {
        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.atTime(23, 59, 59);

        User user = userService.getUser(userEmail);
        double totalIncome = expenseRepository.sumByTypeAndDateRange(user,"Income", startDateTime, endDateTime);
        double totalExpense = expenseRepository.sumByTypeAndDateRange(user,"Expense", startDateTime, endDateTime);
        double netBalance = totalIncome - totalExpense;

        return SummaryResponse.builder()
                .totalIncome(totalIncome)
                .totalExpense(totalExpense)
                .netBalance(netBalance)
                .build();
    }

    public List<CategorySummaryResponse> getCategoryWiseSummary(LocalDate startDate, LocalDate endDate, String userEmail) {
        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.atTime(23, 59, 59);

        User user = userService.getUser(userEmail);
        List<CategorySummary> categorySummaries = expenseRepository.findCategoryWiseSummary(user, startDateTime, endDateTime);

        double totalExpense = categorySummaries.stream()
               .mapToDouble(CategorySummary::getTotalAmount)
               .sum();

        return categorySummaries.stream()
                .map(cs -> CategorySummaryResponse
                        .builder()
                        .category(cs.getCategory())
                        .totalAmount(cs.getTotalAmount())
                        .percentage(calculatePercentage(cs, totalExpense))
                        .build())
                .toList();
    }

    private static double calculatePercentage(CategorySummary cs, double totalExpense) {
        return totalExpense > 0 ? BigDecimal.valueOf(100 * cs.getTotalAmount() / totalExpense)
                .setScale(2, RoundingMode.HALF_UP).doubleValue() : 0;
    }
}
