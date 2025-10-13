package com.java.expense.service;

import com.java.expense.builder.ExpenseBuilder;
import com.java.expense.entity.Expense;
import com.java.expense.entity.User;
import com.java.expense.exception.ExpenseNotFoundException;
import com.java.expense.exception.UserNotFoundException;
import com.java.expense.model.expense.ExpenseParams;
import com.java.expense.model.expense.ExpenseRequest;
import com.java.expense.model.expense.ExpenseListResponse;
import com.java.expense.model.expense.ExpenseResponse;
import com.java.expense.repository.AuthRepository;
import com.java.expense.repository.ExpenseRepository;
import com.java.expense.utils.ValidatorUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static com.java.expense.builder.ExpenseBuilder.buildExpenseResponse;

@Service
@Slf4j
@AllArgsConstructor
public class ExpenseTrackerService {

    private final ExpenseRepository expenseRepository;
    private final AuthRepository authRepository;

    public void addExpense(ExpenseRequest expenseRequest, String email) {

        User user = getUser(email);

        Expense expense = Expense.builder()
                .amount(expenseRequest.getAmount())
                .expenseType(expenseRequest.getExpenseType())
                .expenseDate(expenseRequest.getExpenseDate()!=null ? expenseRequest.getExpenseDate() : LocalDateTime.now())
                .category(expenseRequest.getCategory())
                .description(expenseRequest.getDescription())
                .user(user)
                .build();

        expenseRepository.save(expense);
    }

    public ExpenseListResponse getExpenses(String email, ExpenseParams expenseParams) {
        log.info("Printing expense params : {}", expenseParams);
        Page<Expense> expensePage;
        setEndDate(expenseParams);

        User user = getUser(email);

        LocalDateTime startDate = expenseParams.getStartDate().atStartOfDay();
        LocalDateTime endDate = expenseParams.getEndDate().atTime(LocalTime.MAX);
        ValidatorUtils.validateStartDateAndEndDate(expenseParams.getStartDate(), expenseParams.getEndDate());

        Pageable pageable = PageRequest.of(expenseParams.getPage(), expenseParams.getPageSize());

        if(StringUtils.hasLength(expenseParams.getCategory())) {
            expensePage = expenseRepository.findByUserAndCategoryAndExpenseDateBetween(user, expenseParams.getCategory(), startDate, endDate, pageable);
        } else {
            expensePage = expenseRepository.findByUserAndExpenseDateBetween(user, startDate, endDate, pageable);
        }

        List<com.java.expense.model.expense.ExpenseResponse> expenseList = expensePage.getContent()
                .stream()
                .map(ExpenseBuilder::buildExpense)
                .toList();

        return buildExpenseResponse(expenseList, expensePage);
    }


    public ExpenseResponse editExpense(Long id, String email, ExpenseRequest expenseRequest) {
        User user = getUser(email);

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
        User user = getUser(email);

       Expense existingExpense = expenseRepository.findExpenseByUserAndId(user, id)
                .orElseThrow(() -> new ExpenseNotFoundException("Expense not found. expenseId: " + id));

       expenseRepository.delete(existingExpense);
    }

    private static void setEndDate(ExpenseParams expenseParams) {
        if(expenseParams.getEndDate() == null) {
            expenseParams.setEndDate(LocalDateTime.now().toLocalDate());
        }
    }

    private User getUser(String email) {
        return authRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("User not found. userId: " + email));
    }
}
