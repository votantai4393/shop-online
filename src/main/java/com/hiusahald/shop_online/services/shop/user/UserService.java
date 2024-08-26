package com.hiusahald.shop_online.services.shop.user;

import com.hiusahald.shop_online.dto.response.PageResponse;
import com.hiusahald.shop_online.dto.response.UserResponse;
import org.springframework.security.core.Authentication;

public interface UserService {

    UserResponse getUser(Long id, Authentication authentication);

    PageResponse<UserResponse> getAllUser(int pageNumber, int pageSize,
            Authentication authentication);

    PageResponse<UserResponse> search(String content, int pageNumber, int pageSize,
            Authentication authentication);

    void banUser(Long id, Authentication authentication);

}
