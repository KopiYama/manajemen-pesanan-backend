package com.restoran.menuservice.domain.model;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MenuMakanan {
    private Integer id;
    private String namaMenu;
    private String deskripsi;
    private BigDecimal harga;
    private JenisMakanan jenis;
    private String imageUrl;
    private Boolean isAvailable;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
