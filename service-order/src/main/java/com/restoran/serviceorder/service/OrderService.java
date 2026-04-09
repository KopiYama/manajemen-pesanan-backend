package com.restoran.serviceorder.service;

import com.restoran.serviceorder.dto.*;
import com.restoran.serviceorder.entity.Order;
import com.restoran.serviceorder.entity.OrderItem;
import com.restoran.serviceorder.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;
import java.util.stream.Collectors;

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
                .totalPrice(request.getTotalPrice())
                .status("PENDING")
                .build();

        request.getItems().forEach(itemDto -> {
            OrderItem orderItem = OrderItem.builder()
                    .menuItem(itemDto.getMenuItem())
                    .quantity(itemDto.getQuantity())
                    .price(itemDto.getPrice())
                    .build();
            order.addItem(orderItem);
        });

        Order savedOrder = orderRepository.save(order);
        this.sendOrderEvent(savedOrder);

        return savedOrder;
    }

    @Cacheable(value = "orders", key = "#pageable.pageNumber + '-' + #pageable.pageSize")
    public Page<Order> getAllOrders(Pageable pageable) {
        log.info("Fetching orders from Database for page: {}", pageable.getPageNumber());
        return orderRepository.findAll(pageable);
    }

    public Order getOrderById(UUID id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found with ID: " + id));
    }

    @Transactional
    @CacheEvict(value = "orders", allEntries = true)
    public Order updateOrderStatus(UUID id, UpdateOrderStatusRequestDTO request) {
        Order order = getOrderById(id);
        order.setStatus(request.getStatus().toUpperCase());

        Order updatedOrder = orderRepository.save(order);
        this.sendOrderEvent(updatedOrder);

        return updatedOrder;
    }

    private void sendOrderEvent(Order order) {
        OrderEventDTO event = OrderEventDTO.builder()
                .orderId(order.getId())
                .customerName(order.getCustomerName())
                .menuItems(order.getItems().stream()
                        .map(item -> item.getQuantity() + "x " + item.getMenuItem())
                        .collect(Collectors.toList()))
                .totalPrice(order.getTotalPrice())
                .status(order.getStatus())
                .build();

        kafkaTemplate.send("order-notifications", event);
        log.info("Order event sent to Kafka for ID: {} with status: {}", order.getId(), order.getStatus());     
    }
}
