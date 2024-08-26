package com.hiusahald.shop_online.repositories;

import com.hiusahald.shop_online.models.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}