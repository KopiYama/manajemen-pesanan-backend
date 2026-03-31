package com.restoran.serviceorder.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateOrderStatusRequestDTO {

    @NotBlank(message = "Status is required")
    @Pattern(regexp = "^(PENDING|PAID|PREPARING|READY|COMPLETED|CANCELLED)$", 
             message = "Invalid status value. Allowed: PENDING, PAID, PREPARING, READY, COMPLETED, CANCELLED")
    private String status;
}
