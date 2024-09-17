package com.hiusahald.shop_online.util;

import com.hiusahald.shop_online.dto.response.PageResponse;
import com.hiusahald.shop_online.models.cart.Cart;
import com.hiusahald.shop_online.models.cart.CartItem;
import com.hiusahald.shop_online.models.product.Category;
import com.hiusahald.shop_online.models.product.Image;
import com.hiusahald.shop_online.models.product.Product;
import com.hiusahald.shop_online.models.user.Address;
import com.hiusahald.shop_online.models.user.User;
import com.hiusahald.shop_online.dto.CartDto;
import com.hiusahald.shop_online.dto.CartItemDto;
import com.hiusahald.shop_online.dto.ProductDto;
import com.hiusahald.shop_online.dto.CategoryDto;
import com.hiusahald.shop_online.dto.UserDto;
import com.hiusahald.shop_online.dto.AddressDto;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.function.Function;

public class MapperUntil {

    public static <T, R> PageResponse<R> toPageResponse(Page<T> page, Function<T, R> converter) {
        List<R> content = page.stream()
                .map(converter)
                .toList();
        return PageResponse.<R>builder()
                .content(content)
                .pageSize(page.getSize())
                .pageNumber(page.getNumber())
                .totalPage(page.getTotalPages())
                .isFirst(page.isFirst())
                .isLast(page.isLast())
                .totalElement(page.getTotalElements())
                .build();
    }

    public static UserDto mapToUserDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .lastname(user.getLastname())
                .firstname(user.getFirstname())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .isAdmin(CommonUtil.isAdmin(user))
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }

    public static AddressDto toAddressDto(Address address) {
        return AddressDto.builder()
                .id(address.getId())
                .country(address.getCountry())
                .city(address.getCity())
                .detail(address.getDetail())
                .postalCode(address.getPostalCode())
                .department(address.getDepartment())
                .createdAt(address.getCreatedAt())
                .updatedAt(address.getUpdatedAt())
                .build();
    }

    public static ProductDto mapToProductDto(Product product) {
        List<String> imagesUrl = product.getImages()
                .stream()
                .map(Image::getUrl)
                .toList();
        return ProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .description(product.getDescription())
                .inventory(product.getInventory())
                .category(mapToCategoryDto(product.getCategory()))
                .thumbnailUrl(product.getThumbnail().getUrl())
                .imageUrls(imagesUrl)
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getUpdatedAt())
                .build();
    }

    public static CategoryDto mapToCategoryDto(Category category) {
        return CategoryDto.builder()
                .id(category.getId())
                .name(category.getName())
                .createdAt(category.getCreatedAt())
                .updatedAt(category.getUpdatedAt())
                .build();
    }

    public static CartDto mapToCartDto(Cart cart) {
        List<CartItemDto> items = cart.getItems()
                .stream()
                .map(MapperUntil::mapToCartItemDto)
                .toList();
        return CartDto.builder()
                .id(cart.getId())
                .userDto(mapToUserDto(cart.getUser()))
                .items(items)
                .createdAt(cart.getCreatedAt())
                .updatedAt(cart.getUpdatedAt())
                .build();
    }

    public static CartItemDto mapToCartItemDto(CartItem item) {
        return CartItemDto.builder()
                .product(mapToProductDto(item.getProduct()))
                .quantity(item.getQuantity())
                .createdAt(item.getCreatedAt())
                .updatedAt(item.getUpdatedAt())
                .build();
    }

}
