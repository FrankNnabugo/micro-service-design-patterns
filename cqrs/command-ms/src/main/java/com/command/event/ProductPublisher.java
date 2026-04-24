package com.command.event;

import com.command.utility.AvroMapper;
import com.core.ProductCreatedEvent;
import com.core.ProductDeletedEvent;
import com.core.ProductEventBase;
import com.core.ProductUpdatedEvent;
import com.core.avro.ProductCreatedEventAvro;
import com.core.avro.ProductDeletedEventAvro;
import com.core.avro.ProductUpdatedEventAvro;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductPublisher {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Value("${spring.kafka.topics.product}")
    private String topic;

    private final AvroMapper avroMapper;

    public void publishProductCreatedEvent(ProductCreatedEvent event){

        ProductCreatedEventAvro avro = avroMapper.toCreatedAvro(event);
        kafkaTemplate.send(topic, event.id(), avro)
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

    public void publishProductUpdatedEvent(ProductUpdatedEvent event){
        ProductUpdatedEventAvro avro = avroMapper.toUpdatedAvro(event);
        kafkaTemplate.send(topic, event.id(), avro)
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


    public void publishProductDeletedEvent(ProductDeletedEvent event){
        ProductDeletedEventAvro avro = avroMapper.toDeletedAvro(event);
        kafkaTemplate.send(topic, event.id(), avro)
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
