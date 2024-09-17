package com.hiusahald.shop_online.services.shop.user;

import com.hiusahald.shop_online.dto.response.PageResponse;
import com.hiusahald.shop_online.dto.UserDto;
import com.hiusahald.shop_online.exceptions.ResourceNotFoundException;
import com.hiusahald.shop_online.models.user.User;
import com.hiusahald.shop_online.models.user.UserRepository;
import com.hiusahald.shop_online.util.CommonUtil;
import com.hiusahald.shop_online.util.MapperUntil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private User getCurrentUser(Authentication auth) {
        User user = (User) auth.getPrincipal();
        return this.userRepository.findById(user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found! userId: " + user.getId()));
    }

    @Override
    public UserDto getUser(Long userId, Authentication auth) {
        User user = getCurrentUser(auth);
        return MapperUntil.mapToUserDto(user);
    }

    @Override
    public PageResponse<UserDto> getAllUsers(int number, int size, Authentication authentication) {
        CommonUtil.requireAdminAccess(authentication);
        return null;
    }

    @Override
    @Transactional
    public void banUser(Long userId, Authentication auth) {
        User user = getCurrentUser(auth);
        user.setLocked(true);
        userRepository.save(user);
    }

}

