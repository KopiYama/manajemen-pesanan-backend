package com.restoran.menuservice.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "jenis_makanan")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JenisMakanan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nama_jenis", length = 50, nullable = false)
    private String namaJenis;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
