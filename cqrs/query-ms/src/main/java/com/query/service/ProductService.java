package com.query.service;

import com.query.entity.Product;

import java.util.List;

public interface ProductService {
    Product getProduct(String id);

    List<Product> getAllProducts();
}
