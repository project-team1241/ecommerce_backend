package com.project.ecommerce.controller;

import ch.qos.logback.core.util.StringUtil;
import com.project.ecommerce.model.Product;
import com.project.ecommerce.service.ProductService;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("v1")
public class ProductController {

    @Autowired
    private ProductService productService;


    @GetMapping("/products")
    public ResponseEntity<Object> getProducts(
            @RequestParam Optional<String> category,
            @RequestParam Optional<String> subCategory,
            @RequestParam Optional<Integer> limit) {
        List<Product> productList = new ArrayList<>();
        productList.addAll(productService.getFilteredProducts(category, subCategory, limit));
        return new ResponseEntity<>(productList, HttpStatus.OK);
    }
}
