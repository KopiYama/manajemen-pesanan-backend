package com.restoran.serviceorder.service;

import com.restoran.serviceorder.dto.OrderEventDTO;
import com.restoran.serviceorder.dto.OrderRequestDTO;
import com.restoran.serviceorder.entity.Order;
import com.restoran.serviceorder.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Transactional
    public Order createOrder(OrderRequestDTO request) {
        Order order = Order.builder()
                .customerName(request.getCustomerName())
                .menuItem(request.getMenuItem())
                .quantity(request.getQuantity())
                .totalPrice(request.getTotalPrice())
                .status("PENDING")
                .build();

        Order savedOrder = orderRepository.save(order);

        // Kirim event ke Kafka setelah berhasil simpan ke DB
        OrderEventDTO event = OrderEventDTO.builder()
                .orderId(savedOrder.getId())
                .customerName(savedOrder.getCustomerName())
                .totalPrice(savedOrder.getTotalPrice())
                .build();

        kafkaTemplate.send("order-notifications", event);
        log.info("Order event sent to Kafka for Order ID: {}", savedOrder.getId());

        return savedOrder;
    }
}
