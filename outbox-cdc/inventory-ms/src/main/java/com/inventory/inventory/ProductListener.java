package com.inventory.inventory;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductListener {
private final InventoryRepository inventoryRepository;
    @KafkaListener(topics = "${spring.kafka.topics.product}",
            groupId = "inventory-ms")
    public void consumeProductCreatedEvent(@Payload ProductEvent event, Acknowledgment ack){
        if(alreadyProcessed(event.getId())) return;
        inventoryRepository.save(mapToInventory(event));
        ack.acknowledge();

    }

    private Inventory mapToInventory(ProductEvent event){
        Inventory inventory = new Inventory();
        inventory.setId(event.getId());
        inventory.setName(event.getName());
        inventory.setPrice(event.getPrice());
        inventory.setCreatedAt(event.getCreatedAt());
        inventory.setUpdatedAt(event.getUpdatedAt());
        return inventory;
    }

    private boolean alreadyProcessed(String id){
        inventoryRepository.findById(id).orElseThrow();
        return false;
    }
}
