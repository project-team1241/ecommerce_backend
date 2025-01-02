package com.project.ecommerce.service;

import com.project.ecommerce.model.Message;
import com.project.ecommerce.model.Users;
import com.project.ecommerce.model.enums.SellerRequestStatus;
import com.project.ecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserRoleService {

    @Autowired
    private UserRepository userRepository;

    // Request Seller Access
    public ResponseEntity<Object> requestSellerAccess(String email) {
        Optional<Users> user = userRepository.findByUserEmailId(email);

        if (user.isPresent()) {
            Users existingUser = user.get();
            // Check if the user has already requested seller access
            if (existingUser.getSellerRequestStatus() == SellerRequestStatus.PENDING || existingUser.getSellerRequestStatus() == SellerRequestStatus.REQUEST) {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body("Seller access request is already in progress.");
            }
            existingUser.setSellerRequestStatus(SellerRequestStatus.REQUEST);
            userRepository.save(existingUser);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body("Seller access request has been submitted.");
        } else {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("User not found.");
        }
    }

    // Approve Seller Access
    public ResponseEntity<Object> approveSellerRequest(String email) {
        Optional<Users> user = userRepository.findByUserEmailId(email);

        if (user.isPresent()) {
            Users existingUser = user.get();
            // Only approve if the request is pending
            if (existingUser.getSellerRequestStatus() == SellerRequestStatus.REQUEST) {
                existingUser.setSellerRequestStatus(SellerRequestStatus.APPROVED);
                userRepository.save(existingUser);
                return ResponseEntity
                        .status(HttpStatus.OK)
                        .body("Seller access approved.");
            } else {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body("Seller access request is not in a pending state.");
            }
        } else {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("User not found.");
        }
    }

    // Reject Seller Access
    public ResponseEntity<Object> rejectSellerRequest(String email) {
        Optional<Users> user = userRepository.findByUserEmailId(email);

        if (user.isPresent()) {
            Users existingUser = user.get();
            // Only reject if the request is pending
            if (existingUser.getSellerRequestStatus() == SellerRequestStatus.REQUEST) {
                existingUser.setSellerRequestStatus(SellerRequestStatus.REJECTED);
                userRepository.save(existingUser);
                return ResponseEntity
                        .status(HttpStatus.OK)
                        .body("Seller access rejected.");
            } else {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body("Seller access request is not in a pending state.");
            }
        } else {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("User not found.");
        }
    }

    // Get All Pending Seller Requests (For Admin)
    public ResponseEntity<List<Users>> getPendingSellerRequests() {
        List<Users> pendingRequests = userRepository.findBySellerRequestStatus(SellerRequestStatus.REQUEST);
        return ResponseEntity.status(HttpStatus.OK).body(pendingRequests);
    }
}