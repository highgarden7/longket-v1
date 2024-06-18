package com.longketdan.longket.v1.controller.dto;

import lombok.Data;

@Data
public class GoogleTokenDTO {
    public String access_token;

    public Long expires_in;

    public String scope;

    public String token_type;

    public String id_token;
}
