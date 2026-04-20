package com.gateway.controller;

import com.gateway.dto.AggregatedResponse;
import com.gateway.service.AggregateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/gateway")
@RequiredArgsConstructor
public class AggregateController {
    private final AggregateService aggregateService;

    @GetMapping("/aggregate")
    public ResponseEntity<Mono<AggregatedResponse>> getAggregate(
            @RequestParam String orderId,
            @RequestParam String paymentId,
            @RequestParam String shippingId){
        Mono<AggregatedResponse> aggregatedResponse = aggregateService.aggregate(orderId, paymentId, shippingId);
        return new ResponseEntity<>(aggregatedResponse, HttpStatus.OK);
    }
}
