package com.hiusahald.shop_online.services.auth;

import com.hiusahald.shop_online.dto.response.AuthenticationResponse;
import com.hiusahald.shop_online.dto.request.LoginRequest;
import com.hiusahald.shop_online.dto.request.RegisterRequest;

public interface AuthenticationService {

    void register(RegisterRequest request);

    AuthenticationResponse authenticate(LoginRequest request);

    void activateAccount(String token);

}
