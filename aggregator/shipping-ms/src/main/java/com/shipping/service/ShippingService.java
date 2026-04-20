package com.shipping.service;

import com.shipping.dto.ShippingRequest;
import com.shipping.dto.ShippingResponse;
import com.shipping.entity.Shipping;

public interface ShippingService {
    ShippingResponse createShipping(ShippingRequest request);
    ShippingResponse getShipping(String id);
}
