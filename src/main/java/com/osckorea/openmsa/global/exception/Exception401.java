package com.osckorea.openmsa.global.exception;


import lombok.Getter;
import org.springframework.http.HttpStatus;

import com.osckorea.openmsa.global.api.ApiResponseDto;

// 인증 안됨
@Getter
public class Exception401 extends RuntimeException {
    public Exception401(String message) {
        super(message);
    }

    public ApiResponseDto<?> body(){
        return new ApiResponseDto<>(HttpStatus.UNAUTHORIZED, "unAuthorized", getMessage());
    }

    public HttpStatus status(){
        return HttpStatus.UNAUTHORIZED;
    }
}
