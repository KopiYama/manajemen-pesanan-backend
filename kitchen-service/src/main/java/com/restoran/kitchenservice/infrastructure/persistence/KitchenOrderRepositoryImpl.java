package com.restoran.kitchenservice.infrastructure.persistence;

import com.restoran.kitchenservice.domain.model.KitchenOrder;
import com.restoran.kitchenservice.domain.model.KitchenStatus;
import com.restoran.kitchenservice.domain.repository.KitchenOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class KitchenOrderRepositoryImpl implements KitchenOrderRepository {

    private final MongoKitchenOrderRepository mongoRepository;

    @Override
    public KitchenOrder save(KitchenOrder order) {
        KitchenOrderDocument document = toDocument(order);
        KitchenOrderDocument saved = mongoRepository.save(document);
        return toDomain(saved);
    }

    @Override
    public Optional<KitchenOrder> findById(String id) {
        return mongoRepository.findById(id).map(this::toDomain);
    }

    @Override
    public Optional<KitchenOrder> findByOrderId(String orderId) {
        return mongoRepository.findByOrderId(orderId).map(this::toDomain);
    }

    @Override
    public List<KitchenOrder> findByStatus(KitchenStatus status) {
        return mongoRepository.findByStatus(status).stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<KitchenOrder> findActiveOrders() {
        return mongoRepository.findByStatusNot(KitchenStatus.COMPLETED).stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    private KitchenOrderDocument toDocument(KitchenOrder domain) {
        List<KitchenOrderDocument.ItemDocument> items = domain.getItems().stream()
                .map(i -> KitchenOrderDocument.ItemDocument.builder()
                        .menuItem(i.getMenuItem())
                        .quantity(i.getQuantity())
                        .build())
                .collect(Collectors.toList());

        return KitchenOrderDocument.builder()
                .id(domain.getId())
                .orderId(domain.getOrderId())
                .customerName(domain.getCustomerName())
                .items(items)
                .status(domain.getStatus())
                .createdAt(domain.getCreatedAt())
                .updatedAt(domain.getUpdatedAt())
                .completedAt(domain.getCompletedAt())
                .build();
    }

    private KitchenOrder toDomain(KitchenOrderDocument doc) {
        List<KitchenOrder.KitchenOrderItem> items = doc.getItems().stream()
                .map(i -> KitchenOrder.KitchenOrderItem.builder()
                        .menuItem(i.getMenuItem())
                        .quantity(i.getQuantity())
                        .build())
                .collect(Collectors.toList());

        return KitchenOrder.builder()
                .id(doc.getId())
                .orderId(doc.getOrderId())
                .customerName(doc.getCustomerName())
                .items(items)
                .status(doc.getStatus())
                .createdAt(doc.getCreatedAt())
                .updatedAt(doc.getUpdatedAt())
                .completedAt(doc.getCompletedAt())
                .build();
    }
}
