package com.hiusahald.shop_online.repositories;

import com.hiusahald.shop_online.models.product.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}