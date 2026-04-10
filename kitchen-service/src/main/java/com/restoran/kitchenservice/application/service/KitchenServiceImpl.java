package com.restoran.kitchenservice.application.service;

import com.restoran.kitchenservice.application.dto.KitchenOrderResponseDTO;
import com.restoran.kitchenservice.application.usecase.KitchenUseCase;
import com.restoran.kitchenservice.domain.model.KitchenOrder;
import com.restoran.kitchenservice.domain.model.KitchenStatus;
import com.restoran.kitchenservice.domain.repository.KitchenOrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class KitchenServiceImpl implements KitchenUseCase {

    private final KitchenOrderRepository repository;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    public List<KitchenOrderResponseDTO> getOrdersByStatus(KitchenStatus status) {
        return repository.findByStatus(status).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<KitchenOrderResponseDTO> getActiveOrders() {
        return repository.findActiveOrders().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public KitchenOrderResponseDTO getOrderDetail(String id) {
        KitchenOrder order = findOrderByIdOrOrderId(id);
        return mapToDTO(order);
    }

    @Override
    public KitchenOrderResponseDTO updateStatus(String id, KitchenStatus targetStatus) {
        KitchenOrder order = findOrderByIdOrOrderId(id);
        
        validateTransition(order.getStatus(), targetStatus);

        order.setStatus(targetStatus);
        order.setUpdatedAt(LocalDateTime.now());

        if (targetStatus == KitchenStatus.COMPLETED) {
            order.setCompletedAt(LocalDateTime.now());
        }

        KitchenOrder saved = repository.save(order);
        sendUpdateToKafka(saved);
        
        return mapToDTO(saved);
    }

    private KitchenOrder findOrderByIdOrOrderId(String id) {
        return repository.findById(id)
                .orElseGet(() -> repository.findByOrderId(id)
                .orElseThrow(() -> new RuntimeException("Order not found with ID: " + id)));
    }

    private void validateTransition(KitchenStatus current, KitchenStatus target) {
        boolean isValid = switch (current) {
            case QUEUED -> target == KitchenStatus.IN_PROGRESS;
            case IN_PROGRESS -> target == KitchenStatus.READY_TO_SERVE;
            case READY_TO_SERVE -> target == KitchenStatus.COMPLETED;
            case COMPLETED -> false;
        };
        if (!isValid) throw new RuntimeException("Invalid transition from " + current + " to " + target);
    }

    private void sendUpdateToKafka(KitchenOrder order) {
        Map<String, Object> message = new HashMap<>();
        message.put("orderId", order.getOrderId());
        message.put("status", order.getStatus().toString());
        kafkaTemplate.send("kitchen-updates", message);
    }

    private KitchenOrderResponseDTO mapToDTO(KitchenOrder order) {
        List<KitchenOrderResponseDTO.ItemDTO> items = order.getItems().stream()
                .map(i -> KitchenOrderResponseDTO.ItemDTO.builder()
                        .menuItem(i.getMenuItem())
                        .quantity(i.getQuantity())
                        .build())
                .collect(Collectors.toList());

        return KitchenOrderResponseDTO.builder()
                .id(order.getId())
                .orderId(order.getOrderId())
                .customerName(order.getCustomerName())
                .items(items)
                .status(order.getStatus())
                .createdAt(order.getCreatedAt())
                .updatedAt(order.getUpdatedAt())
                .completedAt(order.getCompletedAt())
                .build();
    }
}
