package com.hiusahald.shop_online.repositories;

import com.hiusahald.shop_online.models.order.OrderItem;
import com.hiusahald.shop_online.models.order.OrderItemId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailRepository extends JpaRepository<OrderItem, OrderItemId> {
}