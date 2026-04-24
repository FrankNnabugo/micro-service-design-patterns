package com.order.order;

import com.core.OrderEvent;
import com.core.OrderRequest;
import com.core.PaymentStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderPublisher orderPublisher;

    @Transactional
    public Order createOrder(OrderRequest request){
        Order order = new Order();
        order.setItem(request.getItem());
        order.setShippingAddress(request.getShippingAddress());
        order.setPaymentStatus(PaymentStatus.NOT_PAID);
        Order savedOrder = orderRepository.save(order);
        OrderEvent event = mapToEvent(savedOrder);
        orderPublisher.publishOrderCreatedEvent(event);
        return order;
    }

    public OrderEvent mapToEvent(Order order){
        return new OrderEvent(
                order.getId(),
                order.getItem(),
                order.getShippingAddress(),
                order.getOrderStatus(),
                order.getCreatedAt()
        );
    }
}
