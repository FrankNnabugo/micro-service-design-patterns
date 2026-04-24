package com.core;

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
public class PaymentEvent{
    private String id;
    private String orderId;
    private String paymentMethod;
    private BigDecimal amount;
    private PaymentStatus paymentStatus;
    private Instant createdAt;
}
