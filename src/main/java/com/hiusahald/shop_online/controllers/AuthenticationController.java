package com.hiusahald.shop_online.controllers;

import com.hiusahald.shop_online.dto.request.LoginForm;
import com.hiusahald.shop_online.dto.request.RegisterForm;
import com.hiusahald.shop_online.services.auth.AuthenticationResponse;
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

    @GetMapping
    public ResponseEntity<?> testApi() {
        return ResponseEntity.accepted().body("Hello world");
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid RegisterForm form) {
        authService.register(form);
        return ResponseEntity.accepted().build();
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody @Valid LoginForm form) {
        return ResponseEntity.ok(authService.authenticate(form));
    }

    @PostMapping("/activate-account/{token}")
    public ResponseEntity<?> activateAccount(@PathVariable String token) {
        authService.activateAccount(token);
        return ResponseEntity.accepted().build();
    }

}
