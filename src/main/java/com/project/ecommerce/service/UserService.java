package com.project.ecommerce.service;

import com.project.ecommerce.model.User;
import com.project.ecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Get all users
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Get user by email id (Primary Key)
    public Optional<User> getUserByEmailId(String emailId) {
        return userRepository.findById(emailId);
    }

    // Create or Update user
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    // Delete user
    public void deleteUser(String emailId) {
        userRepository.deleteById(emailId);
    }
}

