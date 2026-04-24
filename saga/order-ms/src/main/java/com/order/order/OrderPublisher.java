package com.order.order;

import com.core.OrderEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderPublisher {
    private final KafkaTemplate<String, Object>kafkaTemplate;
    @Value("${spring.kafka.topics.order}")
    private String topic;

    public void publishOrderCreatedEvent(OrderEvent event){
        kafkaTemplate.send(topic, event.getId(), event)
                .whenComplete((metaData, throwable)->{
                    if(throwable != null ){
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
