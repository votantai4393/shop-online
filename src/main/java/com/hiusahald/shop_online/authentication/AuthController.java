package com.hiusahald.shop_online.authentication;

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
        try {
            authService.signup(form);
        } catch (jakarta.mail.MessagingException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.accepted().build();
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid LoginForm form) {
        return ResponseEntity.ok(
                authService.login(form)
        );
    }

    @PostMapping("/activate-account")
    public ResponseEntity<?> activateAccount(@RequestParam String token) {
        try {
            authService.activateAccount(token);
        } catch (jakarta.mail.MessagingException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.accepted().build();
    }
}
