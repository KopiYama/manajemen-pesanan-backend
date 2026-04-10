package com.restoran.kitchenservice.infrastructure.messaging;

import com.restoran.kitchenservice.domain.model.KitchenOrder;
import com.restoran.kitchenservice.domain.model.KitchenStatus;
import com.restoran.kitchenservice.domain.repository.KitchenOrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class KitchenOrderConsumer {

    private final KitchenOrderRepository repository;

    @KafkaListener(topics = "order-notifications", groupId = "kitchen-group")
    public void consumeOrderEvent(OrderEventDTO event) {
        log.info("Received order event from Kafka: {}", event);

        if (repository.findByOrderId(event.getOrderId().toString()).isPresent()) {
            log.warn("Duplicate order. Skipping orderId: {}", event.getOrderId());
            return;
        }

        List<KitchenOrder.KitchenOrderItem> domainItems = event.getItems().stream()
                .map(i -> KitchenOrder.KitchenOrderItem.builder()
                        .menuItem(i.getMenuItem())
                        .quantity(i.getQuantity())
                        .build())
                .collect(Collectors.toList());

        KitchenOrder order = KitchenOrder.builder()
                .orderId(event.getOrderId().toString())
                .customerName(event.getCustomerName())
                .items(domainItems)
                .status(KitchenStatus.QUEUED)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        repository.save(order);
        log.info("Order {} added to kitchen domain", event.getOrderId());
    }
}
