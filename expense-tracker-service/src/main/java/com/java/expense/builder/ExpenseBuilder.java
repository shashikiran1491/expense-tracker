package com.java.expense.builder;

import com.java.expense.entity.Expense;
import com.java.expense.model.expense.ExpenseResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public class ExpenseBuilder {
    public static com.java.expense.model.expense.Expense buildExpense(Expense expense) {
        return com.java.expense.model.expense.Expense.builder()
                .id(expense.getId())
                .amount(expense.getAmount())
                .expenseDate(expense.getExpenseDate())
                .expenseType(expense.getExpenseType())
                .category(expense.getCategory())
                .description(expense.getDescription())
                .build();
    }

    public static ExpenseResponse buildExpenseResponse(List<com.java.expense.model.expense.Expense> expenseList, Page<Expense> expensePage) {
        return ExpenseResponse.builder().expenses(expenseList)
                .page(expensePage.getNumber())
                .pageSize(expensePage.getSize())
                .totalElements(expensePage.getTotalElements())
                .totalPages(expensePage.getTotalPages())
                .build();
    }
}
