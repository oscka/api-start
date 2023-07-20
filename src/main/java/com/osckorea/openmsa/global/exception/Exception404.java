package com.osckorea.openmsa.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import com.osckorea.openmsa.global.api.ApiResponseDto;

// resource 를 찾을 수 없음
@Getter
public class Exception404 extends RuntimeException {
    public Exception404(String message) {
        super(message);
    }

    public ApiResponseDto<?> body(){
        return new ApiResponseDto<>(HttpStatus.NOT_FOUND, "notFound", getMessage());
    }

    public HttpStatus status(){
        return HttpStatus.NOT_FOUND;
    }
}
