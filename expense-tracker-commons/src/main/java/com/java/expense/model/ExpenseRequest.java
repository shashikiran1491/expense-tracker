package com.java.expense.model;

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

    private String paidTo;

    @NotBlank(message = "Expense type is required")
    @Pattern(regexp = "Income|Expense", message = "Expense type must be Income or Expense")
    private String expenseType;

    //TODO : Change this because LocalDateTIme should not have zone details. Use OffsetDateTime or ZonedDateTime if zone info is needed.
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSX")
    private LocalDateTime expenseDate;

    @NotBlank(message = "Category is required")
    private String category;

    @Size(max = 500, message = "Description cannot exceed 500 characters")
    private String description;
}
