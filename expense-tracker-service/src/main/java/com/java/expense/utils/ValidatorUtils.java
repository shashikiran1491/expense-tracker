package com.java.expense.utils;

import java.time.LocalDate;

public class ValidatorUtils {

    public static void validateStartDateAndEndDate(LocalDate startDate, LocalDate endDate) {
        if(startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("Start date cannot be after end date");
        }
    }
}
