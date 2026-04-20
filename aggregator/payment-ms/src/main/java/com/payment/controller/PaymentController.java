package com.payment.controller;

import com.payment.dto.PaymentRequest;
import com.payment.dto.PaymentResponse;
import com.payment.entity.Payment;
import com.payment.service.PaymentServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payment")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentServiceImpl paymentServiceImpl;

    @PostMapping
    public ResponseEntity<PaymentResponse> createPayment(@RequestBody PaymentRequest request){
        PaymentResponse payment = paymentServiceImpl.createPayment(request);
        return new ResponseEntity<>(payment, HttpStatus.OK);
    }
    @GetMapping("/{paymentId}")
    public ResponseEntity<PaymentResponse> getPayment(@PathVariable String paymentId){
        PaymentResponse payment = paymentServiceImpl.getPayment(paymentId);
        return new ResponseEntity<>(payment, HttpStatus.OK);
    }
}
