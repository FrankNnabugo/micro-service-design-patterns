package com.query.event;


import com.core.ProductCreatedEvent;
import com.core.ProductEventBase;
import com.core.ProductUpdatedEvent;
import com.core.avro.ProductCreatedEventAvro;
import com.core.avro.ProductDeletedEventAvro;
import com.core.avro.ProductUpdatedEventAvro;
import com.query.entity.Product;
import com.query.repository.ProductRepository;
import com.query.utility.EventMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;


@Service
@KafkaListener(
        topics = "${spring.kafka.topics.product}",
        groupId = "query-ms")
@RequiredArgsConstructor
public class ProductConsumer {
    private final ProductRepository productRepository;
    private final EventMapper eventMapper;

    @KafkaHandler
    public void consumeProductCreatedEvent(@Payload ProductCreatedEventAvro avro, Acknowledgment ack){
        ProductCreatedEvent event = eventMapper.toCreatedEvent(avro);
        Product product = mapToEntity(event);
        productRepository.save(product);
        ack.acknowledge();
    }

    @KafkaHandler
    public void consumeProductUpdatedEvent(@Payload ProductUpdatedEventAvro avro, Acknowledgment ack){
        ProductUpdatedEvent event = eventMapper.toUpdatedEvent(avro);
        Product product = mapToEntity(event);
        productRepository.save(product);
        ack.acknowledge();
    }
    @KafkaHandler
    public void consumeProductDeletedEvent(@Payload ProductDeletedEventAvro avro, Acknowledgment ack){
        Product product = productRepository.findById(avro.getId().toString()).orElseThrow();
        productRepository.delete(product);
        ack.acknowledge();
    }

    private Product mapToEntity(ProductEventBase event){
        Product product = new Product();
        product.setId(event.id());
        product.setName(event.name());
        product.setDescription(event.description());
        product.setCategory(event.category());
        product.setPrice(event.price());
        product.setCurrency(event.currency());
        product.setAvailability(event.availability());
        product.setCreatedAt(event.createdAt());
        product.setUpdatedAt(event.updatedAt());
        return product;
    }
}
