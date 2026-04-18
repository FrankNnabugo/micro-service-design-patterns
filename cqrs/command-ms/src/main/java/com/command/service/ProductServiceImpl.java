package com.command.service;

import com.command.entity.Product;
import com.command.event.ProductPublisher;
import com.command.repository.ProductRepository;
import com.core.ProductCreatedEvent;
import com.core.ProductRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductPublisher productPublisher;

    @Override
    @Transactional
    public Product createProduct(ProductRequestDto productRequestDto) {
        Product savedProduct;
        savedProduct = productRepository.save(toProduct(productRequestDto));
        ProductCreatedEvent event = mapToEvent(savedProduct);
        productPublisher.publishProductCreatedEvent(event);
        return savedProduct;
    }

    @Override
    @Transactional
    public Product updateProduct(String id, ProductRequestDto productRequestDto) {
        productRepository.findById(id).orElseThrow();
        return productRepository.save(toProduct(productRequestDto));
    }

    @Override
    public String deleteProduct(String id) {
     Product savedProduct = productRepository.findById(id).orElseThrow();
     productRepository.delete(savedProduct);
     return "Product deleted";
    }

    private Product toProduct(ProductRequestDto requestDto){
        Product product =  new Product();
        product.setName(requestDto.name());
        product.setDescription(requestDto.description());
        product.setCategory(requestDto.category());
        product.setPrice(requestDto.price());
        product.setCurrency(requestDto.currency());
        product.setAvailability(requestDto.availability());
        return product;
    }

    private ProductCreatedEvent mapToEvent(Product product) {
        return new ProductCreatedEvent(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getCategory(),
                product.getPrice(),
                product.getCurrency(),
                product.getAvailability(),
                product.getCreatedAt(),
                product.getUpdatedAt()
        );
    }

}
