package com.shipping.service;

import com.shipping.dto.ShippingRequest;
import com.shipping.dto.ShippingResponse;
import com.shipping.entity.Shipping;
import com.shipping.repository.ShippingRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShippingServiceImpl implements ShippingService{
    private final ShippingRepository shippingRepository;
    private final ModelMapper modelMapper;

    @Override
    public ShippingResponse createShipping(ShippingRequest request){
        Shipping shipping = new Shipping();
        shipping.setStatus(request.getStatus());
        shipping.setLocation(request.getLocation());
        shippingRepository.save(shipping);
        return modelMapper.map(shipping, ShippingResponse.class);
    }

    @Override
    public ShippingResponse getShipping(String id){
        Shipping shipping = shippingRepository.findById(id).orElseThrow();
        return modelMapper.map(shipping, ShippingResponse.class);
    }
}
