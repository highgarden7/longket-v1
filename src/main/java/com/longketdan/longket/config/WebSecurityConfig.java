package com.longketdan.longket.config;

import com.longketdan.longket.config.jwt.JwtAuthorizationFilter;
import com.longketdan.longket.config.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity//-> 시큐리티 활성화 => 기본 스프링 필터체인에 등록
@RequiredArgsConstructor
public class WebSecurityConfig {

    @Bean
    SecurityFilterChain filterChain(HttpSecurity httpSecurity, AuthenticationEntryPoint authenticationEntryPoint,
                                    AccessDeniedHandler accessDeniedHandler, JwtProvider jwtProvider) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource())) // cors
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) //jSessionId 사용 거부
                .formLogin(withDefaults()) // form 로긴 해제 (UsernamePasswordAuthenticationFilter 비활성화)
                .httpBasic(withDefaults()) // 로그인 인증창이 뜨지 않게 비활성화
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/actuator/health", "/actuator/health/liveness", "/actuator/health/readiness").permitAll() // health check
                        .requestMatchers("/api/v1/docs/**", "/swagger-ui/**", "/swagger-longket.html").permitAll() // swagger
                        .requestMatchers("/api/v1/admin/config").permitAll()
                        .requestMatchers("/api/auth/login/**").permitAll() // token
                        .requestMatchers("/api/auth/refresh/**").permitAll() // token
                        .requestMatchers("/api/auth/login/test/dev").permitAll() // token
                        .anyRequest().authenticated()
                )
                .exceptionHandling(it -> it.authenticationEntryPoint(authenticationEntryPoint))
                .exceptionHandling(it -> it.accessDeniedHandler(accessDeniedHandler))
                .addFilterBefore(new JwtAuthorizationFilter(jwtProvider), UsernamePasswordAuthenticationFilter.class)
                .build();
    }


    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOriginPatterns(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PATCH", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setExposedHeaders(Arrays.asList("Authorization"));
        configuration.setAllowCredentials(true); // 클라이언트에서 쿠키 요청 허용
        configuration.setMaxAge(3600L);
        configuration.addExposedHeader("Authorization"); // 옛날에는 디폴트 였다. 지금은 아닙니다.

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
