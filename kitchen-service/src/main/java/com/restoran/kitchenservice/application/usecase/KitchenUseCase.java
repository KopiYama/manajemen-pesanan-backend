package com.restoran.kitchenservice.application.usecase;

import com.restoran.kitchenservice.application.dto.KitchenOrderResponseDTO;
import com.restoran.kitchenservice.domain.model.KitchenStatus;
import java.util.List;

public interface KitchenUseCase {
    List<KitchenOrderResponseDTO> getOrdersByStatus(KitchenStatus status);
    List<KitchenOrderResponseDTO> getActiveOrders();
    KitchenOrderResponseDTO getOrderDetail(String id);
    KitchenOrderResponseDTO updateStatus(String id, KitchenStatus targetStatus);
}
