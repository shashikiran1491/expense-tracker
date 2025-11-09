package com.java.expense.repository;

import com.java.expense.entity.Expense;
import com.java.expense.entity.User;
import com.java.expense.model.CategorySummary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long>, JpaSpecificationExecutor<Expense> {

    Page<Expense> findByUserAndExpenseDateBetween(User user, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);

    Page<Expense> findByUserAndCategoryAndExpenseDateBetween(User user, String category, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);

    Optional<Expense> findExpenseByUserAndId(User user, Long id);

    @Query("SELECT COALESCE(SUM(e.amount), 0) FROM Expense e WHERE e.user = :user AND e.expenseType = :type AND e.expenseDate BETWEEN :startDate AND :endDate")
    double sumByTypeAndDateRange(@Param("user") User user,
                                 @Param("type") String type,
                                 @Param("startDate") LocalDateTime startDate,
                                 @Param("endDate") LocalDateTime endDate);


    @Query("SELECT new com.java.expense.model.CategorySummary(e.category, SUM(e.amount)) " +
            "FROM Expense e " +
            "WHERE e.user = :user AND e.expenseType = 'Expense'" +
            "AND e.expenseDate BETWEEN :startDate AND :endDate " +
            "GROUP BY e.category")
    List<CategorySummary> findCategoryWiseSummary(@Param("user") User user,
                                                  @Param("startDate") LocalDateTime startDate,
                                                  @Param("endDate") LocalDateTime endDate);


}
