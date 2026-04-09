package com.restoran.serviceorder.service;

import com.restoran.serviceorder.entity.Order;
import com.restoran.serviceorder.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class KitchenUpdateConsumer {

    private final OrderRepository orderRepository;

    @Transactional
    @KafkaListener(topics = "kitchen-updates", groupId = "order-group")
    public void consumeKitchenUpdate(Map<String, String> message) {
        log.info("Received status update from Kitchen: {}", message);
        
        try {
            UUID orderId = UUID.fromString(message.get("orderId"));
            String status = message.get("status");
            
            orderRepository.findById(orderId).ifPresent(order -> {
                order.setStatus(status);
                orderRepository.save(order);
                log.info("Successfully updated Order {} status to {}", orderId, status);
            });
        } catch (Exception e) {
            log.error("Failed to process kitchen update message", e);
        }
    }
}
