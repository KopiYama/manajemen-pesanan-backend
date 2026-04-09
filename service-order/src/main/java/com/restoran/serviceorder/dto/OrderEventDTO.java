package com.restoran.serviceorder.dto;

import lombok.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderEventDTO {
    private UUID orderId;
    private String customerName;
    private List<OrderItemEventDTO> items; // Sekarang berbentuk list objek
    private BigDecimal totalPrice;
    private String status;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OrderItemEventDTO {
        private String menuItem;
        private Integer quantity;
    }
}
