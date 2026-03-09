package com.restoran.serviceorder.dto;

import lombok.*;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequestDTO {
    private String customerName;
    private String menuItem;
    private Integer quantity;
    private BigDecimal totalPrice;
}
