package com.hiusahald.shop_online.services.shop.order;

import com.hiusahald.shop_online.constants.EmailTemplate;
import com.hiusahald.shop_online.dto.OrderDto;
import com.hiusahald.shop_online.dto.response.PageResponse;
import com.hiusahald.shop_online.models.order.OrderRepository;
import com.hiusahald.shop_online.models.product.ProductRepository;
import com.hiusahald.shop_online.models.user.User;
import com.hiusahald.shop_online.models.user.UserRepository;
import com.hiusahald.shop_online.services.email.EmailProperties;
import com.hiusahald.shop_online.services.email.EmailService;
import com.hiusahald.shop_online.services.payment.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final PaymentService paymentService;
    private final EmailService emailService;

    @Value("${app.mailing.waiting-payment.redirect-url")
    private String paymentUrl;

    @Override
    @Transactional
    public OrderDto placeOrder(Long userId, OrderDto request, Authentication authentication) {
        return null;
    }

    private void sendEmail(User user) {
        EmailProperties properties = EmailProperties.builder()
                .username(user.getFullName())
                .build();
        emailService.send(user.getEmail(), EmailTemplate.WAITING_PAYMENT, properties);
    }

    @Override
    public OrderDto updateOrder(Long userId, OrderDto request, Authentication authentication) {
        return null;
    }

    @Override
    public void cancelOrder(Long userId, Authentication authentication) {

    }

    @Override
    public OrderDto getOder(Long userId, Long orderId, Authentication authentication) {
        return null;
    }

    @Override
    public PageResponse<OrderDto> getAllOrders(Long userId, Authentication authentication) {
        return null;
    }

    @Override
    public PageResponse<OrderDto> getAllOrders(Authentication authentication) {
        return null;
    }
}
