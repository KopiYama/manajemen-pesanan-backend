package com.restoran.menuservice.dto;

import lombok.*;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MenuMakananResponseDTO {
    private Integer id;
    private String namaMenu;
    private String deskripsi;
    private BigDecimal harga;
    private String namaJenis;
    private String imageUrl;
    private Boolean isAvailable;
}
