package com.longketdan.longket.v1.service.Auth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.longketdan.longket.config.RedisConfig;
import com.longketdan.longket.config.exception.CustomException;
import com.longketdan.longket.config.exception.ErrorCode;
import com.longketdan.longket.config.jwt.JwtProvider;
import com.longketdan.longket.v1.controller.dto.TokenDTO;
import com.longketdan.longket.v1.model.entity.user.User;
import com.longketdan.longket.v1.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenService {
    private final JwtProvider jwtProvider;
    private final RedisConfig redisConfig;

    private final UserRepository userRepository;

    private static final String USER_KEY = "UserEmail:app:";

    public TokenDTO insertToken(String email, boolean isJoined) throws JsonProcessingException {
        User user = userRepository.findByEmailAndDelYn(email, "N").orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));

        TokenDTO tokenDto = jwtProvider.generateToken(user, isJoined);

        ObjectMapper mapper = new ObjectMapper();

        // redis 에 token 추가
        redisConfig.setData(USER_KEY + user.getEmail(),
                mapper.writeValueAsString(tokenDto), 60 * 60 * 6); // 6시간

        user.setRefreshToken(tokenDto.getRefreshToken());
        userRepository.save(user);

        return tokenDto;
    }


    public TokenDTO refreshToken(String refreshToken, String email) throws JsonProcessingException {
        // DB에서 refreshToken 을 조회.
        User userInfo = userRepository.findByEmailAndDelYn(email, "N")
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));

        // refreshToken validation check
        jwtProvider.validateToken(refreshToken);

        TokenDTO tokenDto = jwtProvider.refreshToken(refreshToken, userInfo);

        // redis에 새로운 token 으로 저장
        ObjectMapper mapper = new ObjectMapper();
        if (userInfo.getRefreshToken() == null || "".equals(userInfo.getRefreshToken())
                || !refreshToken.equals(userInfo.getRefreshToken()))
            throw new CustomException(ErrorCode.NOT_FOUND_TOKEN);

        redisConfig.setData(USER_KEY + userInfo,
                mapper.writeValueAsString(tokenDto), 60 * 60 * 6); // 6시간

        // refreshToken 업데이트
        userInfo.setRefreshToken(tokenDto.getRefreshToken());
        userRepository.save(userInfo);

        return tokenDto;
    }

    /**
     * redis에 토큰 정보 삭제
     *
     * @param email
     * @return
     */
    public boolean expiredToken(String email) {
        return redisConfig.deleteData(USER_KEY + email);
    }

    public TokenDTO generateTestToken() {
        return jwtProvider.getTestToken();
    }
}
