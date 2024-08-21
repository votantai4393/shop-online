package com.hiusahald.shop_online.util;

import java.security.SecureRandom;

public class CommonUtil {

    public static String generateCode(int length) {
        var random = new SecureRandom();
        var s = new StringBuilder();
        for (int i = 0; i < length; i++) {
            s.append(random.nextInt(0,10));
        }
        return s.toString();
    }

}
