package com.restoran.notificationservice.service;

import com.restoran.notificationservice.dto.OrderEventDTO;
import com.restoran.notificationservice.entity.NotificationLog;
import com.restoran.notificationservice.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderConsumer {

    private final NotificationRepository notificationRepository;

    @KafkaListener(topics = "order-notifications", groupId = "notification-group")
    public void consume(OrderEventDTO orderEvent) {
        log.info("Received message from Kafka: {}", orderEvent);

        String itemsSummary = orderEvent.getItems() != null 
                ? orderEvent.getItems().stream()
                    .map(item -> item.getQuantity() + "x " + item.getMenuItem())
                    .collect(Collectors.joining(", "))
                : "No items";

        String message = String.format("Order received from %s with status %s. Items: %s", 
                orderEvent.getCustomerName(), orderEvent.getStatus(), itemsSummary);

        NotificationLog logEntry = NotificationLog.builder()
                .orderId(orderEvent.getOrderId().toString())
                .message(message)
                .createdAt(LocalDateTime.now())
                .build();

        notificationRepository.save(logEntry);
        log.info("Saved notification log to MongoDB for Order ID: {}", orderEvent.getOrderId());
    }
}
