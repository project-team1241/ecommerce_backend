package com.project.ecommerce.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderRequest {
    private int productId;
    private long quantity;
    private int userId;
}

