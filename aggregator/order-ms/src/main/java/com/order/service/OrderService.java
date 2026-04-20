package com.order.service;

import com.order.dto.OrderRequest;
import com.order.dto.OrderResponse;
import com.order.entity.Order;

public interface OrderService {

    OrderResponse createOrder(OrderRequest request);
    OrderResponse getOrder(String id);
}
