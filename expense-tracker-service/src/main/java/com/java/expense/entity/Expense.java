package com.java.expense.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "expense")
public class Expense {

    @Id
    @GeneratedValue
    private Long id;

    private Double amount;

    private String expenseType;

    private LocalDateTime expenseDate;

    private String category;

    private String description;

   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "user_id")
   private User user;

   @Column(insertable = false, updatable = false)
   private LocalDateTime created;

   @Column(insertable = false, updatable = false)
   private LocalDateTime updated;
}
