package com.hiusahald.shop_online.models.cart;

import com.hiusahald.shop_online.models.BaseEntity;
import com.hiusahald.shop_online.models.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "carts")
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public class Cart extends BaseEntity {

    @OneToOne
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CartItem> items = new HashSet<>();

}
