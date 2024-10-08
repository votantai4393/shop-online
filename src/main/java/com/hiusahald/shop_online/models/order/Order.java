package com.hiusahald.shop_online.models.order;

import com.hiusahald.shop_online.constants.OrderStatus;
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
@Table(name = "orders")
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public class Order extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL)
    private Payment payment;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<OrderItem> items = new HashSet<>();

}
