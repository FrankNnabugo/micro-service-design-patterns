package com.core;

import java.math.BigDecimal;
import java.time.Instant;

public record ProductUpdatedEvent(
        String id,
        String name,
        String description,
        String category,
        BigDecimal price,
        String currency,
        String availability,
        Instant createdAt,
        Instant updatedAt
) {
}
