package com.hiusahald.shop_online.authentication;

import com.hiusahald.shop_online.exception.exceptions.ResentEmailException;
import com.hiusahald.shop_online.exception.exceptions.TokenExpiredException;
import com.hiusahald.shop_online.exception.exceptions.UserExistedException;
import com.hiusahald.shop_online.mail.EmailService;
import com.hiusahald.shop_online.mail.EmailTemplate;
import com.hiusahald.shop_online.mail.EmailTemplateProperties;
import com.hiusahald.shop_online.user.User;
import com.hiusahald.shop_online.user.UserRepository;
import com.hiusahald.shop_online.user.role.Role;
import com.hiusahald.shop_online.user.role.RoleEnum;
import com.hiusahald.shop_online.user.role.RoleRepository;
import com.hiusahald.shop_online.user.token.Token;
import com.hiusahald.shop_online.user.token.TokenRepository;
import com.hiusahald.shop_online.util.CommonUtil;
import com.hiusahald.shop_online.util.JwtUtil;
import jakarta.mail.MessagingException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Value("${app.mailing.url.activate-account}")
    private String activateUrl;

    @Override
    @Transactional
    public void signup(SignupForm form) throws MessagingException {
        var userExisted = userRepository.findByEmail(form.email());
        if (userExisted.isEmpty()) {
            var role = Role.builder()
                    .role(RoleEnum.ROLE_USER)
                    .build();
            var newUser = User.builder()
                    .firstname(form.firstname())
                    .lastname(form.lastname())
                    .email(form.email())
                    .password(passwordEncoder.encode(form.password()))
                    .roles(List.of(role))
                    .build();
            newUser = userRepository.save(newUser);
            sendEmail(newUser);
            return;
        }
        var user = userExisted.orElseThrow(EntityNotFoundException::new);
        if (!user.isEnabled()) {
            sendEmail(user);
            throw new ResentEmailException();
        }
        throw new UserExistedException("User already exists! Email: " + form.email());
    }

    @Override
    public AuthResponse login(LoginForm form) {
        var auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(form.email(), form.password())
        );
        var user = (User) auth.getPrincipal();
        var claims = Map.<String, Object>of("full_name", user.getFullName());
        return AuthResponse.builder()
                .token(JwtUtil.generateToken(user, claims))
                .build();
    }

    @Override
    @Transactional
    public void activateAccount(String token) throws MessagingException {
        var tokenExisted = tokenRepository.findByToken(token)
                .orElseThrow(EntityNotFoundException::new);
        var user = tokenExisted.getUser();
        if (tokenExisted.getExpiresAt().isBefore(LocalDateTime.now())) {
            sendEmail(user);
            throw new TokenExpiredException();
        }
        user.setEnabled(true);
        tokenExisted.setValidatedAt(LocalDateTime.now());
        userRepository.save(user);
    }

    private void sendEmail(User user) throws MessagingException {
        emailService.send(
                user.getEmail(),
                EmailTemplate.ACTIVATE_ACCOUNT,
                EmailTemplateProperties.builder()
                        .url(activateUrl)
                        .username(user.getFullName())
                        .token(generateToken(user))
                        .build()
        );
    }

    private String generateToken(User user) {
        var token = Token.builder()
                .createdAt(LocalDateTime.now())
                .user(user)
                .token(CommonUtil.generateCode(6))
                .expiresAt(LocalDateTime.now().plusMinutes(15))
                .build();
        token = tokenRepository.save(token);
        return token.getToken();
    }

}
