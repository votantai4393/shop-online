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

    private String country;

    private String city;

    private String detail;

    private String postalCode;

    private String department;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

}
