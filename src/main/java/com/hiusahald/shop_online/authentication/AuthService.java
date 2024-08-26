package com.hiusahald.shop_online.authentication;

import jakarta.mail.MessagingException;

public interface AuthService {

    void signup(SignupForm form);

    AuthResponse login(LoginForm form);

    void activateAccount(String token);

}
