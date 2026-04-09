package com.restoran.serviceorder.dto;

import lombok.*;
import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemResponseDTO {
    private UUID id;
    private String menuItem;
    private String name;      // Alias untuk UI
    private Integer quantity;
    private BigDecimal price;
    private BigDecimal harga; // Alias untuk UI
}
