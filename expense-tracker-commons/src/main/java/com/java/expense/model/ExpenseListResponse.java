package com.java.expense.model;

import lombok.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExpenseListResponse {
    private List<ExpenseResponse> expenses;
    private int page;
    private int pageSize;
    private long totalElements;
    private int totalPages;
}
