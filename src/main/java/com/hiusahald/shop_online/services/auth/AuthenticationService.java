package com.hiusahald.shop_online.services.auth;

import com.hiusahald.shop_online.dto.request.LoginForm;
import com.hiusahald.shop_online.dto.request.RegisterForm;

public interface AuthenticationService {

    void register(RegisterForm form);

    AuthenticationResponse authenticate(LoginForm form);

    void activateAccount(String token);

}
