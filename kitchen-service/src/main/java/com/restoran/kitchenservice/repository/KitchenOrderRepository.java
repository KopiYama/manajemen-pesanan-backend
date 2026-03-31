package com.restoran.kitchenservice.repository;

import com.restoran.kitchenservice.entity.KitchenOrder;
import com.restoran.kitchenservice.enums.KitchenStatus;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;
import java.util.Optional;

public interface KitchenOrderRepository extends MongoRepository<KitchenOrder, String> {
    Optional<KitchenOrder> findByOrderId(String orderId);
    List<KitchenOrder> findByStatus(KitchenStatus status);
    List<KitchenOrder> findByStatusNot(KitchenStatus status);
}
