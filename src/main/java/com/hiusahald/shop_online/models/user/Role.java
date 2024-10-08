package com.hiusahald.shop_online.models.user;

import com.hiusahald.shop_online.constants.ROLE;
import com.hiusahald.shop_online.models.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "roles")
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public class Role extends BaseEntity {

    @Column(unique = true, nullable = false)
    @Enumerated(EnumType.STRING)
    private ROLE name;

    @ManyToMany(mappedBy = "roles")
    private Set<User> users = new HashSet<>();

}
