package com.project.ecommerce.repository;


import com.project.ecommerce.model.Users;
import com.project.ecommerce.model.enums.SellerRequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users, Integer> {
    Optional<Users> findByUserEmailId(String userEmailId);

    Users findByUserName(String userName);

    void deleteByUserEmailId(String emailId);

    List<Users> findBySellerRequestStatus(SellerRequestStatus sellerRequestStatus);
}