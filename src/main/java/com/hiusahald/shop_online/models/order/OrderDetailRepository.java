package com.hiusahald.shop_online.models.order;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailRepository extends JpaRepository<OrderItem, OrderItemId> {
}