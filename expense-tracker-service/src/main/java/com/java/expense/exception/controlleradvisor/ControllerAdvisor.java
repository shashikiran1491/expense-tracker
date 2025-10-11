package com.java.expense.exception.controlleradvisor;

import com.java.expense.exception.StandardError;
import com.java.expense.exception.UserLoginException;
import com.java.expense.exception.UserRegistrationException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@ControllerAdvice
public class ControllerAdvisor {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StandardError> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.toList());

        StandardError error = StandardError.builder()
                .status(BAD_REQUEST.name())
                .errors(errors)
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(BAD_REQUEST).body(error);
    }

    @ExceptionHandler({UserRegistrationException.class, UserLoginException.class})
    public ResponseEntity<StandardError> handleUserExceptions(RuntimeException ex) {
        StandardError error = StandardError.builder()
                .status(BAD_REQUEST.name())
                .errors(List.of(ex.getMessage()))
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(BAD_REQUEST).body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<StandardError> handleAll(Exception ex) {
        StandardError error = StandardError.builder()
                .status(INTERNAL_SERVER_ERROR.name())
                .errors(List.of("Unexpected error occurred"))
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(500).body(error);
    }
}
