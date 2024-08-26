package com.hiusahald.shop_online.repositories;

import com.hiusahald.shop_online.models.cart.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
}