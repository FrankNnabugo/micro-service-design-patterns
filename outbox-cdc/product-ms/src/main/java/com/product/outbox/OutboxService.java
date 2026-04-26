package com.product.outbox;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.product.product.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OutboxService {
    private final OutboxEventRepository outboxEventRepository;
    private final ObjectMapper objectMapper;

    public void createOutBox(Product product){
        OutboxEvent event = new OutboxEvent();
        event.setAggregateId(product.getId());
        event.setAggregateType("Product");
        event.setEventType("PRODUCT_CREATED");
        try {
            event.setPayload(objectMapper.writeValueAsString(product));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        outboxEventRepository.save(event);
    }
}
