package com.payment.service;

import com.payment.dto.PaymentRequest;
import com.payment.dto.PaymentResponse;
import com.payment.entity.Payment;
import com.payment.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
   private final PaymentRepository paymentRepository;
   private final ModelMapper modelMapper;

   @Override
   public PaymentResponse createPayment(PaymentRequest request){
       Payment payment = new Payment();
       payment.setStatus(request.getStatus());
       payment.setPaymentMethod(request.getPaymentMethod());
       paymentRepository.save(payment);
       return modelMapper.map(payment, PaymentResponse.class);
   }

   @Override
   public PaymentResponse getPayment(String id){
       Payment payment = paymentRepository.findById(id).orElseThrow();
       return modelMapper.map(payment, PaymentResponse.class);
   }
}
