package com.java.expense.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CategorySummaryResponse {

    private String category;
    private double totalAmount;
    private double percentage;

}
