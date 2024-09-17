package com.hiusahald.shop_online.services.shop.cart;

import com.hiusahald.shop_online.dto.CartDto;
import com.hiusahald.shop_online.dto.request.CartRequest;
import com.hiusahald.shop_online.dto.response.PageResponse;
import com.hiusahald.shop_online.exceptions.ResourceNotFoundException;
import com.hiusahald.shop_online.models.cart.Cart;
import com.hiusahald.shop_online.models.cart.CartItem;
import com.hiusahald.shop_online.models.cart.CartItemRepository;
import com.hiusahald.shop_online.models.cart.CartRepository;
import com.hiusahald.shop_online.models.product.Product;
import com.hiusahald.shop_online.models.product.ProductRepository;
import com.hiusahald.shop_online.models.user.User;
import com.hiusahald.shop_online.models.user.UserRepository;
import com.hiusahald.shop_online.util.MapperUntil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;


    private User getCurrentUser(Authentication authentication) {
        User currentUser = (User) authentication.getPrincipal();
        return userRepository.findById(currentUser.getId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found! userId: " + currentUser.getId()
                        ));
    }

    private Product getProductById(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + productId));
    }

    @Override
    public CartDto getCart(Authentication auth) {
        return null;
    }

    @Override
    public PageResponse<CartDto> getAllCarts(int number, int size, Authentication auth) {
        return null;
    }

    @Override
    @Transactional
    public CartDto addToCart(CartRequest request, Authentication auth) {
        Cart cart = getCurrentUser(auth).getCart();
        cartItemRepository.findItemInCart(cart.getId(), request.productId())
                .ifPresentOrElse(
                        item -> {
                            item.setQuantity(item.getQuantity() + request.quantity());
                            cartItemRepository.save(item);
                        },
                        () -> {
                            Product product = getProductById(request.productId());
                            CartItem cartItem = CartItem.builder()
                                    .cart(cart)
                                    .product(product)
                                    .quantity(request.quantity())
                                    .build();
                            cartItemRepository.save(cartItem);
                        }
                );
        Cart saved = cartRepository.findById(cart.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found with id: " + cart.getId()));
        return MapperUntil.mapToCartDto(saved);
    }

    @Override
    public CartDto removeItemFromCart(CartRequest cartRequest, Authentication auth) {
        return null;
    }

    @Override
    public CartDto updateCart(CartRequest request, Authentication auth) {
        return null;
    }
}
