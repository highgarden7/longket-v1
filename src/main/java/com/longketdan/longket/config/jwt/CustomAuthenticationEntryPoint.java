package com.longketdan.longket.config.jwt;

import com.longketdan.longket.config.exception.ErrorCode;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.Serializable;

@Slf4j
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        String exception = (String)request.getAttribute("exception");
        ErrorCode errorCode;

        if(exception == null) {
            errorCode = ErrorCode.UNAUTHORIZED;
            setResponse(response, errorCode);
        } else if(exception.equals(ErrorCode.NOT_FOUND_TOKEN.getMessage())) {
            errorCode = ErrorCode.NOT_FOUND_TOKEN;
            setResponse(response, errorCode);
        }else if(exception.equals(ErrorCode.EXPIRED_TOKEN.getMessage())){
            errorCode = ErrorCode.EXPIRED_TOKEN;
            setResponse(response, errorCode);
        }else if(exception.equals(ErrorCode.INVALID_TOKEN.getMessage())){
            errorCode = ErrorCode.INVALID_TOKEN;
            setResponse(response, errorCode);
        }else if(exception.equals(ErrorCode.UNSUPPORTED_TOKEN.getMessage())) {
            errorCode = ErrorCode.UNSUPPORTED_TOKEN;
            setResponse(response, errorCode);
        }else if(exception.equals(ErrorCode.CLAIMS_IS_EMPTY_TOKEN.getMessage())) {
            errorCode = ErrorCode.CLAIMS_IS_EMPTY_TOKEN;
            setResponse(response, errorCode);
        }else if(exception.equals(ErrorCode.ACCESS_DENIED_TOKEN.getMessage())) {
            errorCode = ErrorCode.ACCESS_DENIED_TOKEN;
            setResponse(response, errorCode);
        }

    }

    /**
     * 한글 출력을 위해 getWriter() 사용
     */
    private void setResponse(HttpServletResponse response, ErrorCode errorCode) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(errorCode.getHttpStatus().value());
        response.getWriter().println("{ "
                + "\"status\" : \"SC_UNAUTHORIZED\","
                + "\"code\" : " + errorCode.getCode() + ","
                + "\"name\" : \"" + errorCode.name() + "\","
                + "\"message\" : \""+ errorCode.getMessage() +"\""
                +" }"
        );
    }

}

