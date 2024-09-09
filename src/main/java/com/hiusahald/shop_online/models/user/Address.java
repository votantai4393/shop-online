package com.hiusahald.shop_online.models.user;

import com.hiusahald.shop_online.models.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "addresses")
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public class Address extends BaseEntity {

    @Column(nullable = false)
    private String country;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String detail;

    @Column(nullable = false)
    private String postalCode;

    private String department;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private User user;

}
