package com.hiusahald.shop_online.models.product;

import com.hiusahald.shop_online.models.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "images")
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public class Image extends BaseEntity {

    @Column(unique = true, nullable = false)
    private String url;

    @ManyToOne
    @JoinColumn(name = "productId", nullable = false)
    private Product product;

}
