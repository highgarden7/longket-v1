package com.longketdan.longket.v1.service.Auth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.longketdan.longket.config.exception.CustomException;
import com.longketdan.longket.config.exception.ErrorCode;
import com.longketdan.longket.v1.controller.dto.TokenDTO;
import com.longketdan.longket.v1.controller.dto.UserDTO;
import com.longketdan.longket.v1.model.entity.user.User;
import com.longketdan.longket.v1.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class OauthLoginService {

    private final GoogleAuthService googleAuthService;

    private final KakaoAuthService kakaoAuthService;

    private final UserService userService;

    private final TokenService tokenService;

    public TokenDTO socialLogin(String code, String registrationId) throws JsonProcessingException {
        UserDTO.OauthResponse user;

        switch (registrationId) {
            case "google" :
                log.info("google oauth login...");
                user = googleAuthService.getUserResource(code);

                break;
            case "kakao" :
                log.info("kakao oauth login...");
                user = kakaoAuthService.getUserResource(code);

                break;
            default:
                throw new CustomException(ErrorCode.INVALID_REGISTRATION_ID);
        }

        if (!userService.isExistByEmail(user.getEmail())) {
            // 회원가입
            log.info("user insert____ email : {}", user.getEmail());

            User newUser = new User();
            newUser.setProvider(registrationId);
            newUser.setProviderId(user.getProviderId());
            newUser.setEmail(user.getEmail());
            newUser.setDelYn("N");

            userService.insertUser(newUser);

            return tokenService.insertToken(user.getEmail(), false);
        } else {
            User userData = userService.getUserByEmailAndDelYn(user.getEmail(), "N");
            // JWT 발급
            if (userData.getNickName() == null)
                return tokenService.insertToken(user.getEmail(), false);
            else
                return tokenService.insertToken(user.getEmail(), true);
        }
    }

    public TokenDTO refreshToken(String refreshToken, String email) throws JsonProcessingException {
        return tokenService.refreshToken(refreshToken, email);
    }

    public boolean expiredToken(String email) {
        return tokenService.expiredToken(email);
    }

    public TokenDTO generateTestToken() {
        return tokenService.generateTestToken();
    }
}
