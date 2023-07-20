package com.osckorea.openmsa.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import com.osckorea.openmsa.global.api.ApiResponseDto;
import com.osckorea.openmsa.global.api.ValidDto;

// 유효성 검사 실패, 잘못된 파라메터 요청
@Getter
public class Exception400 extends RuntimeException {

    private String key;
    private String value;

    public Exception400(String key, String value) {
        super(value);
        this.key = key;
        this.value = value;
    }

    public ApiResponseDto<?> body(){
        ValidDto validDTO = new ValidDto(key, value);
        return new ApiResponseDto<>(HttpStatus.BAD_REQUEST, "badRequest", validDTO);
    }

    public HttpStatus status(){
        return HttpStatus.BAD_REQUEST;
    }
}