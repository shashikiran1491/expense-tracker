package com.java.expense.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Data
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue
    private Long id;
    private String email;
    private String password;

    @Column(insertable = false, updatable = false)
    private LocalDateTime created;

    @Column(insertable = false, updatable = false)
    private LocalDateTime updated;

    private LocalDateTime lastLogin;
    private boolean enabled;
}
