package com.project.ecommerce.controller;

import com.project.ecommerce.model.Users;
import com.project.ecommerce.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1")
public class RoleController {

    @Autowired
    private UserRoleService userRoleService;

    // Request Seller Access
    @PostMapping("/request/seller/{email}")
    public ResponseEntity<Object> requestSellerAccess(@PathVariable String email) {
        return userRoleService.requestSellerAccess(email);
    }

    // Approve Seller Access (Admin Only)
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/approve/seller/{email}")
    public ResponseEntity<Object> approveSellerRequest(@PathVariable String email) {
        return userRoleService.approveSellerRequest(email);
    }

    // Reject Seller Access (Admin Only)
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/reject/seller/{email}")
    public ResponseEntity<Object> rejectSellerRequest(@PathVariable String email) {
        return userRoleService.rejectSellerRequest(email);
    }

    // Get All Pending Seller Requests (Admin Only)
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/seller-requests")
    public ResponseEntity<List<Users>> getAllPendingSellerRequests() {
        return userRoleService.getPendingSellerRequests();
    }
}
