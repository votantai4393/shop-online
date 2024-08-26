package com.hiusahald.shop_online.models.order;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
@Data
public class OrderItemId {

    @Column(name = "orderId")
    private Long orderId;

    @Column(name = "productId")
    private Long productId;

}
