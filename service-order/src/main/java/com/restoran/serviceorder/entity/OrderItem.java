package com.restoran.serviceorder.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "order_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    @JsonIgnore
    private Order order;

    @Column(nullable = false)
    @JsonProperty("menuItem")
    private String menuItem;

    @Column(nullable = false)
    @JsonProperty("quantity")
    private Integer quantity;

    @Column(nullable = false)
    @JsonProperty("price")
    private BigDecimal price;

    // Alias tambahan untuk keamanan kompatibilitas UI
    @JsonProperty("name")
    public String getName() {
        return menuItem;
    }

    @JsonProperty("harga")
    public BigDecimal getHarga() {
        return price;
    }
}
