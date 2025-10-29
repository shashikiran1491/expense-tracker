package com.java.expense.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SummaryResponse {
    private double totalIncome;
    private double totalExpense;
    private double netBalance;
}
