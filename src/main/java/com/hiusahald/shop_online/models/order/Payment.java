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

    @Column(unique = true)
    private String transactionId;

    private Double amount;

    private String method;

    private String status;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @OneToOne
    @JoinColumn(name = "orderId")
    private Order order;

}
