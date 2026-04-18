package com.core;

import java.math.BigDecimal;

public record ProductRequestDto(
        String name,
        String description,
        String category,
        BigDecimal price,
        String currency,
        String availability
) {
}
