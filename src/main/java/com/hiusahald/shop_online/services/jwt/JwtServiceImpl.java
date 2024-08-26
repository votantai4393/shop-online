package com.hiusahald.shop_online.services.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtServiceImpl implements JwtService {

    @Value("${app.security.jwt.secret-key}")
    private String secretKey;
    @Value("${app.security.jwt.expiration}")
    private Long expiration;

    @NonNull
    private SecretKey getSigningKey() {
        var bytes = this.secretKey.getBytes();
        return Keys.hmacShaKeyFor(
                Base64.getEncoder().encode(bytes)
        );
    }

    @Override
    public String generateToken(@NonNull UserDetails userDetails, Map<String, Object> claims) {
        var now = new Date();
        var expires = new Date(now.getTime() + this.expiration);
        return Jwts.builder()
                .signWith(this.getSigningKey())
                .subject(userDetails.getUsername())
                .claims(claims)
                .claim("authorities", userDetails.getAuthorities())
                .issuedAt(now)
                .expiration(expires)
                .compact();
    }

    @Override
    public String generateToken(UserDetails userDetails) {
        return this.generateToken(userDetails, new HashMap<>());
    }

    @Override
    public String getUsername(String token) {
        return this.getClaim(token, Claims::getSubject);
    }

    @Override
    public Date getExpiration(String token) {
        return this.getClaim(token, Claims::getExpiration);
    }

    @Override
    public <T> T getClaim(String token, @NonNull Function<Claims, T> resolver) {
        return resolver.apply(this.getAllClaims(token));
    }

    @Override
    public Object getClaim(String token, String key) {
        return this.getClaim(token, claims -> claims.get(key));
    }

    private Claims getAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(this.getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    @Override
    public boolean isTokenExpired(String token) {
        return this.getExpiration(token).before(new Date());
    }

    @Override
    public boolean isTokenValid(String token, @NonNull UserDetails userDetails) {
        return this.getUsername(token).equals(userDetails.getUsername())
                && !this.isTokenExpired(token);
    }

}
