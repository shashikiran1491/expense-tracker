package com.java.expense.model;


import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HighestCategory {

    private String category;
    private double amount;
}
