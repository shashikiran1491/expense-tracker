package com.java.expense.repository.specification;

import com.java.expense.entity.Expense;
import com.java.expense.entity.User;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ExpenseSpecification {

    public static Specification<Expense> filterExpenses(
            User user,
            String category,
            String type,
            LocalDateTime startDate,
            LocalDateTime endDate) {

        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Always filter by user
            predicates.add(cb.equal(root.get("user"), user));

            // Optional filters
            if (StringUtils.hasText(category) && !"All Categories".equalsIgnoreCase(category)) {
                predicates.add(cb.equal(root.get("category"), category));
            }

            if (StringUtils.hasText(type) && !"All Types".equalsIgnoreCase(type)) {
                predicates.add(cb.equal(root.get("expenseType"), type));
            }

            // Date range
            predicates.add(cb.between(root.get("expenseDate"), startDate, endDate));

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

}
