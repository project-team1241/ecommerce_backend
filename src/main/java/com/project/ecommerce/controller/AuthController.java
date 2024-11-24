package com.project.ecommerce.controller;

import com.project.ecommerce.config.JwtUtils;
import com.project.ecommerce.model.User;
import com.project.ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user) {
        User createdUser = userService.createUser(user);
        return ResponseEntity.ok(createdUser);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User loginRequest) {
        return userService.getUserByEmail(loginRequest.getUserEmailId())
                .filter(user -> passwordEncoder.matches(loginRequest.getUserPassword(), user.getUserPassword()))
                .map(user -> {
                    String token = jwtUtils.generateToken(user.getUserEmailId());
                    return ResponseEntity.ok(token);
                })
                .orElse(ResponseEntity.status(401).body("Invalid email or password"));
    }

    @GetMapping("/validate")
    public ResponseEntity<String> validateToken(@RequestParam String token) {
        if (jwtUtils.validateToken(token)) {
            return ResponseEntity.ok("Token is valid");
        }
        return ResponseEntity.status(401).body("Invalid or expired token");
    }
}
