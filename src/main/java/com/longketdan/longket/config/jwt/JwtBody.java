package com.longketdan.longket.config.jwt;

import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtBody {

    private String sub;

    private String iat;

    private Date iss;

    private String aud;

    private Date exp;

    private Map<String, Object> infoList;

    private String role;

    public static JwtBody of(Claims bodyClaims){
        JwtBody jwtBody = new JwtBody();
        jwtBody.sub = bodyClaims.getSubject();
        jwtBody.iat = bodyClaims.getIssuer();
        jwtBody.iss = bodyClaims.getIssuedAt();
        jwtBody.aud = bodyClaims.getAudience();
        jwtBody.exp = bodyClaims.getExpiration();
        jwtBody.infoList = bodyClaims.get("info", Map.class);
        jwtBody.role = bodyClaims.get("role").toString();

        return jwtBody;
    }

}
