package com.restoran.kitchenservice.dto;

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
    private List<String> items;
    private KitchenStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime completedAt;
}
