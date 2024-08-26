package com.hiusahald.shop_online.repositories;

import com.hiusahald.shop_online.models.user.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}