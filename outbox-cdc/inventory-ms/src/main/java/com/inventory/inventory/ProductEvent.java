package com.inventory.inventory;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductEvent {
    private String id;
    private String name;
    private BigDecimal price;
    private Instant createdAt;
    private Instant updatedAt;
}
