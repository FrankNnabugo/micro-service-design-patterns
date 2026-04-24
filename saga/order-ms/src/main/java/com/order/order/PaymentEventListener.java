package com.order.order;

import com.core.OrderStatus;
import com.core.PaymentEvent;
import com.core.PaymentStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
@KafkaListener(
        topics = "${spring.kafka.topics.payment}",
        groupId = "order-ms")
@RequiredArgsConstructor
@Slf4j
public class PaymentEventListener {
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final OrderRepository orderRepository;

    @KafkaHandler
    public void handleSuccessfulPaymentEvent(@Payload PaymentEvent event, Acknowledgment ack){
        log.info("received payment event: {}", event);
        if(event.getPaymentStatus().equals(PaymentStatus.PAID)){
            Order order = orderRepository.findById(event.getOrderId()).orElseThrow(()->
                    new RuntimeException("Order with " + event.getOrderId() + " Not found"));
            order.setOrderStatus(OrderStatus.COMPLETED);
            order.setPaymentStatus(PaymentStatus.PAID);
            orderRepository.save(order);
            ack.acknowledge();
            log.info("order {} is paid", event.getOrderId());
        }
        else if (event.getPaymentStatus().equals(PaymentStatus.FAILED)) {
            Order order = orderRepository.findById(event.getOrderId()).orElseThrow(()->
                    new RuntimeException("Order with " + event.getOrderId() + " Not found"));
            order.setOrderStatus(OrderStatus.CANCELLED);
            order.setPaymentStatus(PaymentStatus.FAILED);
            orderRepository.save(order);
            ack.acknowledge();
            log.info("order {} is failed", order.getId());

        }

    }
}
