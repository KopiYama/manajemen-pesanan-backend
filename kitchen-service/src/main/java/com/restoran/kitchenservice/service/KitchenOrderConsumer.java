package com.restoran.kitchenservice.service;

import com.restoran.kitchenservice.dto.OrderEventDTO;
import com.restoran.kitchenservice.entity.KitchenOrder;
import com.restoran.kitchenservice.enums.KitchenStatus;
import com.restoran.kitchenservice.repository.KitchenOrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class KitchenOrderConsumer {

    private final KitchenOrderRepository repository;

    @KafkaListener(topics = "order-notifications", groupId = "kitchen-group")
    public void consumeOrderEvent(OrderEventDTO event) {
        log.info("Received order event from Kafka: {}", event);

        if (repository.findByOrderId(event.getOrderId().toString()).isPresent()) {
            log.warn("Duplicate order received from Kafka. Skipping orderId: {}", event.getOrderId());
            return;
        }

        KitchenOrder kitchenOrder = KitchenOrder.builder()
                .orderId(event.getOrderId().toString())
                .customerName(event.getCustomerName())
                .status(KitchenStatus.QUEUED)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        repository.save(kitchenOrder);
        log.info("New order queued in kitchen: {}", event.getOrderId());
    }
}
