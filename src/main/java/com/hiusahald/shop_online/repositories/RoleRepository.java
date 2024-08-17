package com.hiusahald.shop_online.repositories;

import com.hiusahald.shop_online.entities.auth.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
