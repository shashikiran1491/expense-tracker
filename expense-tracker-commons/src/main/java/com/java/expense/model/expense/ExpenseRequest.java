package com.java.expense.model.expense;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExpenseRequest {

    @NotNull(message = "Amount is required")
    @Positive(message = "Amount must be greater than zero")
    private Double amount;

    @NotBlank(message = "Expense type is required")
    @Pattern(regexp = "INCOME|EXPENSE", message = "Expense type must be INCOME or EXPENSE")
    private String expenseType;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime expenseDate;

    @NotBlank(message = "Category is required")
    private String category;

    @Size(max = 500, message = "Description cannot exceed 500 characters")
    private String description;
}
