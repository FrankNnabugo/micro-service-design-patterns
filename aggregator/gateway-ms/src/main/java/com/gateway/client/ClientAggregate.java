package com.gateway.client;

import com.gateway.config.WebClientConfig;
import com.gateway.dto.OrderResponse;
import com.gateway.dto.PaymentResponse;
import com.gateway.dto.ShippingResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class ClientAggregate {
    private final WebClientConfig webClientConfig;

    public Mono<OrderResponse> getOrder(String orderId) {
        return webClientConfig.webClient()
                .get()
                .uri("http://localhost:9000/api/order/{orderId}", orderId)
                .retrieve()
                .bodyToMono(OrderResponse.class)
                .onErrorResume(ex -> Mono.just(new OrderResponse(null, "unavailable", null, null)));
    }

    public Mono<PaymentResponse> getPayment(String paymentId){
        return webClientConfig.webClient()
                .get()
                .uri("http://localhost:9091/api/payment/{paymentId}", paymentId)
                .retrieve()
                .bodyToMono(PaymentResponse.class)
                .onErrorResume(ex -> Mono.just(new PaymentResponse(null, "unavailable", null, null)));
    }

    public Mono<ShippingResponse> getShipping(String shippingId){
        return webClientConfig.webClient()
                .get()
                .uri("http://localhost:9092/api/shipping/{shippingId}", shippingId)
                .retrieve()
                .bodyToMono(ShippingResponse.class)
                .onErrorResume(ex -> Mono.just(new ShippingResponse(null, "unavailable", null, null)));
    }

}
