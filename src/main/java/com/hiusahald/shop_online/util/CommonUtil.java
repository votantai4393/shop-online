package com.hiusahald.shop_online.util;

import com.hiusahald.shop_online.constants.ROLE;
import com.hiusahald.shop_online.exceptions.AuthorizationException;
import com.hiusahald.shop_online.models.user.User;
import com.hiusahald.shop_online.dto.response.PageResponse;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.security.SecureRandom;
import java.util.List;
import java.util.function.Function;

public class CommonUtil {

    public static String generateCode(int length) {
        SecureRandom random = new SecureRandom();
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < length; i++) {
            s.append(random.nextInt(0, 10));
        }
        return s.toString();
    }

    public static String getFilename(String origin) {
        return separateFilename(origin)[0];
    }

    public static String getFileExtension(String origin) {
        return separateFilename(origin)[1];
    }

    private static String[] separateFilename(String origin) {
        String[] arr = new String[2];
        int delimiter = origin.lastIndexOf(".");
        arr[0] = origin.substring(0, delimiter);
        arr[1] = origin.substring(delimiter);
        return arr;
    }

    public static <T, R> PageResponse<R> toPageResponse(Page<T> page, Function<T, R> converter) {
        List<R> list = page.stream()
                .map(converter)
                .toList();
        return PageResponse.<R>builder()
                .pageSize(page.getSize())
                .pageNumber(page.getNumber())
                .totalPage(page.getTotalPages())
                .isFirst(page.isFirst())
                .isLast(page.isLast())
                .totalElement(page.getTotalElements())
                .content(list)
                .build();
    }
}
