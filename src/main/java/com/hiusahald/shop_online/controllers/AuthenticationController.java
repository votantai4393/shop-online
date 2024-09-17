package com.hiusahald.shop_online.controllers;

import com.hiusahald.shop_online.dto.request.LoginRequest;
import com.hiusahald.shop_online.dto.request.RegisterRequest;
import com.hiusahald.shop_online.dto.response.AuthenticationResponse;
import com.hiusahald.shop_online.services.auth.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthenticationService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid RegisterRequest request) {
        authService.register(request);
        return ResponseEntity.accepted().build();
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody @Valid LoginRequest request) {
        return ResponseEntity.ok(authService.authenticate(request));
    }

    @PutMapping("/activate-account/{token}")
    public ResponseEntity<?> activateAccount(@PathVariable String token) {
        authService.activateAccount(token);
        return ResponseEntity.accepted().build();
    }

}
