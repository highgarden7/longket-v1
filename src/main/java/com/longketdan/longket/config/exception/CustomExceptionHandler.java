package com.longketdan.longket.config.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler(CustomException.class)
    protected ResponseEntity<CommonResponse> handleCustomException(CustomException e) {
        return CommonResponse.errorResponseEntity(e.getErrorCode());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CommonResponse> handleInvalidArgument(MethodArgumentNotValidException e) {
        return CommonResponse.errorResponseEntity(ErrorCode.INVALID_DATA);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<CommonResponse> handleInvalidArgument(HttpMessageNotReadableException e) {
        return CommonResponse.errorResponseEntity(ErrorCode.INVALID_DATA);
    }
}
