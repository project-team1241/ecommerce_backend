package com.project.ecommerce.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "product_table")
public class Product {
    @Id
    @Column(name = "product_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int productId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false) // Foreign key to Users table
    @JsonBackReference // Break the circular reference here
    private Users seller;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "product_Brand")
    private String productBrand;

    @Column(name = "product_Desc")
    private String productDesc;

    @Column(name = "product_category")
    private String productCategory;

    @Column(name = "product_sub_category")
    private String productSubCategory;

    @Column(name = "product_price")
    private String productPrice;

    @Column(name = "product_discount")
    private String productDiscount;

    @Column(name = "product_img")
    private String productImg;

    @Column(name = "product_thumb_img")
    private String productThumbImg;

    @Column(name = "product_rating")
    private String productRating;

    @Column(name = "product_quantity")
    private long productQuantity;
}
