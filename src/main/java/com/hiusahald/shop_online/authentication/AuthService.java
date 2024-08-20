package com.hiusahald.shop_online.authentication;

import jakarta.mail.MessagingException;

public interface AuthService {

    void signup(SignupForm form) throws MessagingException;

    AuthResponse login(LoginForm form);

    void activateAccount(String token) throws MessagingException;

}
