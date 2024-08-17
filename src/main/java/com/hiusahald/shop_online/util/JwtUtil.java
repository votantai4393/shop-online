package com.hiusahald.shop_online.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;

import javax.crypto.SecretKey;
import java.util.*;
import java.util.function.Function;

public class JwtUtil {

    @Value("${spring.application.authentication.jwt.secret-key}")
    private static String secretKey;

    @Value("${spring.application.authentication.jwt.expiration}")
    private static Long expiration;

    @NonNull
    private static SecretKey getSecretKey() {
        var encodedBytes = Base64.getEncoder().encode(secretKey.getBytes());
        return Keys.hmacShaKeyFor(encodedBytes);
    }

    private static String generateToken(UserDetails userDetails, Map<String, Objects> claims) {
        Objects.requireNonNull(userDetails);
        var now = new Date();
        return Jwts.builder()
                   .signWith(getSecretKey())
                   .subject(userDetails.getUsername())
                   .claims(claims)
                   .issuedAt(now)
                   .expiration(new Date(now.getTime() + expiration))
                   .compact();
    }

    public static String generateToken(UserDetails userDetails) {
        return generateToken(userDetails, new HashMap<>());
    }

    private static Claims getAllClaims(String token) {
        return Jwts.parser()
                   .verifyWith(getSecretKey())
                   .build()
                   .parseSignedClaims(token)
                   .getPayload();
    }

    public static <T> T getClaim(String token, Function<Claims, T> resolver) {
        Objects.requireNonNull(resolver);
        return resolver.apply(getAllClaims(token));
    }

    public static String getUsername(String token) {
        return getClaim(token, Claims::getSubject);
    }

    public static boolean isTokenValid(UserDetails userDetails, String token) {
        Objects.requireNonNull(userDetails);
        return Objects.equals(userDetails.getUsername(), getUsername(token))
                && getClaim(token, Claims::getExpiration).before(new Date());
    }

}
