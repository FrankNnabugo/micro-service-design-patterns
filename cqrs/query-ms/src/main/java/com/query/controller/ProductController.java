package com.query.controller;

import com.query.entity.Product;
import com.query.service.ProductServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/products/read")
@RequiredArgsConstructor
public class ProductController {
    private final ProductServiceImpl productServiceImpl;

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable("id")String id){
        Product product = productServiceImpl.getProduct(id);
        return ResponseEntity.ok(product);
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts(){
        List<Product> product = productServiceImpl.getAllProducts();
        return ResponseEntity.ok(product);
    }
}
