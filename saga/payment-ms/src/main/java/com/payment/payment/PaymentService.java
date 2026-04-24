package com.payment.payment;

import com.core.PaymentEvent;
import com.core.PaymentRequest;
import com.core.PaymentStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final PaymentPublisher paymentPublisher;

    public Payment pay(PaymentRequest request){
        Payment payment = new Payment();
        payment.setOrderId(request.getOrderId());
        payment.setPaymentMethod(request.getPaymentMethod());
        payment.setAmount(request.getAmount());
        payment.setPaymentStatus(PaymentStatus.PAID);
        Payment savedPayment = paymentRepository.save(payment);
        PaymentEvent event = mapToEvent(savedPayment);
        paymentPublisher.sendPaidEvent(event);
        return payment;
    }

    private PaymentEvent mapToEvent(Payment payment){
        return new PaymentEvent(
                payment.getId(),
                payment.getOrderId(),
                payment.getPaymentMethod(),
                payment.getAmount(),
                payment.getPaymentStatus(),
                payment.getCreatedAt()
        );
    }
}
