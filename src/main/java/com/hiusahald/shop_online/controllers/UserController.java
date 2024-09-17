package com.hiusahald.shop_online.controllers;

import com.hiusahald.shop_online.dto.response.PageResponse;
import com.hiusahald.shop_online.dto.UserDto;
import com.hiusahald.shop_online.services.shop.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUser(
            @PathVariable("id") Long userId,
            Authentication auth) {
        return ResponseEntity.ok(
                userService.getUser(userId, auth)
        );
    }

    @GetMapping
    public ResponseEntity<PageResponse<UserDto>> getAllUser(
            @RequestParam(name = "page_number", defaultValue = "0") int number,
            @RequestParam(name = "page_size", defaultValue = "10") int size,
            Authentication auth) {
        return ResponseEntity.ok(
                userService.getAllUsers(number, size, auth)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> banUser(
            @PathVariable("id") Long userId,
            Authentication auth) {
        userService.banUser(userId, auth);
        return ResponseEntity.accepted().build();
    }

}
