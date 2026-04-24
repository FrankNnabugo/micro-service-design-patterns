package com.core;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderEvent {
    private String id;
    private String item;
    private String shippingAddress;
    private OrderStatus orderStatus;
    private Instant createdAt;
}
