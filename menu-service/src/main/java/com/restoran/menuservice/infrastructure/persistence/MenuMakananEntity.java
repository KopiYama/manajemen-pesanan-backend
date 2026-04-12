package com.restoran.menuservice.infrastructure.persistence;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "menu_makanan")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MenuMakananEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nama_menu", length = 100, nullable = false)
    private String namaMenu;

    @Column(columnDefinition = "TEXT")
    private String deskripsi;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal harga;

    @ManyToOne
    @JoinColumn(name = "jenis_id")
    private JenisMakananEntity jenis;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "is_available")
    private Boolean isAvailable;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (isAvailable == null) isAvailable = true;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
