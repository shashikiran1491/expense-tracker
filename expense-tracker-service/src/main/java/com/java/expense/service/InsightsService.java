package com.java.expense.service;

import com.java.expense.entity.Expense;
import com.java.expense.entity.User;
import com.java.expense.model.HighestCategory;
import com.java.expense.model.InsightsResponse;
import com.java.expense.repository.ExpenseRepository;
import com.java.expense.repository.specification.ExpenseSpecification;
import com.java.expense.utils.ValidatorUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class InsightsService {

    private final ExpenseRepository expenseRepository;
    private final UserService userService;

    public InsightsResponse getInsights(String email, int month, int year) {
        ValidatorUtils.validateMonthAndYear(month, year);
        User user = userService.getUser(email);

        YearMonth ym = YearMonth.of(year, month);
        log.info("Printing YearMonth {} ", ym);

        int totalDaysInMonth = ym.lengthOfMonth();
        log.info("Printing totalDaysInMonth {} ", totalDaysInMonth);

        LocalDate monthStartDate = ym.atDay(1);
        LocalDate monthEndDate = ym.atEndOfMonth();

        log.info("Printing monthStartDate {} and monthEndDate {} ", monthStartDate, monthEndDate);

        LocalDate now = LocalDate.now();

        long daysElapsed;
        long daysRemaining;

        if(year == now.getYear() && month == now.getMonth().getValue()) {
            daysElapsed = ChronoUnit.DAYS.between(monthStartDate, now) + 1;
            daysRemaining = totalDaysInMonth - daysElapsed;
        } else {
            daysElapsed = ChronoUnit.DAYS.between(monthStartDate, monthEndDate) + 1;
            daysRemaining = 0;
        }

        log.info("Days elapsed {} and remaining {} ", daysElapsed, daysRemaining);

        List<Expense> expenses = expenseRepository.findAll(ExpenseSpecification
                .filterExpenses(user, null, "Expense", monthStartDate.atStartOfDay(), monthEndDate.atTime(LocalTime.MAX)));

        log.info("Print list of expenses  {} ", expenses);

        if (expenses.isEmpty()) {
          return InsightsResponse.builder()
                    .averageDailySpending(0)
                    .projectedMonthlyTotal(0)
                    .noOfDaysRemaining(daysRemaining)
                    .highestCategory(null)
                  .build();
        }

        double totalExpenses = expenses.stream().mapToDouble(Expense::getAmount).sum();

        double averageDailySpending = totalExpenses/daysElapsed;

        double projectedMonthlyTotal = expenses.size() < 3 ? totalExpenses
                : (averageDailySpending * totalDaysInMonth);

        log.info("Print totalExpense : {}, averageDailySpending: {},  projectedMonthlyTotal : {}",
                totalExpenses, averageDailySpending, projectedMonthlyTotal);

        Map<String, Double> categoryExpenseMap = expenses.stream()
                .collect(Collectors.groupingBy(Expense::getCategory , Collectors.summingDouble(Expense::getAmount)));

        String highestCategory = categoryExpenseMap.entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry :: getKey)
                .orElse(null);

        double highestCategoryAmount = highestCategory != null ? categoryExpenseMap.get(highestCategory) : 0;

        return InsightsResponse.builder()
                .averageDailySpending(Math.round(averageDailySpending))
                .projectedMonthlyTotal(Math.round(projectedMonthlyTotal))
                .noOfDaysRemaining(daysRemaining)
                .highestCategory(HighestCategory
                        .builder()
                        .category(highestCategory)
                        .amount(highestCategoryAmount)
                        .build()).build();
    }
}
