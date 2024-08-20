package com.hiusahald.shop_online.authentication;

import com.hiusahald.shop_online.user.UserRepository;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    @Override
    public void signup(SignupForm form) throws MessagingException {

    }

    @Override
    public AuthResponse login(LoginForm form) {
        return null;
    }

    @Override
    public void activateAccount(String token) throws MessagingException {

    }
}
