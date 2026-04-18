package com.command.controller;

import com.command.entity.Product;
import com.command.service.ProductServiceImpl;
import com.core.ProductRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products/write")
@RequiredArgsConstructor
public class ProductController {
    private final ProductServiceImpl productServiceImpl;

    @PostMapping()
    public ResponseEntity<Product> createProduct(@RequestBody ProductRequestDto requestDto){
        Product product = productServiceImpl.createProduct(requestDto);
        return ResponseEntity.ok(product);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable("id") String id, @RequestBody ProductRequestDto requestDto){
        Product product = productServiceImpl.updateProduct(id, requestDto);
        return ResponseEntity.ok(product);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable("id") String id){
        String product = productServiceImpl.deleteProduct(id);
        return ResponseEntity.ok(product);

    }
}
