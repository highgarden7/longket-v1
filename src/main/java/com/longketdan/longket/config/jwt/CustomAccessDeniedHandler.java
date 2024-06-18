package com.longketdan.longket.config.jwt;

import com.longketdan.longket.config.exception.ErrorCode;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAccessDeniedHandler extends Throwable implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {

        ErrorCode errorCode;
        errorCode = ErrorCode.ACCESS_DENIED_TOKEN;
        setResponse(response, errorCode);
    }

    /**
     * 한글 출력을 위해 getWriter() 사용
     */
    private void setResponse(HttpServletResponse response, ErrorCode errorCode) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.getWriter().println("{ "
                + "\"status\" : \"SC_FORBIDDEN\","
                + "\"code\" : " + errorCode.getCode() + ","
                + "\"name\" : \"" + errorCode.name() + "\","
                + "\"message\" : \""+ errorCode.getMessage() +"\""
                +" }"
        );
    }
}
