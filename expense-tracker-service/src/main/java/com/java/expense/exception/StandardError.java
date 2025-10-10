package com.java.expense.exception;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Data
public class StandardError {
    private String status;
    private LocalDateTime timestamp;
    private List<String> errors;
}
