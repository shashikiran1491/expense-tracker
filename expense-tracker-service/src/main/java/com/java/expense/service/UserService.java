package com.java.expense.service;

import com.java.expense.entity.User;
import com.java.expense.exception.UserNotFoundException;
import com.java.expense.repository.AuthRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {

    private final AuthRepository authRepository;

    public User getUser(String email) {
        return authRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("User not found. userId: " + email));
    }

}
