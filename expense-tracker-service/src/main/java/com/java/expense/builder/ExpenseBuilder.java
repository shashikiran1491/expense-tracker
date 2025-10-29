package com.java.expense.builder;

import com.java.expense.entity.Expense;
import com.java.expense.model.ExpenseListResponse;
import com.java.expense.model.ExpenseResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public class ExpenseBuilder {
    public static ExpenseResponse buildExpense(Expense expense) {
        return ExpenseResponse.builder()
                .id(expense.getId())
                .paidTo(expense.getPaidTo())
                .amount(expense.getAmount())
                .expenseDate(expense.getExpenseDate())
                .expenseType(expense.getExpenseType())
                .category(expense.getCategory())
                .description(expense.getDescription())
                .build();
    }

    public static ExpenseListResponse buildExpenseResponse(List<ExpenseResponse> expenseList, Page<Expense> expensePage) {
        return ExpenseListResponse.builder().expenses(expenseList)
                .page(expensePage.getNumber())
                .pageSize(expensePage.getSize())
                .totalElements(expensePage.getTotalElements())
                .totalPages(expensePage.getTotalPages())
                .build();
    }
}
