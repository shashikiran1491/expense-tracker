package com.java.expense.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
public enum Category {
    FOOD("Food"),
    TRANSPORT("Transport"),
    BILLS("Bills"),
    SHOPPING("Shopping"),
    OTHER("Other");

    private final String displayName;

    Category(String displayName) {
        this.displayName = displayName;
    }

    public static List<String> getAllCategories() {
        return Arrays.stream(values())
                .map(Category::getDisplayName)
                .toList();
    }
}






