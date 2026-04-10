package com.restoran.kitchenservice.presentation.controller;

import com.restoran.kitchenservice.application.dto.KitchenOrderResponseDTO;
import com.restoran.kitchenservice.application.usecase.KitchenUseCase;
import com.restoran.kitchenservice.domain.model.KitchenStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/kitchen")
@RequiredArgsConstructor
public class KitchenController {

    private final KitchenUseCase kitchenUseCase;

    @GetMapping("/queue")
    public List<KitchenOrderResponseDTO> getQueue() {
        return kitchenUseCase.getOrdersByStatus(KitchenStatus.QUEUED);
    }

    @GetMapping("/in-progress")
    public List<KitchenOrderResponseDTO> getInProgress() {
        return kitchenUseCase.getOrdersByStatus(KitchenStatus.IN_PROGRESS);
    }

    @GetMapping("/ready")
    public List<KitchenOrderResponseDTO> getReady() {
        return kitchenUseCase.getOrdersByStatus(KitchenStatus.READY_TO_SERVE);
    }

    @GetMapping("/orders")
    public List<KitchenOrderResponseDTO> getActiveOrders() {
        return kitchenUseCase.getActiveOrders();
    }

    @GetMapping("/orders/{id}")
    public KitchenOrderResponseDTO getOrderDetail(@PathVariable String id) {
        return kitchenUseCase.getOrderDetail(id);
    }

    @PutMapping("/{id}/status")
    public KitchenOrderResponseDTO updateStatus(
            @PathVariable String id,
            @RequestParam KitchenStatus status) {
        return kitchenUseCase.updateStatus(id, status);
    }
}
