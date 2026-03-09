package com.restoran.serviceorder.controller;

import com.restoran.serviceorder.dto.OrderRequestDTO;
import com.restoran.serviceorder.dto.WebResponse;
import com.restoran.serviceorder.entity.Order;
import com.restoran.serviceorder.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<WebResponse<Order>> createOrder(@RequestBody OrderRequestDTO request) {
        Order createdOrder = orderService.createOrder(request);
        
        WebResponse<Order> response = WebResponse.<Order>builder()
                .success(true)
                .message("Order created successfully")
                .data(createdOrder)
                .build();
                
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
