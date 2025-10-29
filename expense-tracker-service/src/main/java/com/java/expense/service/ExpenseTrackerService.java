package com.java.expense.service;

import com.java.expense.builder.ExpenseBuilder;
import com.java.expense.entity.Expense;
import com.java.expense.entity.User;
import com.java.expense.exception.ExpenseNotFoundException;
import com.java.expense.model.ExpenseRequest;
import com.java.expense.model.ExpenseListResponse;
import com.java.expense.model.ExpenseResponse;
import com.java.expense.repository.ExpenseRepository;
import com.java.expense.repository.specification.ExpenseSpecification;
import com.java.expense.utils.ValidatorUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static com.java.expense.builder.ExpenseBuilder.buildExpenseResponse;

@Service
@Slf4j
@AllArgsConstructor
public class ExpenseTrackerService {

    private final ExpenseRepository expenseRepository;
    private final UserService userService;

    public void addExpense(ExpenseRequest expenseRequest, String email) {

        User user = userService.getUser(email);

        Expense expense = Expense.builder()
                .amount(expenseRequest.getAmount())
                .expenseType(expenseRequest.getExpenseType())
                .paidTo(expenseRequest.getPaidTo())
                .expenseDate(expenseRequest.getExpenseDate()!=null ? expenseRequest.getExpenseDate() : LocalDateTime.now())
                .category(expenseRequest.getCategory())
                .description(expenseRequest.getDescription())
                .user(user)
                .build();

        expenseRepository.save(expense);
    }

    public ExpenseListResponse getExpenses(String email, LocalDate startDate, LocalDate endDate, String category, String type, Integer page, Integer pageSize) {
        Page<Expense> expensePage;

        if(endDate == null) {
            endDate = LocalDate.now();
        }

        ValidatorUtils.validateStartDateAndEndDate(startDate, endDate);

        User user = userService.getUser(email);
        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.atTime(LocalTime.MAX);

        Pageable pageable = PageRequest.of(page, pageSize, Sort.by("expenseDate").descending());

        expensePage = expenseRepository.findAll(
                ExpenseSpecification.filterExpenses(user, category, type, startDateTime, endDateTime),
                pageable
        );

        List<ExpenseResponse> expenseList = expensePage.getContent()
                .stream()
                .map(ExpenseBuilder::buildExpense)
                .toList();

        return buildExpenseResponse(expenseList, expensePage);
    }

    public ExpenseResponse editExpense(Long id, String email, ExpenseRequest expenseRequest) {
        User user = userService.getUser(email);

        Expense existingExpense = expenseRepository.findExpenseByUserAndId(user, id)
                .orElseThrow(() -> new ExpenseNotFoundException("Expense not found. expenseId: " + id));

        existingExpense.setAmount(expenseRequest.getAmount());
        existingExpense.setExpenseType(expenseRequest.getExpenseType());
        existingExpense.setExpenseDate(expenseRequest.getExpenseDate());
        existingExpense.setCategory(expenseRequest.getCategory());
        existingExpense.setDescription(expenseRequest.getDescription());

        Expense expense = expenseRepository.save(existingExpense);
        return ExpenseBuilder.buildExpense(expense);
    }

    public void deleteExpense(String email, Long id) {
        User user = userService.getUser(email);

       Expense existingExpense = expenseRepository.findExpenseByUserAndId(user, id)
                .orElseThrow(() -> new ExpenseNotFoundException("Expense not found. expenseId: " + id));

       expenseRepository.delete(existingExpense);
    }
}
