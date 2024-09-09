package com.hiusahald.shop_online.services.auth;

import com.hiusahald.shop_online.constants.EmailTemplate;
import com.hiusahald.shop_online.constants.ROLE;
import com.hiusahald.shop_online.dto.request.LoginForm;
import com.hiusahald.shop_online.dto.request.RegisterForm;
import com.hiusahald.shop_online.exceptions.*;
import com.hiusahald.shop_online.models.user.Role;
import com.hiusahald.shop_online.models.user.Token;
import com.hiusahald.shop_online.models.user.User;
import com.hiusahald.shop_online.repositories.RoleRepository;
import com.hiusahald.shop_online.repositories.TokenRepository;
import com.hiusahald.shop_online.repositories.UserRepository;
import com.hiusahald.shop_online.services.email.EmailProperties;
import com.hiusahald.shop_online.services.email.EmailService;
import com.hiusahald.shop_online.services.jwt.JwtService;
import com.hiusahald.shop_online.util.CommonUtil;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final TokenRepository tokenRepository;
    private final EmailService emailService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authManager;

    @Value("${app.mailing.activate-account.token-expiration}")
    private Long tokenExpiration;
    @Value("${app.mailing.activate-account.redirect-url}")
    private String redirectUrl;
    @Value("${app.mailing.activate-account.token-length}")
    private Integer tokenLength;

    @Override
    @Transactional
    public void register(@NonNull RegisterForm form) {
        Optional<User> existedUser = this.userRepository.findByEmail(form.email());
        if (existedUser.isEmpty()) {
            Role role = this.roleRepository.findByName(ROLE.USER.name())
                    .orElseGet(
                            () -> {
                                Role userRole = Role.builder()
                                        .name(ROLE.USER.name())
                                        .build();
                                return this.roleRepository.save(userRole);
                            }
                    );
            Set<Role> roles = new HashSet<>();
            roles.add(role);
            String encoded = this.passwordEncoder.encode(form.password());
            User newUser = User.builder()
                    .email(form.email())
                    .password(encoded)
                    .firstname(form.firstname())
                    .lastname(form.lastname())
                    .roles(roles)
                    .build();
            newUser = this.userRepository.save(newUser);
            sendActivateEmail(newUser);
            return;
        }
        existedUser.map(user -> {
            if (!user.isEnabled()) {
                throw new AccountExistedException("Account existed!");
            }
            throw new AccountUnverifiedException("Account is waiting to activate!");
        });
    }

    @Override
    public AuthenticationResponse authenticate(@NonNull LoginForm form) {
        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(form.email(), form.password());
        Authentication auth = this.authManager.authenticate(token);
        User user = (User) auth.getPrincipal();
        String jwt = this.jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwt)
                .build();
    }

    @Override
    @Transactional
    public void activateAccount(String code) {
        Token token = this.tokenRepository.findByCode(code)
                .orElseThrow(() -> new IncorrectTokenException("Token not found! | Token: " + code));
        User user = token.getUser();
        if (user.isEnabled()) {
            throw new TokenActivatedExeption("Token hash been activated!");
        }
        if (token.isExpires()) {
            this.sendActivateEmail(user);
            throw new TokenExpiredException("Token has been expired, a new email has been sent!");
        }
        token.setValidatedAt(LocalDateTime.now());
        user.setEnabled(true);
        userRepository.save(user);
    }

    private void sendActivateEmail(@NonNull User user) {
        String code = saveCode(user, this.tokenLength, this.tokenExpiration);
        this.emailService.send(
                user.getEmail(),
                EmailTemplate.ACTIVATE_ACCOUNT,
                EmailProperties.builder()
                        .url(this.redirectUrl)
                        .code(code)
                        .username(user.getFullName())
                        .build()
        );
    }

    private String saveCode(User user, int length, long expiration) {
        long millisecondsToMinutes = expiration / (60 * 1000);
        LocalDateTime expires = LocalDateTime.now().plusMinutes(millisecondsToMinutes);
        String code = CommonUtil.generateCode(length);
        Token token = Token.builder()
                .code(code)
                .user(user)
                .expiresAt(expires)
                .build();
        token = this.tokenRepository.save(token);
        return token.getCode();
    }

}
