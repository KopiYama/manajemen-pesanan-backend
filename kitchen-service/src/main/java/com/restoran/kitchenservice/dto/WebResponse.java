package com.restoran.kitchenservice.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WebResponse<T> {
    private boolean success;
    private String message;
    private T data;
}
