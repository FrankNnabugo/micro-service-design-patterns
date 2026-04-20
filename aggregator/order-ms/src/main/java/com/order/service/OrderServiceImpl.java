package com.order.service;

import com.fasterxml.jackson.databind.RuntimeJsonMappingException;
import com.order.dto.OrderRequest;
import com.order.dto.OrderResponse;
import com.order.entity.Order;
import com.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{
    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;
    @Override
    public OrderResponse createOrder(OrderRequest request) {
        Order order = new Order();
        order.setItem(request.getItem());
        order.setStatus(request.getStatus());
        orderRepository.save(order);
        return modelMapper.map(order, OrderResponse.class);
    }

    @Override
    public OrderResponse getOrder(String id) {
        Order order = orderRepository.findById(id).orElseThrow(()->
                new RuntimeJsonMappingException("Order not found"));
        return modelMapper.map(order, OrderResponse.class);
    }
}
