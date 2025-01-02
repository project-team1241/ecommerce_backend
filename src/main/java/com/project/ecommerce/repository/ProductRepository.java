package com.project.ecommerce.repository;

import com.project.ecommerce.model.Product;
import com.project.ecommerce.model.Users;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@EnableJpaRepositories
public interface ProductRepository extends JpaRepository<Product, Integer> {

    @Query("SELECT p FROM Product p WHERE " +
            "(COALESCE(:category, '') = '' OR LOWER(p.productCategory) = LOWER(:category)) AND " +
            "(COALESCE(:subCategory, '') = '' OR LOWER(p.productSubCategory) = LOWER(:subCategory))")
    List<Product> findByCategoryAndSubCategory(
            @Param("category") String category,
            @Param("subCategory") String subCategory,
            Pageable pageable);

    @Query("SELECT p FROM Product p WHERE " +
            "(COALESCE(:category, '') = '' OR LOWER(p.productCategory) = LOWER(:category)) AND " +
            "(COALESCE(:subCategory, '') = '' OR LOWER(p.productSubCategory) = LOWER(:subCategory)) AND " +
            "(:seller IS NULL OR p.seller = :seller)")
    List<Product> findByCategoryAndSubCategoryAndSeller(
            @Param("category") String category,
            @Param("subCategory") String subCategory,
            @Param("seller") Users seller,
            Pageable pageable);

    @Query("SELECT p FROM Product p WHERE p.seller.userEmailId = :sellerEmail")
    List<Product> findBySellerEmail(@Param("sellerEmail") String sellerEmail);

    // Custom query to reduce product quantity when an order is placed
    @Modifying
    @Query("UPDATE Product p SET p.productQuantity = p.productQuantity - :quantity WHERE p.productId = :productId")
    void updateProductQuantity(@Param("productId") int productId, @Param("quantity") long quantity);

    // Find product by id
    Optional<Product> findById(int productId);
}