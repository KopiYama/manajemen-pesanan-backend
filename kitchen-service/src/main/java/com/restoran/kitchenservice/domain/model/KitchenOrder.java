package com.restoran.kitchenservice.domain.model;

import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KitchenOrder {
    private String id;
    private String orderId;
    private String customerName;
    private List<KitchenOrderItem> items;
    private KitchenStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime completedAt;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class KitchenOrderItem {
        private String menuItem;
        private Integer quantity;
    }
}
