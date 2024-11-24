package com.project.ecommerce.service;

import com.project.ecommerce.model.User;
import com.project.ecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByUserEmailId(email); // Query by email ID
    }

    public User createUser(User user) {
        user.setUserPassword(passwordEncoder.encode(user.getUserPassword()));
        return userRepository.save(user);
    }

    public User updateUserByEmail(String email, User updatedUser) {
        return userRepository.findByUserEmailId(email)
                .map(existingUser -> {
                    existingUser.setUserName(updatedUser.getUsername());
                    existingUser.setUserPassword(passwordEncoder.encode(updatedUser.getUserPassword()));
                    return userRepository.save(existingUser);
                })
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
    }

    public void deleteUserByEmail(String email) {
        userRepository.findByUserEmailId(email)
                .ifPresentOrElse(userRepository::delete, () -> {
                    throw new RuntimeException("User not found with email: " + email);
                });
    }
}
