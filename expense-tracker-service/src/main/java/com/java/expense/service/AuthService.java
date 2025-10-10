package com.java.expense.service;

import com.java.expense.entity.User;
import com.java.expense.exception.UserRegistrationException;
import com.java.expense.model.UserRequest;
import com.java.expense.repository.AuthRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class AuthService {

    private final AuthRepository authRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public void register(UserRequest userRequest) {
        boolean userExists = authRepository.existsByEmail(userRequest.getEmail());

        if (userExists) {
            throw new UserRegistrationException("User with given email already exists");
        } else {
            registerUser(userRequest);
        }
    }

    private void registerUser(UserRequest userRequest) {
        User user = User.builder()
                .email(userRequest.getEmail())
                .password(passwordEncoder.encode(userRequest.getPassword()))
                .enabled(true)
                .build();
        authRepository.save(user);
    }
}
