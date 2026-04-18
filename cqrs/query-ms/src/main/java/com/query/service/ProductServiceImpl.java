package com.query.service;

import com.query.entity.Product;
import com.query.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{
    private final ProductRepository productRepository;
    @Override
    public Product getProduct(String id) {
   return productRepository.findById(id).orElseThrow();
    }

    @Override
    public List<Product> getAllProducts() {
      return productRepository.findAll();
    }
}
