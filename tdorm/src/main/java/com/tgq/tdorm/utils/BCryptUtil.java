package com.tgq.tdorm.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @Author tgq
 * @Date 2020/12/18 17:03
 */
public class BCryptUtil {

    private static BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public static String encode(String pwd){
        return encoder.encode(pwd);
    }
}
