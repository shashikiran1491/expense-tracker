package com.java.expense.model.expense;

import lombok.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExpenseResponse {
    private List<Expense> expenses;
    private int page;
    private int pageSize;
    private long totalElements;
    private int totalPages;
}
