package com.hiusahald.shop_online.models.cart;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
@Data
public class CartItemId {

    @Column(name = "cartId")
    private Long cartId;

    @Column(name = "productId")
    private Long productId;

}
