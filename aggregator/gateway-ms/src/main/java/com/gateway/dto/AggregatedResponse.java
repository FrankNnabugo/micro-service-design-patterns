package com.gateway.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AggregatedResponse {
    private OrderResponse orderResponse;
    private PaymentResponse paymentResponse;
    private ShippingResponse shippingResponse;
}
