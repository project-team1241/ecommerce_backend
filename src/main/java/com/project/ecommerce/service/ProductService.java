package com.project.ecommerce.service;

import com.project.ecommerce.model.Product;
import com.project.ecommerce.model.Users;
import com.project.ecommerce.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Autowired
    private UserService userService;

    public List<Product> getFilteredProducts(Optional<String> category, Optional<String> subCategory, Optional<Integer> limit, Optional<String> sellerReq) {
        int pageSize = 100;

        if (limit.isPresent())
            pageSize = limit.get();
        Pageable pageable = PageRequest.of(0, pageSize);

        String categoryParam = category.orElse("");
        String subCategoryParam = subCategory.orElse("");

        if (sellerReq.isPresent()) {
            Optional<Users> sellerOpt = userService.getUserByEmailId(sellerReq.orElse(""));
            Users seller = sellerOpt.orElseThrow(() -> new RuntimeException("Seller not found"));// user with seller privileges

            return productRepository.findByCategoryAndSubCategoryAndSeller(categoryParam, subCategoryParam, seller, pageable);
        }

        return productRepository.findByCategoryAndSubCategoryAndSeller(categoryParam, subCategoryParam, null, pageable);

    }

    // Method to get products by seller email (for sellers to see their own products)
    public List<Product> getProductsForSeller(String sellerEmail) {
        return productRepository.findBySellerEmail(sellerEmail);
    }

    // Method to update product quantity after an order
    @Transactional
    public void updateProductQuantity(int productId, long quantity) {
        productRepository.updateProductQuantity(productId, quantity);
    }

    // Method to get a product by its ID
    public Optional<Product> getProductById(int productId) {
        return productRepository.findById(productId);
    }

    // Method to create a new product (usually called by the seller)
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    // Method to update an existing product (usually called by the seller)
    public Product updateProduct(Product product) {
        return productRepository.save(product);
    }

    // Method to delete a product (usually called by the seller)
    public void deleteProduct(int productId) {
        productRepository.deleteById(productId);
    }
}
