package com.hiusahald.shop_online.services.shop.user;

import com.hiusahald.shop_online.dto.response.PageResponse;
import com.hiusahald.shop_online.dto.UserDto;
import org.springframework.security.core.Authentication;

public interface UserService {

    UserDto getUser(Long userId, Authentication authentication);

    PageResponse<UserDto> getAllUsers(int number, int size, Authentication authentication);

    void banUser(Long userId, Authentication authentication);

}
