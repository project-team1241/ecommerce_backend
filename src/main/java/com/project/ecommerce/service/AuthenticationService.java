package com.project.ecommerce.service;

import com.project.ecommerce.model.*;
import com.project.ecommerce.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    public AuthenticationService(
            UserRepository userRepository,
            AuthenticationManager authenticationManager,
            PasswordEncoder passwordEncoder
    ) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User signup(User input) throws Exception {
        User user = new User();
        if (input != null) {

            user.setUserName(input.getUsername());
            user.setUserEmailId(input.getUserEmailId());
            user.setUserPassword(passwordEncoder.encode(input.getPassword()));

            return userRepository.save(user);
        }
        else {
            throw new Exception("Something went wrong");
        }
    }

    public User authenticate(LoginUserDto input) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getEmail(),
                        input.getPassword()
                )
        );

        return userRepository.findByUserEmailId(input.getEmail())
                .orElseThrow();
    }
}