package com.project.ecommerce.controller;

import com.project.ecommerce.model.User;
import com.project.ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    // Get all users
    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    // Get user by email ID
    @GetMapping("/{emailId}")
    public ResponseEntity<User> getUserByEmailId(@PathVariable String emailId) {
        Optional<User> user = userService.getUserByEmailId(emailId);
        return user.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Create a new user
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User savedUser = userService.saveUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }

    // Update an existing user
    @PutMapping("/{emailId}")
    public ResponseEntity<User> updateUser(@PathVariable String emailId, @RequestBody User user) {
        Optional<User> existingUser = userService.getUserByEmailId(emailId);
        if (existingUser.isPresent()) {
            user.setUserEmailId(emailId); // Ensure the emailId doesn't change
            User updatedUser = userService.saveUser(user);
            return ResponseEntity.ok(updatedUser);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete a user
    @DeleteMapping("/{emailId}")
    public ResponseEntity<Void> deleteUser(@PathVariable String emailId) {
        Optional<User> existingUser = userService.getUserByEmailId(emailId);
        if (existingUser.isPresent()) {
            userService.deleteUser(emailId);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
