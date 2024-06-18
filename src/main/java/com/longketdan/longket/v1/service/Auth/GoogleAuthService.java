package com.longketdan.longket.v1.service.Auth;

import com.longketdan.longket.v1.controller.dto.GoogleTokenDTO;
import com.longketdan.longket.v1.controller.dto.GoogleUserDTO;
import com.longketdan.longket.v1.controller.dto.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class GoogleAuthService {
    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${app.google.clientId}")
    private String clientId;
    @Value("${app.google.clientSecret}")
    private String clientSecret;
    @Value("${app.google.redirectUri}")
    private String redirectUri;
    @Value("${app.google.tokenUri}")
    private String tokenUri;

    @Value("${app.google.resourceUri}")
    private String resourceUri;

    public UserDTO.OauthResponse getUserResource(String authorizationCode) {
        log.info("getting google user resource...");
        GoogleTokenDTO token = getAccessToken(authorizationCode);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token.getAccess_token());
        HttpEntity entity = new HttpEntity(headers);

        GoogleUserDTO googleUser = restTemplate.exchange(resourceUri, HttpMethod.GET, entity, GoogleUserDTO.class).getBody();
        return new UserDTO.OauthResponse(googleUser.getId(), "google", googleUser.getEmail());
    }

    private GoogleTokenDTO getAccessToken(String authorizationCode) {
        log.info("getting google access token...");
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("code", authorizationCode);
        params.add("client_id", clientId);
        params.add("client_secret", clientSecret);
        params.add("redirect_uri", redirectUri);
        params.add("grant_type", "authorization_code");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity entity = new HttpEntity(params, headers);

        ResponseEntity<GoogleTokenDTO> token = restTemplate.exchange(tokenUri, HttpMethod.POST, entity, GoogleTokenDTO.class);

        return token.getBody();
    }
}



