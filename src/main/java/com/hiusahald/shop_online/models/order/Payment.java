package com.hiusahald.shop_online.models.order;

import com.hiusahald.shop_online.models.BaseEntity;
import com.hiusahald.shop_online.models.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "payments")
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public class Payment extends BaseEntity {

    @Column(unique = true, nullable = false)
    private String transactionId;

    @Column(nullable = false)
    private Double amount;

    @Column(nullable = false)
    private String method;

    @Column(nullable = false)
    private String status;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    @OneToOne
    @JoinColumn(name = "orderId", nullable = false)
    private Order order;

}
