package com.hiusahald.shop_online.authentication;

import com.hiusahald.shop_online.constants.CommonConstant;
import com.hiusahald.shop_online.constants.ROLE;
import com.hiusahald.shop_online.exception.exceptions.PasswordNotMatchException;
import com.hiusahald.shop_online.exception.exceptions.ResentEmailException;
import com.hiusahald.shop_online.exception.exceptions.TokenActivationException;
import com.hiusahald.shop_online.exception.exceptions.UserExistedException;
import com.hiusahald.shop_online.jwt.JwtService;
import com.hiusahald.shop_online.email.EmailService;
import com.hiusahald.shop_online.constants.EmailTemplate;
import com.hiusahald.shop_online.email.EmailProperties;
import com.hiusahald.shop_online.shop.user.User;
import com.hiusahald.shop_online.shop.user.UserRepository;
import com.hiusahald.shop_online.shop.user.role.Role;
import com.hiusahald.shop_online.shop.user.role.RoleRepository;
import com.hiusahald.shop_online.shop.user.role.UserRole;
import com.hiusahald.shop_online.shop.user.token.Token;
import com.hiusahald.shop_online.shop.user.token.TokenRepository;
import com.hiusahald.shop_online.util.CommonUtil;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final TokenRepository tokenRepository;
    private final EmailService emailService;
    private final JwtService jwtService;
    private final CommonUtil commonUtil;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Value("${app.mailing.url.activate-account}")
    private final String activateUrl;

    @Override
    @Transactional
    public void signup(SignupForm form) {
        if (!form.password().equals(form.confirmPassword())) {
            throw new PasswordNotMatchException(
                    String.format("Password not match! [password: %s, confirmed password: %s]",
                            form.password(), form.confirmPassword())
            );
        }
        var userExisted = userRepository.findByEmail(form.email());
        if (userExisted.isEmpty()) {
            User newUser = User.builder()
                    .firstname(form.firstname())
                    .lastname(form.lastname())
                    .email(form.email())
                    .password(passwordEncoder.encode(form.password()))
                    .build();
            Role role = roleRepository.findByRole(ROLE.USER)
                    .orElseThrow(() -> new EntityNotFoundException(
                            String.format("Role %s wasn't initialized yet!", ROLE.USER.name()))
                    );
            List<UserRole> userRoles = List.of(
                    UserRole.builder()
                            .role(role)
                            .user(newUser)
                            .build()
            );
            newUser.setRoles(userRoles);
            newUser = userRepository.save(newUser);
            sendEmail(newUser);
            return;
        }
        var user = userExisted.get();
        if (!user.isEnabled()) {
            sendEmail(user);
            throw new ResentEmailException("An email sent to email: " + user.getEmail());
        }
        throw new UserExistedException("User already exists! Email: " + form.email());
    }

    private void sendEmail(User user) {
        EmailProperties props = EmailProperties.builder()
                .url(activateUrl)
                .username(user.getFullName())
                .code(generateToken(user))
                .build();
        emailService.send(
                user.getEmail(),
                EmailTemplate.ACTIVATE_ACCOUNT,
                props
        );
    }

    private String generateToken(User user) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expires = now.plus(CommonConstant.TOKEN_EXPIRATION, ChronoUnit.MILLIS);
        String code = commonUtil.generateCode(CommonConstant.TOKEN_LENGTH);
        Token token = Token.builder()
                .createdAt(now)
                .user(user)
                .token(code)
                .expiresAt(expires)
                .build();
        token = tokenRepository.save(token);
        return token.getToken();
    }

    @Override
    public AuthResponse login(LoginForm form) {
        var auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(form.email(), form.password())
        );
        var user = (User) auth.getPrincipal();
        var claims = Map.<String, Object>of("full_name", user.getFullName());
        var token = jwtService.generateToken(user, claims);
        return AuthResponse.builder()
                .token(token)
                .build();
    }

    @Override
    @Transactional
    public void activateAccount(String token) {
        var tokenExisted = tokenRepository.findByToken(token)
                .orElseThrow(() ->
                        new TokenActivationException(
                                String.format("Token not found! [token: %s]", token)
                        ));
        var user = tokenExisted.getUser();
        if (user.isEnabled()) {
            throw new TokenActivationException(
                    String.format("User activated already!, [email: %s]", user.getEmail())
            );
        }
        if (tokenExisted.getExpiresAt().isBefore(LocalDateTime.now())) {
            sendEmail(user);
            throw new TokenActivationException(
                    String.format("Token expired, A new email has been sent! [token: %s]", token));
        }
        user.setEnabled(true);
        tokenExisted.setValidatedAt(LocalDateTime.now());
        userRepository.save(user);
    }

}
