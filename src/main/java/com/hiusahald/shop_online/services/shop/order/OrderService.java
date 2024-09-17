package com.hiusahald.shop_online.services.shop.order;

import com.hiusahald.shop_online.dto.OrderDto;
import com.hiusahald.shop_online.dto.response.PageResponse;
import org.springframework.security.core.Authentication;

public interface OrderService {

    OrderDto placeOrder(Long userId, OrderDto request, Authentication authentication);

    OrderDto updateOrder(Long userId, OrderDto request, Authentication authentication);

    void cancelOrder(Long userId, Authentication authentication);

    OrderDto getOder(Long userId, Long orderId, Authentication authentication);

    PageResponse<OrderDto> getAllOrders(Long userId, Authentication authentication);

    PageResponse<OrderDto> getAllOrders(Authentication authentication);

}
