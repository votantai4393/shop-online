package com.hiusahald.shop_online.user.role;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class RoleInitialization {

    private final RoleRepository roleRepository;

    @Bean
    public CommandLineRunner initRoles() {
        return args -> {
            roleRepository.save(
                    Role.builder()
                            .role(RoleEnum.ROLE_ADMIN)
                            .build()
            );

            roleRepository.save(
                    Role.builder()
                            .role(RoleEnum.ROLE_USER)
                            .build()
            );
        };

    }
}
