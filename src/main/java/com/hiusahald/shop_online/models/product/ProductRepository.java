package com.hiusahald.shop_online.models.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("""
           select p from Product p
           where p.category.id = :categoryId
           """)
    Optional<Product> findProductByCategoryId(@Param("categoryId") Long categoryId);
}