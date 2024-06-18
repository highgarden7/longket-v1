package com.longketdan.longket.v1.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.longketdan.longket.config.exception.CustomException;
import com.longketdan.longket.config.exception.ErrorCode;
import com.longketdan.longket.config.exception.CommonResponse;
import com.longketdan.longket.v1.service.Auth.OauthLoginService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final OauthLoginService loginService;

    // OAuth 로그인
    @GetMapping("/login/{registrationId}")
    @Operation(summary = "Oauth Login 요청, ", description = "JWT 발급을 위한 로그인 요청")
    public ResponseEntity<Object> OAuthLogin(@RequestParam(name = "code") String code,
                                             @PathVariable(value = "registrationId") String registrationId) throws JsonProcessingException, UnsupportedEncodingException {
        String decodedCode = URLDecoder.decode(code, StandardCharsets.UTF_8.name());
        return CommonResponse.dataResponseEntity(loginService.socialLogin(decodedCode, registrationId));
    }

    // Refresh Token 갱신
    @GetMapping("/refresh/{email}")
    @Operation(summary = "Refresh Token 요청, ", description = "JWT 발급을 위한 refresh token 요청")
    public ResponseEntity<Object> refreshToken(@RequestParam(name = "refreshToken") String refreshToken,
                                               @PathVariable(value = "email") String email) throws JsonProcessingException {
        return CommonResponse.dataResponseEntity(loginService.refreshToken(refreshToken, email));
    }

    @GetMapping("/logout/{email}")
    @Operation(summary = "로그아웃", description = "token을 폐기하고 로그아웃")
    public ResponseEntity<Object> logout(@PathVariable(value = "email") String email) {
        if (loginService.expiredToken(email)) return CommonResponse.successResponseEntity();
        throw new CustomException(ErrorCode.NOT_FOUND);
    }

    @GetMapping("/login/test/dev")
    @Operation(summary = "테스트 토큰 발급", description = "개발용 테스트 토큰 발급")
    public ResponseEntity<Object> testLogin() {

        return CommonResponse.dataResponseEntity(loginService.generateTestToken());
    }
}
