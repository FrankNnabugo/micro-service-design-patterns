package com.command.event;

import com.core.ProductCreatedEvent;
import com.core.avro.ProductEventAvro;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.Conversions;
import org.apache.avro.LogicalTypes;
import org.apache.avro.Schema;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.nio.ByteBuffer;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductPublisher {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Value("${spring.kafka.topics.product}")
    private String topic;

    private ProductEventAvro mapToAvro(ProductCreatedEvent productCreatedEvent){
        BigDecimal price = productCreatedEvent.price();
        Schema.Field field = ProductEventAvro.getClassSchema().getField("price");
        LogicalTypes.Decimal decimalType = (LogicalTypes.Decimal) field.schema().getLogicalType();
        Conversions.DecimalConversion conversion = new Conversions.DecimalConversion();
        ByteBuffer avroPrice = conversion.toBytes(
                price,
                field.schema(),
                decimalType
        );

        ProductEventAvro productAvro = new ProductEventAvro();
        productAvro.setId(productCreatedEvent.id());
        productAvro.setName(productCreatedEvent.name());
        productAvro.setCategory(productCreatedEvent.category());
        productAvro.setDescription(productCreatedEvent.description());
        productAvro.setCurrency(productCreatedEvent.currency());
        productAvro.setPrice(avroPrice);
        productAvro.setAvailability(productCreatedEvent.availability());
        productAvro.setCreatedAt(productCreatedEvent.createdAt());
        productAvro.setUpdatedAt(productCreatedEvent.updatedAt());
        return productAvro;
    }

    public void publishProductCreatedEvent(ProductCreatedEvent event){

        ProductEventAvro product = mapToAvro(event);
        kafkaTemplate.send(topic, event.id(), product)
                .whenComplete((metaData, throwable)->{
                    if(throwable != null){
                        log.error("error publishing product created event", throwable);
                        throw new RuntimeException(throwable);
                    }
                    log.info("product event ({}) published to {} - {} - {}",
                            metaData.getProducerRecord().value(),
                            metaData.getRecordMetadata().topic(),
                            metaData.getRecordMetadata().partition(),
                            metaData.getRecordMetadata().offset());
                });

    }
}
