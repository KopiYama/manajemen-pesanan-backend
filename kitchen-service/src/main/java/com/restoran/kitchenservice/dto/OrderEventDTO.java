package com.restoran.kitchenservice.dto;

import lombok.*;
import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderEventDTO {
    private UUID orderId;
    private String customerName;
    private BigDecimal totalPrice;
    private String status;
}
