package com.restoran.kitchenservice.controller;

import com.restoran.kitchenservice.dto.KitchenOrderResponseDTO;
import com.restoran.kitchenservice.enums.KitchenStatus;
import com.restoran.kitchenservice.service.KitchenService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/kitchen")
@RequiredArgsConstructor
public class KitchenController {

    private final KitchenService kitchenService;

    @GetMapping("/queue")
    public List<KitchenOrderResponseDTO> getQueue() {
        return kitchenService.getOrdersByStatus(KitchenStatus.QUEUED);
    }

    @PutMapping("/queue/{orderId}/process")
    public KitchenOrderResponseDTO processOrder(@PathVariable String orderId) {
        return kitchenService.updateStatus(orderId, KitchenStatus.IN_PROGRESS);
    }

    @GetMapping("/in-progress")
    public List<KitchenOrderResponseDTO> getInProgress() {
        return kitchenService.getOrdersByStatus(KitchenStatus.IN_PROGRESS);
    }

    @PutMapping("/in-progress/{orderId}/ready")
    public KitchenOrderResponseDTO readyOrder(@PathVariable String orderId) {
        return kitchenService.updateStatus(orderId, KitchenStatus.READY_TO_SERVE);
    }

    @GetMapping("/ready")
    public List<KitchenOrderResponseDTO> getReady() {
        return kitchenService.getOrdersByStatus(KitchenStatus.READY_TO_SERVE);
    }

    @PutMapping("/ready/{orderId}/complete")
    public KitchenOrderResponseDTO completeOrder(@PathVariable String orderId) {
        return kitchenService.updateStatus(orderId, KitchenStatus.COMPLETED);
    }

    @GetMapping("/orders")
    public List<KitchenOrderResponseDTO> getAllActiveOrders() {
        return kitchenService.getActiveOrders();
    }

    @GetMapping("/orders/{orderId}")
    public KitchenOrderResponseDTO getOrderDetail(@PathVariable String orderId) {
        return kitchenService.getOrderDetail(orderId);
    }
}
