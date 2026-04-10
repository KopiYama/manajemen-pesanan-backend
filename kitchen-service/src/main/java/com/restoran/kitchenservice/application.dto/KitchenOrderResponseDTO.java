package com.restoran.kitchenservice.application.dto;

import com.restoran.kitchenservice.domain.model.KitchenStatus;
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
    private List<ItemDTO> items;
    private KitchenStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime completedAt;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ItemDTO {
        private String menuItem;
        private Integer quantity;
    }
}
