package com.java.expense.utils;

import com.java.expense.exception.InvalidMonthException;
import com.java.expense.exception.InvalidYearException;

import java.time.LocalDate;

public class ValidatorUtils {

    public static void validateStartDateAndEndDate(LocalDate startDate, LocalDate endDate) {
        if(startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("Start date cannot be after end date");
        }
    }

    public static void validateMonthAndYear(int month, int year) {
        if(month < 1 || month > 12) {
            throw new InvalidMonthException("Month is not valid");
        }

        if(year < 2020 || year > 2029) {
            throw new InvalidYearException("Enter year between 2020 and 2029");
        }
    }
}
