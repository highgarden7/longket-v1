package com.longketdan.longket.config.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {

    //TOKEN Exception
    NOT_FOUND_TOKEN(HttpStatus.UNAUTHORIZED, 401001, "토큰이 존재하지 않습니다."),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, 401002, "잘못된 토큰정보 입니다."),
    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, 401003, "토큰이 만료되었습니다."),

    UNSUPPORTED_TOKEN(HttpStatus.UNAUTHORIZED, 401004, "지원되지 않는 토큰입니다."),
    CLAIMS_IS_EMPTY_TOKEN(HttpStatus.UNAUTHORIZED, 401005, "토큰 claims 이 존재하지 않습니다."),
    ACCESS_DENIED_TOKEN(HttpStatus.UNAUTHORIZED, 401006, "접근 권한이 없습니다."),
    EXISTS_TOKEN(HttpStatus.UNAUTHORIZED, 401007, "이미 로그인 되었습니다."),
    INVALID_REGISTRATION_ID(HttpStatus.UNAUTHORIZED, 401008, "잘못된 로그인 요청입니다."),

    //API RuntimeException
    NOT_FOUND(HttpStatus.NOT_FOUND, 404, "데이터를 찾을 수 없습니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, 500, "내부 서버 오류."),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, 401, "접근 할 수 없습니다."),
    LOGIN_FAIL(HttpStatus.UNAUTHORIZED, 401, "로그인 실패."),

    // Duplicate
    DUPLICATE_NICKNAME(HttpStatus.CONFLICT, 409001, "중복된 닉네임이 존재합니다,"),

    //Valid Exception
    INVALID_DATA(HttpStatus.BAD_REQUEST, 400, "잘못된 데이터 정보 기입입니다."),
    INVALID_CATEGORY_TYPE(HttpStatus.BAD_REQUEST, 400, "잘못된 카테고리 타입입니다."),
    INVALID_COMMENT_UPDATE(HttpStatus.BAD_REQUEST, 400, "자신의 댓글만 수정할 수 있습니다."),
    INVALID_COMMENT_DELETE(HttpStatus.BAD_REQUEST,400, "부모 댓글이 있는 댓글은 삭제할 수 없습니다.")
    ;


    private final HttpStatus httpStatus;
    private final int code;
    private final String message;
}
