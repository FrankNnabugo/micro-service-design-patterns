package com.query.event;


import com.core.ProductCreatedEvent;
import com.core.ProductDeletedEvent;
import com.core.avro.ProductEventAvro;
import com.query.entity.Product;
import com.query.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.apache.avro.Conversions;
import org.apache.avro.LogicalTypes;
import org.apache.avro.Schema;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@KafkaListener(
        topics = "${spring.kafka.topics.product}",
        groupId = "query-ms")
@RequiredArgsConstructor
public class ProductConsumer {
    private final ProductRepository productRepository;

    @KafkaHandler
    public void consumeProductCreatedEvent(@Payload ProductEventAvro avro, Acknowledgment ack){
        ProductCreatedEvent event = mapToEvent(avro);
        Product product = mapToEntity(event);
        productRepository.save(product);
        ack.acknowledge();
    }

    @KafkaHandler
    public void consumeProductUpdatedEvent(@Payload ProductEventAvro avro, Acknowledgment ack){
        ProductCreatedEvent event = mapToEvent(avro);
        Product product = mapToEntity(event);
        productRepository.save(product);
        ack.acknowledge();
    }
    @KafkaHandler
    public void consumeProductDeletedEvent(@Payload ProductDeletedEvent event, Acknowledgment ack){
        Product product = productRepository.findById(event.id()).orElseThrow();
        productRepository.delete(product);
    }

    private ProductCreatedEvent mapToEvent(ProductEventAvro avro) {
        Conversions.DecimalConversion conversion = new Conversions.DecimalConversion();

        Schema.Field field = ProductEventAvro.getClassSchema().getField("price");
        LogicalTypes.Decimal decimalType =
                (LogicalTypes.Decimal) field.schema().getLogicalType();

        BigDecimal price = conversion.fromBytes(
                avro.getPrice(),
                field.schema(),
                decimalType
        );
        return new ProductCreatedEvent(
                avro.getId().toString(),
                avro.getName().toString(),
                avro.getDescription().toString(),
                avro.getCategory().toString(),
                price,
                avro.getCurrency().toString(),
                avro.getAvailability().toString(),
                avro.getCreatedAt(),
                avro.getUpdatedAt()
        );
    }

    private Product mapToEntity(ProductCreatedEvent event){
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
