package com.project.ecommerce.service;

import com.project.ecommerce.model.Order;
import com.project.ecommerce.model.OrderRequest;
import com.project.ecommerce.model.Product;
import com.project.ecommerce.model.Users;
import com.project.ecommerce.repository.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserService userService;

    @Transactional
    public ResponseEntity<Object> placeOrder(OrderRequest orderRequest) {
        // Fetch the product
        Optional<Product> productOpt = productService.getProductById(orderRequest.getProductId());

        Product product = productOpt.orElse(null);

        // Check if there is enough stock
        assert product != null;
        if (product.getProductQuantity() < orderRequest.getQuantity()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Insufficient stock for this product.");
        }

        // Reduce the stock
        productService.updateProductQuantity(orderRequest.getProductId(), -orderRequest.getQuantity());

        // Create the order
        Users user = userService.getUserById(orderRequest.getUserId());
        Order order = new Order();
        order.setUser(user);
        order.setProduct(product);
        order.setQuantity(orderRequest.getQuantity());

        // Save the order
        orderRepository.save(order);

        return ResponseEntity.status(HttpStatus.CREATED).body("Order placed successfully.");
    }
}
