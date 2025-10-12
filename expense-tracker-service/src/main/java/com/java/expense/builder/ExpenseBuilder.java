package com.java.expense.builder;

import com.java.expense.entity.Expense;
import com.java.expense.model.expense.ExpenseListResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public class ExpenseBuilder {
    public static com.java.expense.model.expense.ExpenseResponse buildExpense(Expense expense) {
        return com.java.expense.model.expense.ExpenseResponse.builder()
                .id(expense.getId())
                .amount(expense.getAmount())
                .expenseDate(expense.getExpenseDate())
                .expenseType(expense.getExpenseType())
                .category(expense.getCategory())
                .description(expense.getDescription())
                .build();
    }

    public static ExpenseListResponse buildExpenseResponse(List<com.java.expense.model.expense.ExpenseResponse> expenseList, Page<Expense> expensePage) {
        return ExpenseListResponse.builder().expenses(expenseList)
                .page(expensePage.getNumber())
                .pageSize(expensePage.getSize())
                .totalElements(expensePage.getTotalElements())
                .totalPages(expensePage.getTotalPages())
                .build();
    }
}
