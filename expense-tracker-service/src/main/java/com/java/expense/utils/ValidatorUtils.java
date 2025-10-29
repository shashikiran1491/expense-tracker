package com.java.expense.utils;

import java.time.LocalDate;

public class ValidatorUtils {

    public static void validateStartDateAndEndDate(LocalDate startDate, LocalDate endDate) {
       /* if(startDate.isAfter(LocalDate.now()) || endDate.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Dates cannot be in the future");
        }*/

        if(startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("Start date cannot be after end date");
        }
    }
}
