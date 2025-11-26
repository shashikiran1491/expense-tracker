package com.java.expense.entity;

import com.java.expense.enums.LoginProvider;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

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

    @Column(nullable = false, unique = true)
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String pictureUrl;

    @Column(insertable = false, updatable = false)
    private LocalDateTime created;

    @Column(insertable = false, updatable = false)
    private LocalDateTime updated;

    private LocalDateTime lastLogin;

    private boolean enabled;

    @Enumerated(EnumType.STRING)
    private LoginProvider loginProvider;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Expense> expense;
}
