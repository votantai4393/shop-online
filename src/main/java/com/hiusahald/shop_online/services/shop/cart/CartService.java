package com.hiusahald.shop_online.services.shop.cart;

import com.hiusahald.shop_online.dto.CartDto;
import com.hiusahald.shop_online.dto.request.CartRequest;
import com.hiusahald.shop_online.dto.response.PageResponse;
import org.springframework.security.core.Authentication;

public interface CartService {

    CartDto getCart(Authentication auth);

    PageResponse<CartDto> getAllCarts(int number, int size, Authentication auth);

    CartDto addToCart(CartRequest request, Authentication auth);

    CartDto removeItemFromCart(CartRequest cartRequest, Authentication auth);

    CartDto updateCart(CartRequest request, Authentication auth);

}
