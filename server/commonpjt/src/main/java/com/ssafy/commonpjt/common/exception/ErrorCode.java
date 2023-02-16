package com.ssafy.commonpjt.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    PAGE_NOT_FOUND(HttpStatus.NOT_FOUND, "페이지를 찾을 수 없습니다."),

    NOT_FOUND_USER(HttpStatus.UNAUTHORIZED, "잘못된 접근입니다."),

    DUPLICATED_VALUE(HttpStatus.CONFLICT, "등록된 상태입니다."),

    NOT_MY_CONTENTS(HttpStatus.FORBIDDEN, "접근할 수 없습니다."),

    SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류입니다."),

    NO_CONTENT(HttpStatus.NO_CONTENT, "요청하신 정보를 찾을 수 없습니다."),
    ;

    private final HttpStatus status;

    private final String message;
}
