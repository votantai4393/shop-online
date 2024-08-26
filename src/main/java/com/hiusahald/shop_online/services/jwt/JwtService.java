package com.hiusahald.shop_online.services.jwt;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;
import java.util.Map;
import java.util.function.Function;

public interface JwtService {

    String generateToken(UserDetails userDetails, Map<String, Object> claims);

    String generateToken(UserDetails userDetails);

    <T> T getClaim(String token, Function<Claims, T> resolver);

    Object getClaim(String token, String key);

    String getUsername(String token);

    Date getExpiration(String token);

    boolean isTokenExpired(String token);

    boolean isTokenValid(String token, UserDetails userDetails);

}
