package com.hiusahald.shop_online.repositories;

import com.hiusahald.shop_online.entities.auth.Token;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<Token, Long> {
}
