package com.java.expense.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CategorySummary {

    private String category;
    private double totalAmount;

}
