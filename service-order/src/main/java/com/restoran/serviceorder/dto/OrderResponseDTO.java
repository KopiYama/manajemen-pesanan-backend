package com.restoran.serviceorder.dto;

import lombok.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponseDTO {
    private UUID id;
    private String customerName;
    private String status;
    private BigDecimal totalPrice;
    private List<OrderItemResponseDTO> items;
}
