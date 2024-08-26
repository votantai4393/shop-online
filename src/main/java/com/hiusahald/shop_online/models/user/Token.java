package com.hiusahald.shop_online.models.user;

import com.hiusahald.shop_online.models.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Entity
@Table(name = "tokens")
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public class Token extends BaseEntity {

    @Column(unique = true)
    private String code;

    private LocalDateTime expiresAt;

    private LocalDateTime validatedAt;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @Transient
    public boolean isExpires() {
        return this.getExpiresAt().isBefore(LocalDateTime.now());
    }

}
