package com.longketdan.longket.config.jwt;

import com.longketdan.longket.config.exception.CustomException;
import com.longketdan.longket.config.exception.ErrorCode;
import com.longketdan.longket.v1.controller.dto.TokenDTO;
import com.longketdan.longket.v1.model.entity.user.User;
import com.longketdan.longket.v1.repository.user.UserRepository;
import io.jsonwebtoken.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtProvider {
    public static final String HEADER = "Authorization";
    @Value("${spring.jwt.secret.key}")
    private String secretKey;

    //private final long EXPIRE_TIME = 1 * 60 * 1000L; // 토큰 유효시간 1분
    private static final long EXPIRE_TIME = 1 * 60 * 30 * 1000L; // 토큰 유효시간 30분
    private static final long REFRESH_EXPIRE_TIME = 60 * 60 * 10 * 1000L; // 리프레시토큰 유효시간 10시간
    private static final String AUTHORIZATION = "Authorization";
    private static final String EXCEPTION = "exception";

    private final UserRepository userRepository;


    /**
     * 일반 Token 생성
     *
     * @param user, isJoined
     * @return
     */
    public TokenDTO generateToken(User user, boolean isJoined) {
        Date now = new Date();
        Map<String, Object> infoList = new HashMap<>();
        User userInfo = userRepository.findByEmailAndDelYn(user.getEmail(), "N").orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));
        infoList.put("userId", userInfo.getId());
        infoList.put("email", userInfo.getEmail());
        infoList.put("provider", userInfo.getProvider());

        Claims claims = Jwts.claims();
        claims.setIssuer("api.longket.com"); // 토큰 발급자
        claims.setIssuedAt(now); // 토큰 발행 시간 정보
        claims.setSubject(userInfo.getEmail()); // 토큰 제목 (user email)
        claims.setAudience("longket.com");// 토큰 대상자
        claims.setExpiration(new Date(now.getTime() + EXPIRE_TIME));  // 발급 만료시간
        claims.put("role", "NONE");
        claims.put("info", infoList);

        // Access Token 생성
        String accessToken = Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setClaims(claims) // 정보 저장
                .setExpiration(new Date(now.getTime() + EXPIRE_TIME)) // 발급 만료시간 refresh token 은 다른날짜를 줘야될거같음.
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();

        // Refresh Token 생성
        String refreshToken = Jwts.builder()
                .setExpiration(new Date(now.getTime() + REFRESH_EXPIRE_TIME)) // 발급 만료시간 refresh token 은 다른날짜를 줘야될거같음.
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();

        return TokenDTO.builder()
                .grantType("Bearer")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .isJoined(isJoined)
                .build();
    }

    public TokenDTO refreshToken(String refreshToken, User userInfo) {
        Date now = new Date();
        Map<String, Object> infoList = new HashMap<>();
        infoList.put("userId", userInfo.getId());
        infoList.put("email", userInfo.getEmail());
        infoList.put("provider", userInfo.getProvider());

        Claims claims = Jwts.claims();
        claims.setIssuer("api.longket.com"); // 토큰 발급자
        claims.setIssuedAt(now); // 토큰 발행 시간 정보
        claims.setSubject(userInfo.getEmail()); // 토큰 제목 (user email)
        claims.setAudience("longket.com");// 토큰 대상자
        claims.setExpiration(new Date(now.getTime() + EXPIRE_TIME));  // 발급 만료시간
        claims.put("role", "NONE");
        claims.put("info", infoList);

        // Access Token 생성
        String accessToken = Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setClaims(claims) // 정보 저장
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();

        return TokenDTO.builder()
                .grantType("Bearer")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .isJoined(true)
                .build();
    }

    public TokenDTO getTestToken() {
        Date now = new Date();
        Map<String, Object> infoList = new HashMap<>();
        infoList.put("userId", 6L);
        infoList.put("email", "kjwon8202002@gmail.com");
        infoList.put("provider", "google");

        Claims claims = Jwts.claims();
        claims.setIssuer("api.longket.com"); // 토큰 발급자
        claims.setIssuedAt(now); // 토큰 발행 시간 정보
        claims.setSubject("kjwon8202002@gmail.com"); // 토큰 제목 (user email)
        claims.setAudience("longket.com");// 토큰 대상자
        claims.setExpiration(new Date(now.getTime() + EXPIRE_TIME));  // 발급 만료시간
        claims.put("role", "NONE");
        claims.put("info", infoList);

        // Access Token 생성
        String accessToken = Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setClaims(claims) // 정보 저장
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();

        // Refresh Token 생성
        String refreshToken = Jwts.builder()
                .setExpiration(new Date(now.getTime() + REFRESH_EXPIRE_TIME)) // 발급 만료시간 refresh token 은 다른날짜를 줘야될거같음.
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();

        return TokenDTO.builder()
                .grantType("Bearer")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .isJoined(true)
                .build();
    }

    /**
     * 토큰으로부터 받은 정보를 기반으로 Authentication 객체를 반환하는 메소드.
     *
     * @param accessToken
     * @return Authentication
     */
    public Authentication getAuthentication(String accessToken) {

        // 토큰 복호화
        Claims claims = parseClaims(accessToken);

        // 클레임에서 권한 정보 가져오기
        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get("role").toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .toList();

        JwtBody jwtBody = JwtBody.of(claims);

        // UserDetails 객체를 만들어서 Authentication 리턴
        return new UsernamePasswordAuthenticationToken(jwtBody, "", authorities);
    }

    /**
     * 사용자가 보낸 요청 헤더의 'Authorization' 필드에서 토큰을 추출하는 메소드.
     *
     * @param request
     * @return token(String)
     */
    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public boolean validateToken(String token, HttpServletRequest request) {
        if (token == null) {
            request.setAttribute(EXCEPTION, ErrorCode.NOT_FOUND_TOKEN.getMessage());
            return false;
        }
        Claims claims = parseClaims(token);
        if (claims.get("role") == null || claims.get("info") == null) {
            request.setAttribute(EXCEPTION, ErrorCode.ACCESS_DENIED_TOKEN.getMessage());
            return false;
        }

        byte[] decodedKey = Base64.getDecoder().decode(secretKey);
        try {
            return Jwts.parser()
                    .setSigningKey(decodedKey)
                    .parseClaimsJws(token)
                    .getBody()
                    .getExpiration()
                    .after(new Date());
        } catch (ExpiredJwtException e) {
            request.setAttribute(EXCEPTION, ErrorCode.EXPIRED_TOKEN.getMessage());
        } catch (UnsupportedJwtException e) {
            request.setAttribute(EXCEPTION, ErrorCode.UNSUPPORTED_TOKEN.getMessage());
        } catch (IllegalArgumentException e) {
            request.setAttribute(EXCEPTION, ErrorCode.CLAIMS_IS_EMPTY_TOKEN.getMessage());
        } catch (Exception e) {
            //SecurityException | MalformedJwtException e -> 포함
            request.setAttribute(EXCEPTION, ErrorCode.INVALID_TOKEN.getMessage());
        }
        return false;
    }

    public void validateToken(String token) {
        if (token == null) {
            throw new CustomException(ErrorCode.NOT_FOUND_TOKEN);
        }

        byte[] decodedKey = Base64.getDecoder().decode(secretKey);
        try {
            Jwts.parser()
                .setSigningKey(decodedKey)
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();

        } catch (ExpiredJwtException e) {
            throw new CustomException(ErrorCode.EXPIRED_TOKEN);
        } catch (UnsupportedJwtException e) {
            throw new CustomException(ErrorCode.UNSUPPORTED_TOKEN);
        } catch (IllegalArgumentException e) {
            throw new CustomException(ErrorCode.CLAIMS_IS_EMPTY_TOKEN);
        } catch (Exception e) {
            //SecurityException | MalformedJwtException e -> 포함
            throw new CustomException(ErrorCode.INVALID_TOKEN);
        }
    }

    private Claims parseClaims(String token) {
        byte[] decodedKey = Base64.getDecoder().decode(secretKey);
        try {
            return Jwts.parser().setSigningKey(decodedKey).parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }
}
