package com.core;

import java.math.BigDecimal;
import java.time.Instant;

public interface ProductEventBase {
    String id();
    String name();
    String category();
    BigDecimal price();
    String description();
    String currency();
    String availability();
    Instant createdAt();
    Instant updatedAt();
}
