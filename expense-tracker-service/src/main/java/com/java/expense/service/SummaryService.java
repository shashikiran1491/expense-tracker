package com.java.expense.service;

import com.java.expense.entity.User;
import com.java.expense.enums.Category;
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
import java.util.Comparator;
import java.util.List;
import java.util.Map;
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
        double totalIncome = expenseRepository.sumByTypeAndDateRange(user, "Income", startDateTime, endDateTime);
        double totalExpense = expenseRepository.sumByTypeAndDateRange(user, "Expense", startDateTime, endDateTime);
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

        List<String> categories = Category.getAllCategories();

        List<CategorySummary> categorySummaries = expenseRepository.findCategoryWiseSummary(user, startDateTime, endDateTime);
        Map<String, Double> categoryExpenseMap = categorySummaries
                .stream()
                .collect(Collectors.toMap(
                        CategorySummary::getCategory,
                        CategorySummary::getTotalAmount
                ));

        double totalExpense = categorySummaries.stream()
                .mapToDouble(CategorySummary::getTotalAmount)
                .sum();

        List<CategorySummaryResponse> categorySummaryResponses =  categories.stream().map(category -> {
                    double categoryExpense = getCategoryExpense(category, categoryExpenseMap);
                    return toCategoryResponse(category, categoryExpense, totalExpense);
                }).collect(Collectors.toList());

        categorySummaryResponses.sort(Comparator.comparingDouble(CategorySummaryResponse::getPercentage).reversed());

        return categorySummaryResponses;

    }

    private CategorySummaryResponse toCategoryResponse(String category, double categoryExpense, double totalExpense) {
        return CategorySummaryResponse
                .builder()
                .category(category)
                .totalAmount(categoryExpense)
                .percentage(calculatePercentage(categoryExpense, totalExpense))
                .build();
    }

    private static double getCategoryExpense(String category, Map<String, Double> categoryExpenseMap) {
        return categoryExpenseMap.getOrDefault(category, 0.00);
    }

    private double calculatePercentage(double categoryExpense, double totalExpenses) {
        return totalExpenses > 0 ? BigDecimal.valueOf(100 * categoryExpense / totalExpenses)
                .setScale(2, RoundingMode.HALF_UP).doubleValue() : 0;
    }
}
