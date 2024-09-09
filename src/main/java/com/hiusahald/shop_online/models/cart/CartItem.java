package com.hiusahald.shop_online.models.cart;

import com.hiusahald.shop_online.models.BaseJoinEntity;
import com.hiusahald.shop_online.models.product.Product;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "cartItems")
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public class CartItem extends BaseJoinEntity {

    @EmbeddedId
    private CartItemId id;

    @Column(nullable = false)
    private Integer quantity;

    @ManyToOne
    @MapsId("cartId")
    private Cart cart;

    @ManyToOne
    @MapsId("productId")
    private Product product;

}
