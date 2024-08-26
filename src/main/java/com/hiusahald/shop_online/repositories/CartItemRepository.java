package com.hiusahald.shop_online.repositories;

import com.hiusahald.shop_online.models.cart.CartItem;
import com.hiusahald.shop_online.models.cart.CartItemId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, CartItemId> {
}