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
    private String menuItem;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private BigDecimal price;

    // ALIASES UNTUK FRONTEND (Agar tidak 'undefined' atau 'RpNaN')
    
    @JsonProperty("name")
    public String getName() {
        return menuItem;
    }

    @JsonProperty("harga")
    public BigDecimal getHarga() {
        return price;
    }
    
    @JsonProperty("menuItem")
    public String getMenuItemAlias() {
        return menuItem;
    }
    
    @JsonProperty("price")
    public BigDecimal getPriceAlias() {
        return price;
    }
}
