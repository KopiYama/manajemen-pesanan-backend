package com.restoran.kitchenservice.domain.repository;

import com.restoran.kitchenservice.domain.model.KitchenOrder;
import com.restoran.kitchenservice.domain.model.KitchenStatus;
import java.util.List;
import java.util.Optional;

public interface KitchenOrderRepository {
    KitchenOrder save(KitchenOrder order);
    Optional<KitchenOrder> findById(String id);
    Optional<KitchenOrder> findByOrderId(String orderId);
    List<KitchenOrder> findByStatus(KitchenStatus status);
    List<KitchenOrder> findActiveOrders();
}
