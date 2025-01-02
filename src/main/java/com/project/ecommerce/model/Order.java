package com.project.ecommerce.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "orders_table")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "order_id")
    private int orderId;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
    @JsonManagedReference
    private Users user;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "product_id", nullable = false)
    @JsonManagedReference
    private Product product;

    @Column(name = "quantity")
    private long quantity;

    @Column(name = "order_date")
    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_status")
    private OrderStatus orderStatus;

    public Order() {
        this.orderDate = LocalDateTime.now();
        this.orderStatus = OrderStatus.PENDING;
    }

    public enum OrderStatus {
        PENDING,
        COMPLETED,
        CANCELED
    }
}
