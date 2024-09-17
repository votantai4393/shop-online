package com.hiusahald.shop_online.models.order;

import com.hiusahald.shop_online.models.BaseAuditingEntity;
import com.hiusahald.shop_online.models.product.Product;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public class OrderItem extends BaseAuditingEntity {

    @EmbeddedId
    private OrderItemId id;

    @Column(nullable = false)
    private Integer quantity;

    @ManyToOne
    @MapsId("orderId")
    private Order order;

    @ManyToOne
    @MapsId("productId")
    private Product product;

}
