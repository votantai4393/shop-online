package com.hiusahald.shop_online.repositories;

import com.hiusahald.shop_online.entities.auth.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
