package com.hiusahald.shop_online.authentication;

import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody @Valid SignupForm form) {
        authService.signup(form);
        return ResponseEntity.accepted().build();
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid LoginForm form) {
        AuthResponse response = authService.login(form);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/activate-account")
    public ResponseEntity<?> activateAccount(@PathVariable String token) {
        authService.activateAccount(token);
        return ResponseEntity.accepted().build();
    }

}
