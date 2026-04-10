package com.restoran.kitchenservice.infrastructure.persistence;

import com.restoran.kitchenservice.domain.model.KitchenStatus;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;
import java.util.Optional;

public interface MongoKitchenOrderRepository extends MongoRepository<KitchenOrderDocument, String> {
    Optional<KitchenOrderDocument> findByOrderId(String orderId);
    List<KitchenOrderDocument> findByStatus(KitchenStatus status);
    List<KitchenOrderDocument> findByStatusNot(KitchenStatus status);
}
