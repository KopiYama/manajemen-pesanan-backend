package com.restoran.serviceorder.controller;

import com.restoran.serviceorder.dto.*;
import com.restoran.serviceorder.entity.Order;
import com.restoran.serviceorder.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<WebResponse<Order>> createOrder(@Valid @RequestBody OrderRequestDTO request) {
        Order createdOrder = orderService.createOrder(request);
        
        WebResponse<Order> response = WebResponse.<Order>builder()
                .success(true)
                .message("Order created successfully")
                .data(createdOrder)
                .build();
                
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<WebResponse<Page<Order>>> getAllOrders(@PageableDefault(size = 10) Pageable pageable) {
        Page<Order> orders = orderService.getAllOrders(pageable);
        
        WebResponse<Page<Order>> response = WebResponse.<Page<Order>>builder()
                .success(true)
                .message("Orders retrieved successfully")
                .data(orders)
                .build();
                
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<WebResponse<Order>> getOrderById(@PathVariable UUID id) {
        Order order = orderService.getOrderById(id);
        
        WebResponse<Order> response = WebResponse.<Order>builder()
                .success(true)
                .message("Order found")
                .data(order)
                .build();
                
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<WebResponse<Order>> updateStatus(
            @PathVariable UUID id, 
            @Valid @RequestBody UpdateOrderStatusRequestDTO request) {
        
        Order updatedOrder = orderService.updateOrderStatus(id, request);
        
        WebResponse<Order> response = WebResponse.<Order>builder()
                .success(true)
                .message("Order status updated successfully")
                .data(updatedOrder)
                .build();
                
        return ResponseEntity.ok(response);
    }
}
