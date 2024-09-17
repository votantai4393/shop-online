package com.hiusahald.shop_online.controllers;

import com.hiusahald.shop_online.dto.CartDto;
import com.hiusahald.shop_online.dto.request.CartRequest;
import com.hiusahald.shop_online.dto.response.PageResponse;
import com.hiusahald.shop_online.services.shop.cart.CartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping
    public ResponseEntity<CartDto> addToCart(
            @RequestBody @Valid CartRequest cartRequest,
            Authentication auth) {
        return ResponseEntity.ok(cartService.addToCart(cartRequest, auth));
    }

    @DeleteMapping
    public ResponseEntity<CartDto> removeItem(
            @RequestBody CartRequest cartRequest,
            Authentication auth) {
        return ResponseEntity.ok(cartService.removeItemFromCart(cartRequest, auth));
    }

    @PutMapping("/update-quantity")
    public ResponseEntity<CartDto> updateQuantity(
            @RequestBody @Valid CartRequest cartRequest,
            Authentication auth) {
        return ResponseEntity.ok(cartService.updateCart(cartRequest, auth));
    }

    @GetMapping
    public ResponseEntity<CartDto> getCart(Authentication auth) {
        return ResponseEntity.ok(cartService.getCart(auth));
    }

    @GetMapping("/all")
    public ResponseEntity<PageResponse<CartDto>> getAllCarts(
            @RequestParam(name = "page_number", defaultValue = "0") int number,
            @RequestParam(name = "page_size", defaultValue = "10") int size,
            Authentication auth) {
        return ResponseEntity.ok(cartService.getAllCarts(number, size, auth));
    }

}
