package com.hiusahald.shop_online.controllers;

import com.hiusahald.shop_online.dto.OrderDto;
import com.hiusahald.shop_online.services.shop.order.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<?> placeOrder(
            Long userId, OrderDto requestBody,
            Authentication authentication) {
        return null;
    }

}
