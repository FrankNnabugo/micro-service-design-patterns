package com.gateway.client;

import com.gateway.config.WebClient;
import com.gateway.dto.OrderResponse;
import com.gateway.dto.PaymentResponse;
import com.gateway.dto.ShippingResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class ClientAggregate {
    private final WebClient webClient;

    public Mono<OrderResponse> getOrder(String orderId){
        return webClient.webClient()
                .get()
                .uri("http://localhost:9000/api/order/{orderId}", orderId)
                .retrieve()
                .bodyToMono(OrderResponse.class);
    }

    public Mono<PaymentResponse> getPayment(String paymentId){
        return webClient.webClient()
                .get()
                .uri("http://localhost:9091/api/payment/{paymentId}", paymentId)
                .retrieve()
                .bodyToMono(PaymentResponse.class);
    }

    public Mono<ShippingResponse> getShipping(String shippingId){
        return webClient.webClient()
                .get()
                .uri("http://localhost:9092/api/shipping/{shippingId}", shippingId)
                .retrieve()
                .bodyToMono(ShippingResponse.class);
    }

}
