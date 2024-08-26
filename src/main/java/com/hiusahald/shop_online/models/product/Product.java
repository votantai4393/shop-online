package com.hiusahald.shop_online.models.product;

import com.hiusahald.shop_online.models.BaseEntity;
import com.hiusahald.shop_online.models.cart.CartItem;
import com.hiusahald.shop_online.models.order.OrderItem;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "products")
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public class Product extends BaseEntity {

    private String name;

    private Double price;

    private Integer inventory;

    private String description;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "thumbnailId")
    private Image thumbnail;

    @ManyToOne
    @JoinColumn(name = "categoryId")
    private Category category;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Image> images = new HashSet<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<OrderItem> orderItems = new HashSet<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CartItem> cartItems = new HashSet<>();

}
