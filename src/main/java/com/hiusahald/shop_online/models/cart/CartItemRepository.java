package com.hiusahald.shop_online.models.cart;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, CartItemId> {

    @Modifying
    @Transactional
    @Query("""
           update CartItem ci
           set ci.quantity = (ci.quantity + :quantity)
           where ci.cart.id = :cartId
           and ci.product.id = :productId
           """)
    void updateItemQuantity(@Param("cartId") Long cartId, @Param("productId") Long productId,
            @Param("quantity") int quantity);

    @Query("""
           select ci from CartItem ci
           where ci.cart.id = :cartId
           and ci.product.id = :productId
           """)
    Optional<CartItem> findItemInCart(@Param("cartId") Long cartId, @Param("productId") Long productId);

    @Modifying
    @Transactional
    @Query("""
           delete from CartItem ci
           where ci.cart.id = :cartId
           and ci.product.id = :productId
           """)
    void removeItemFromCart(@Param("cartId") Long cartId, @Param("productId") Long productId);

}