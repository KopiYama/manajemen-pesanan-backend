package com.restoran.menuservice.domain.model;

import lombok.*;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JenisMakanan {
    private Integer id;
    private String namaJenis;
    private LocalDateTime createdAt;
}
