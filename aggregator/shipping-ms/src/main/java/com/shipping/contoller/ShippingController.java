package com.shipping.contoller;

import com.shipping.dto.ShippingRequest;
import com.shipping.dto.ShippingResponse;
import com.shipping.entity.Shipping;
import com.shipping.service.ShippingServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/shipping")
@RequiredArgsConstructor
public class ShippingController {
    private final ShippingServiceImpl shippingServiceImpl;

    @PostMapping
    public ResponseEntity<ShippingResponse> createShipping(@RequestBody ShippingRequest request){
        ShippingResponse shipping = shippingServiceImpl.createShipping(request);
        return new ResponseEntity<>(shipping, HttpStatus.OK);
    }

    @GetMapping("{shippingId}")
    public ResponseEntity<ShippingResponse> getShipping(@PathVariable String shippingId){
        ShippingResponse shipping = shippingServiceImpl.getShipping(shippingId);
        return new ResponseEntity<>(shipping, HttpStatus.OK);
    }
}
