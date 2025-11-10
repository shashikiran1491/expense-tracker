package com.java.expense.model;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InsightsResponse {
    private double averageDailySpending;
    private double projectedMonthlyTotal;
    private long noOfDaysRemaining;
    private HighestCategory highestCategory;
}
