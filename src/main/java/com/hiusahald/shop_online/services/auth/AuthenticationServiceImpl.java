package com.hiusahald.shop_online.services.auth;

import com.hiusahald.shop_online.constants.EmailTemplate;
import com.hiusahald.shop_online.dto.request.LoginForm;
import com.hiusahald.shop_online.dto.request.RegisterForm;
import com.hiusahald.shop_online.exceptions.ResourceNotFoundException;
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
        Role role = this.roleRepository.findByName("USER_ROLE")
                .orElseThrow(() -> new ResourceNotFoundException("Role not found!"));
        User user = User.builder()
                .email(form.email())
                .password(passwordEncoder.encode(form.password()))
                .firstname(form.firstname())
                .lastname(form.lastname())
                .roles(Set.of(role))
                .build();
        user = this.userRepository.save(user);
        sendActivateEmail(user);
    }

    @Override
    public AuthenticationResponse authenticate(@NonNull LoginForm form) {
        Authentication auth = this.authManager.authenticate(
                new UsernamePasswordAuthenticationToken(form.email(), form.password())
        );
        return AuthenticationResponse.builder()
                .token(jwtService.generateToken((User) auth.getPrincipal()))
                .build();
    }

    @Override
    @Transactional
    public void activateAccount(String code) {
        Token token = this.tokenRepository.findByCode(code)
                .orElseThrow(() -> new ResourceNotFoundException("Token not found!"));
        User user = token.getUser();
        if (user.isEnabled()) {
        }
        token.setValidatedAt(LocalDateTime.now());
        user.setEnabled(true);
        userRepository.save(user);
    }

    private void sendActivateEmail(@NonNull User user) {
        this.emailService.send(
                user.getEmail(),
                EmailTemplate.ACTIVATE_ACCOUNT,
                EmailProperties.builder()
                        .url(this.redirectUrl)
                        .code(saveCode(user, this.tokenLength, this.tokenExpiration))
                        .username(user.getFullName())
                        .build()
        );
    }

    private String saveCode(User user, int length, long expiration) {
        long milliSecondToMinutes = expiration / (60 * 1000);
        LocalDateTime expires = LocalDateTime.now().plusMinutes(milliSecondToMinutes);
        Token token = Token.builder()
                .code(CommonUtil.generateCode(length))
                .user(user)
                .expiresAt(expires)
                .build();
        token = this.tokenRepository.save(token);
        return token.getCode();
    }

}
