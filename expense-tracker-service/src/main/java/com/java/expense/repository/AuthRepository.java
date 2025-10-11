package com.java.expense.repository;

import com.java.expense.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthRepository extends JpaRepository<com.java.expense.entity.User, Long> {

    boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);
}
