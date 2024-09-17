package com.hiusahald.shop_online.services.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.*;
import java.util.function.Function;

@Service
public class JwtServiceImpl implements JwtService {

    @Value("${app.security.jwt.secret-key}")
    private String secretKey;
    @Value("${app.security.jwt.expiration}")
    private Long expiration;

    private SecretKey getSigningKey() {
        byte[] bytes = secretKey.getBytes();
        return Keys.hmacShaKeyFor(
                Base64.getEncoder().encode(bytes)
        );
    }

    @Override
    public String generateToken(UserDetails userDetails, Map<String, Object> claims) {
        return Jwts.builder()
                .signWith(getSigningKey())
                .subject(userDetails.getUsername())
                .claims(claims)
                .claim("authorities", userDetails.getAuthorities())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .compact();
    }

    @Override
    public String generateToken(UserDetails userDetails) {
        return generateToken(userDetails, new HashMap<>());
    }

    @Override
    public String getUsername(String token) {
        return getClaim(token, Claims::getSubject);
    }

    @Override
    public Date getExpiration(String token) {
        return getClaim(token, Claims::getExpiration);
    }

    @Override
    public <T> T getClaim(String token, Function<Claims, T> resolver) {
        return resolver.apply(getAllClaims(token));
    }

    @Override
    public Object getClaim(String token, String key) {
        return getClaim(token, claims -> claims.get(key));
    }

    private Claims getAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    @Override
    public boolean isTokenExpired(String token) {
        return getExpiration(token).before(new Date());
    }

    @Override
    public boolean isTokenValid(String token, UserDetails userDetails) {
        return Objects.equals(getUsername(token), userDetails.getUsername())
                && !isTokenExpired(token);
    }

}
