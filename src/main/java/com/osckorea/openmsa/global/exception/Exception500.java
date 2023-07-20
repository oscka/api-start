package com.osckorea.openmsa.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import com.osckorea.openmsa.global.api.ApiResponseDto;

// 서버 에러
@Getter
public class Exception500 extends RuntimeException {
    public Exception500(String message) {
        super(message);
    }

    public ApiResponseDto<?> body(){
        return new ApiResponseDto<>(HttpStatus.INTERNAL_SERVER_ERROR, "serverError", getMessage());
    }

    public HttpStatus status(){
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }
}
