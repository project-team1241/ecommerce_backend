package com.project.ecommerce.service;

import com.project.ecommerce.model.Message;
import com.project.ecommerce.model.Users;
import com.project.ecommerce.model.dtos.LoginRequest;
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

    private static final String WUOP = "Wrong username or password";
    private final UserRepository userRepository;
    private final JWTService jwtService;
    private final AuthenticationManager authManager;

    @Autowired
    public UserService(UserRepository userRepository, JWTService jwtService, AuthenticationManager authManager) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.authManager = authManager;
    }

    // Get all users
    public List<Users> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<Users> getUserById(int userId) {
        return userRepository.findById(userId);
    }

    public Optional<Users> getUserByEmailId(String emailId) {
        return userRepository.findByUserEmailId(emailId);
    }

    public Optional<Users> getUserByUserName(String userName) {
        return Optional.ofNullable(userRepository.findByUserName(userName));
    }

    // Create or Update user
    public Users saveUser(LoginRequest loginRequest) {
        Users user = new Users();
        user.setUserName(loginRequest.getUserName());
        user.setUserEmailId(loginRequest.getUserEmailId());
        user.setUserPassword(loginRequest.getUserPassword());
        return userRepository.save(user);
    }

    // Delete user
    public void deleteUser(String emailId) {
        userRepository.deleteByUserEmailId(emailId);
    }

    public ResponseEntity<Object> verify(LoginRequest loginRequest) {
        try {
            Authentication authentication = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUserName(), loginRequest.getUserPassword()
                    )
            );
            if (authentication.isAuthenticated()) {
                String token = jwtService.generateToken(loginRequest.getUserName());
                String userRole = userRepository.findByUserName(loginRequest.getUserName()).getUserRole().toLowerCase();
                return ResponseEntity
                        .status(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(Message.getTokenMsg(token,userRole));
            }
        } catch (BadCredentialsException e) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(Message.getErrorMsg(WUOP));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(Message.getErrorMsg("An error occurred"));
        }
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Message.getErrorMsg(WUOP));
    }

    /**
     * @deprecated
     */
    @Deprecated(since = "1.4", forRemoval = true)
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
                    .body(Message.getErrorMsg(WUOP));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(Message.getErrorMsg("An error occurred"));
        }
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Message.getErrorMsg(WUOP));
    }
}

