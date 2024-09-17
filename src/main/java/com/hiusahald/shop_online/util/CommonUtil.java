package com.hiusahald.shop_online.util;

import com.hiusahald.shop_online.constants.ROLE;
import com.hiusahald.shop_online.exceptions.ResourceNotAvailableException;
import com.hiusahald.shop_online.models.user.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.security.SecureRandom;
import java.util.Objects;

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

    public static boolean isAdmin(User user) {
        return user.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(role -> role.equals(ROLE.ADMIN.name()));
    }

    public static User getCurentUser(Authentication auth) {
        return (User) auth.getPrincipal();
    }

    public static void validateAdminAccess(User currentUser) {
        if (!isAdmin(currentUser)) {
            throw new ResourceNotAvailableException("Not allowed to access this resource!");
        }
    }

    public static void requireAdminAccess(Authentication auth) {
        User currentUser = getCurentUser(auth);
        if (!isAdmin(currentUser)) {
            throw new ResourceNotAvailableException("Not allowed to access this resource!");
        }
    }

}
