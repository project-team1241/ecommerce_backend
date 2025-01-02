package com.project.ecommerce.service;

import com.project.ecommerce.model.Message;
import com.project.ecommerce.model.Users;
import com.project.ecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JWTService jwtService;

    @Autowired
    private AuthenticationManager authManager;

    // Get all users
    public List<Users> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<Users> getUserByEmailId(String emailId) {
        return userRepository.findByUserEmailId(emailId);
    }

    public Users getUserByUserName(String userName) {
        return  userRepository.findByUserName(userName);
    }

    // Create or Update user
    public Users saveUser(Users user) {
        return userRepository.save(user);
    }

    // Delete user
    public void deleteUser(String emailId) {
        userRepository.deleteByUserEmailId(emailId);
    }

    public ResponseEntity<Object> verify(Users user) {
        try {
            Authentication authentication = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUserName(), user.getUserPassword()));
            if (authentication.isAuthenticated()) {
                String token = jwtService.generateToken(user.getUserName());
                return ResponseEntity
                        .status(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(Message.getTokenMsg(token));
            }
        } catch (BadCredentialsException e) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(Message.getErrorMsg("Wrong username or password"));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(Message.getErrorMsg("An error occurred"));
        }
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Message.getErrorMsg("Wrong username or password"));
    }

    public Users getUserById(int userId) {
        return null;
    }
}

