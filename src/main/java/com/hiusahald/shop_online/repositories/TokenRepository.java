package com.hiusahald.shop_online.repositories;

import com.hiusahald.shop_online.models.user.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long> {

    Optional<Token> findByCode(String code);

}