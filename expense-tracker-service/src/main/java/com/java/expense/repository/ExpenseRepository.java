package com.java.expense.repository;

import com.java.expense.entity.Expense;
import com.java.expense.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    Page<Expense> findByUserAndExpenseDateBetween(User user, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);

    Page<Expense> findByUserAndCategoryAndExpenseDateBetween(User user, String category, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);

    Optional<Expense> findExpenseByUserAndId(User user, Long id);

}
