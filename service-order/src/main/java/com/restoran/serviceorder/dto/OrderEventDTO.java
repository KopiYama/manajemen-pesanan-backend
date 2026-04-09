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
    private List<String> menuItems; // List nama makanan untuk dapur
    private BigDecimal totalPrice;
    private String status;
}
