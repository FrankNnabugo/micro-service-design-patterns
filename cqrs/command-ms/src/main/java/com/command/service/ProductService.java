package com.command.service;

import com.command.entity.Product;
import com.core.ProductRequestDto;

public interface ProductService {

    Product createProduct(ProductRequestDto productRequestDto);

    Product updateProduct(String id, ProductRequestDto productRequestDto);

    String deleteProduct(String id);
}
