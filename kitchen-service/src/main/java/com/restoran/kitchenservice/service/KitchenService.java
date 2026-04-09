package com.restoran.kitchenservice.service;

import com.restoran.kitchenservice.dto.KitchenOrderResponseDTO;
import com.restoran.kitchenservice.entity.KitchenOrder;
import com.restoran.kitchenservice.enums.KitchenStatus;
import com.restoran.kitchenservice.exception.InvalidStageTransitionException;
import com.restoran.kitchenservice.exception.OrderNotFoundException;
import com.restoran.kitchenservice.repository.KitchenOrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class KitchenService {

    private final KitchenOrderRepository repository;
    private final org.springframework.kafka.core.KafkaTemplate<String, Object> kafkaTemplate;

    public List<KitchenOrderResponseDTO> getOrdersByStatus(KitchenStatus status) {
        return repository.findByStatus(status).stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    public List<KitchenOrderResponseDTO> getActiveOrders() {
        return repository.findByStatusNot(KitchenStatus.COMPLETED).stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    public KitchenOrderResponseDTO getOrderDetail(String orderId) {
        Optional<KitchenOrder> orderOpt = repository.findByOrderId(orderId);
        if (orderOpt.isEmpty()) {
            orderOpt = repository.findById(orderId);
        }
        
        KitchenOrder order = orderOpt.orElseThrow(() -> 
                new OrderNotFoundException("Kitchen order not found for ID: " + orderId));
        return mapToResponseDTO(order);
    }

    public KitchenOrderResponseDTO updateStatusById(String id, KitchenStatus targetStatus) {
        // Coba cari berdasarkan ID internal MongoDB dahulu
        Optional<KitchenOrder> orderOpt = repository.findById(id);
        
        // Jika tidak ketemu, cari berdasarkan Order ID (UUID)
        if (orderOpt.isEmpty()) {
            orderOpt = repository.findByOrderId(id);
        }

        KitchenOrder order = orderOpt.orElseThrow(() -> 
                new OrderNotFoundException("Kitchen order not found for ID: " + id));

        validateTransition(order.getStatus(), targetStatus);

        order.setStatus(targetStatus);
        order.setUpdatedAt(LocalDateTime.now());

        if (targetStatus == KitchenStatus.COMPLETED) {
            order.setCompletedAt(LocalDateTime.now());
        }

        KitchenOrder updatedOrder = repository.save(order);
        log.info("Order {} transitioned to {}", id, targetStatus);
        
        // Kirim update status ke Kafka agar Order Service bisa update DB-nya
        try {
            java.util.Map<String, Object> message = new java.util.HashMap<>();
            message.put("orderId", updatedOrder.getOrderId());
            message.put("status", updatedOrder.getStatus().toString());
            kafkaTemplate.send("kitchen-updates", message);
        } catch (Exception e) {
            log.error("Failed to send status update to Kafka", e);
        }
        
        return mapToResponseDTO(updatedOrder);
    }

    public KitchenOrderResponseDTO updateStatus(String orderId, KitchenStatus targetStatus) {
        return updateStatusById(orderId, targetStatus);
    }

    private void validateTransition(KitchenStatus current, KitchenStatus target) {
        boolean isValid = switch (current) {
            case QUEUED -> target == KitchenStatus.IN_PROGRESS;
            case IN_PROGRESS -> target == KitchenStatus.READY_TO_SERVE;
            case READY_TO_SERVE -> target == KitchenStatus.COMPLETED;
            case COMPLETED -> false;
        };

        if (!isValid) {
            throw new InvalidStageTransitionException("Invalid status transition from " + current + " to " + target);
        }
    }

    private KitchenOrderResponseDTO mapToResponseDTO(KitchenOrder order) {
        return KitchenOrderResponseDTO.builder()
                .id(order.getId())
                .orderId(order.getOrderId())
                .customerName(order.getCustomerName())
                .items(order.getItems())
                .status(order.getStatus())
                .createdAt(order.getCreatedAt())
                .updatedAt(order.getUpdatedAt())
                .completedAt(order.getCompletedAt())
                .build();
    }
}
