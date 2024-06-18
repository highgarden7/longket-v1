package com.longketdan.longket.v1.util;

import com.longketdan.longket.config.jwt.JwtBody;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class Util {

    public static Long getUserIdByToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        JwtBody jwtBody = (JwtBody) authentication.getPrincipal();
        Integer userId = (Integer) jwtBody.getInfoList().get("userId");

        return userId.longValue();
    }
}
