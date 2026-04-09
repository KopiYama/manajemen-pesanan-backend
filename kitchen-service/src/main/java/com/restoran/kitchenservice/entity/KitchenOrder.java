package com.restoran.kitchenservice.entity;

import com.restoran.kitchenservice.enums.KitchenStatus;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "kitchen_orders")
public class KitchenOrder {
    @Id
    private String id;

    @Indexed(unique = true)
    private String orderId;

    private String customerName;
    private List<String> items;
    private KitchenStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime completedAt;
}
