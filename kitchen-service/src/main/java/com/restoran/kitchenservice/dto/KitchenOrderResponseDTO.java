package com.restoran.kitchenservice.dto;

import com.restoran.kitchenservice.entity.KitchenOrder;
import com.restoran.kitchenservice.enums.KitchenStatus;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KitchenOrderResponseDTO {
    private String id;
    private String orderId;
    private String customerName;
    private List<KitchenOrder.KitchenOrderItem> items;
    private KitchenStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime completedAt;
}
