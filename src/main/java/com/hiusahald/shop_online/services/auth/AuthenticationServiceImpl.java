package com.hiusahald.shop_online.services.auth;

import com.hiusahald.shop_online.constants.EmailTemplate;
import com.hiusahald.shop_online.constants.ROLE;
import com.hiusahald.shop_online.dto.response.AuthenticationResponse;
import com.hiusahald.shop_online.dto.request.LoginRequest;
import com.hiusahald.shop_online.dto.request.RegisterRequest;
import com.hiusahald.shop_online.exceptions.ResourceNotFoundException;
import com.hiusahald.shop_online.exceptions.TokenActivationException;
import com.hiusahald.shop_online.models.user.*;
import com.hiusahald.shop_online.services.email.EmailProperties;
import com.hiusahald.shop_online.services.email.EmailService;
import com.hiusahald.shop_online.services.jwt.JwtService;
import com.hiusahald.shop_online.util.CommonUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Map;

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
    public void register(RegisterRequest request) {
        User user = User.builder()
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .firstname(request.firstname())
                .lastname(request.lastname())
                .build();
        user.getRoles().add(getUserRole());
        user = userRepository.save(user);
        sendActivateEmail(user);
    }

    private Role getUserRole() {
        return roleRepository.findByName(ROLE.USER.name())
                .orElseGet(() -> {
                    Role userRole = Role.builder()
                            .name(ROLE.USER)
                            .build();
                    return roleRepository.save(userRole);
                });
    }

    private void sendActivateEmail(User user) {
        emailService.send(user.getEmail(),
                EmailTemplate.ACTIVATE_ACCOUNT,
                EmailProperties.builder()
                        .url(redirectUrl)
                        .code(saveCode(user, tokenLength, tokenExpiration))
                        .username(user.getFullName())
                        .build()
        );
    }

    private String saveCode(User user, int length, long expiration) {
        Token token = Token.builder()
                .code(CommonUtil.generateCode(length))
                .user(user)
                .expiresAt(LocalDateTime.now().plusMinutes(expiration))
                .build();
        token = tokenRepository.save(token);
        return token.getCode();
    }

    @Override
    public AuthenticationResponse authenticate(LoginRequest request) {
        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(request.email(), request.password());
        Authentication auth = authManager.authenticate(token);
        User user = (User) auth.getPrincipal();
        String jwt = jwtService.generateToken(user, Map.of("fullName", user.getFullName()));
        return new AuthenticationResponse(jwt);
    }

    @Override
    @Transactional
    public void activateAccount(String code) {
        Token token = tokenRepository.findByCode(code)
                .orElseThrow(() -> new ResourceNotFoundException("Token not found with code: " + code));
        User user = token.getUser();
        if (user.isEnabled()) {
            throw new TokenActivationException("Account has been activated with email: " + user.getEmail());
        }
        if (token.isExpires()) {
            sendActivateEmail(user);
            throw new TokenActivationException("Token has been expired, a new email has been sent to "
                    + user.getEmail());
        }
        token.setValidatedAt(LocalDateTime.now());
        user.setEnabled(true);
        userRepository.save(user);
    }

}
