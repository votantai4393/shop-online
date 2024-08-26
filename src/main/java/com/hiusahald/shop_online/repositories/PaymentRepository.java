package com.hiusahald.shop_online.repositories;

import com.hiusahald.shop_online.models.order.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}