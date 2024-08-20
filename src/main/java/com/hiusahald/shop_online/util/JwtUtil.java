package com.hiusahald.shop_online.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class JwtUtil {

    @Value("${app.security.jwt.secret-key}")
    private static String secretKey;
    @Value("${app.security.jwt.expiration}")
    private static Long expiration;


    private static SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(
                Base64.getEncoder()
                        .encode(secretKey.getBytes())
        );
    }

    public static String generateToken(UserDetails userDetails, Map<String, Object> addons) {
        return Jwts.builder()
                .signWith(getSigningKey())
                .subject(userDetails.getUsername())
                .claims(addons)
                .claim("authorities", userDetails.getAuthorities())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .compact();
    }

    public static String generateToken(UserDetails userDetails) {
        return generateToken(userDetails, new HashMap<>());
    }

    public static String getUsername(String token) {
        return getClaim(token, Claims::getSubject);
    }

    public static Date getExpiration(String token) {
        return getClaim(token, Claims::getExpiration);
    }

    public static <T> T getClaim(String token, Function<Claims, T> resolver) {
        return resolver.apply(getAllClaims(token));
    }

    public static Object getClaim(String token, String property) {
        return getClaim(token, claims -> claims.get(property));
    }

    private static Claims getAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public static boolean isTokenExpired(String token) {
        return getExpiration(token).before(new Date());
    }

    public static boolean isTokenValid(String token, UserDetails userDetails) {
        return getUsername(token).equals(userDetails.getUsername())
                && !isTokenExpired(token);
    }

}
