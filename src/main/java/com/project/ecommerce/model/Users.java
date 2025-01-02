package com.project.ecommerce.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.project.ecommerce.model.enums.SellerRequestStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "users_table")
public class Users {

    @Column(name = "user_email_id")
    private String userEmailId;

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int userId;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "user_password")
    private String userPassword;

    @Column(name = "user_role")
    private String userRole = "USER";

    @Enumerated(EnumType.STRING)
    @Column(name = "seller_request_status")
    private SellerRequestStatus sellerRequestStatus;

    @OneToMany(mappedBy = "seller", cascade = CascadeType.ALL)
    @JsonBackReference // Break circular reference in Products
    private List<Product> products;
}