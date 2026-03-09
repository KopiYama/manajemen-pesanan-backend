package com.restoran.notificationservice.service;

import com.restoran.notificationservice.dto.OrderEventDTO;
import com.restoran.notificationservice.entity.NotificationLog;
import com.restoran.notificationservice.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderConsumer {

    private final NotificationRepository notificationRepository;

    /**
     * @KafkaListener adalah anotasi yang menandai metode sebagai target dari pesan Kafka.
     * Spring akan secara otomatis membuat message listener container untuk metode ini.
     * 
     * - topics: Nama topic yang ingin didengarkan ('order-notifications').
     * - groupId: ID grup konsumen untuk mengelola offset pembacaan pesan ('notification-group').
     */
    @KafkaListener(topics = "order-notifications", groupId = "notification-group")
    public void consume(OrderEventDTO orderEvent) {
        log.info("Received message from Kafka: {}", orderEvent);

        NotificationLog logEntry = NotificationLog.builder()
                .orderId(orderEvent.getOrderId())
                .message(orderEvent.getMessage())
                .createdAt(LocalDateTime.now())
                .build();

        notificationRepository.save(logEntry);
        log.info("Saved notification log to MongoDB for Order ID: {}", orderEvent.getOrderId());
    }
}
