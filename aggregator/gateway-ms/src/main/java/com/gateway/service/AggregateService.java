package com.gateway.service;

import com.gateway.client.ClientAggregate;
import com.gateway.dto.AggregatedResponse;
import com.gateway.dto.OrderResponse;
import com.gateway.dto.PaymentResponse;
import com.gateway.dto.ShippingResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class AggregateService {
    private final ClientAggregate clientAggregate;

    public Mono<AggregatedResponse> aggregate(String orderId, String paymentId, String shippingId) {
        Mono<OrderResponse> orderMono = clientAggregate.getOrder(orderId);
        Mono<PaymentResponse> paymentMono = clientAggregate.getPayment(paymentId);
        Mono<ShippingResponse> shippingMono = clientAggregate.getShipping(shippingId);
        return Mono.zip(orderMono, paymentMono, shippingMono)
                .map(response -> {
                    OrderResponse order = response.getT1();
                    PaymentResponse payment = response.getT2();
                    ShippingResponse shipping = response.getT3();

                    return new AggregatedResponse(order, payment, shipping);
                });
    }
}
