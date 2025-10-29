package com.java.expense.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExpenseResponse {
    private long id;
    private Double amount;
    private String paidTo;
    private String expenseType;
    private LocalDateTime expenseDate;
    private String category;
    private String description;
}
