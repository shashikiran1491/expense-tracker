package com.java.expense.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
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

    private String paidTo;

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
