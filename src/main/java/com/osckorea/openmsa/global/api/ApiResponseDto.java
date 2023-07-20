package com.osckorea.openmsa.global.api;

import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@ToString
@Getter
public class ApiResponseDto<T> {

    public static <T> ApiResponseDto<T> createOK(T data) {
        return new ApiResponseDto<>(data);
    }

    public static final ApiResponseDto<String> DEFAULT_OK = new ApiResponseDto<>();

    private Integer status; // 에러시에 의미 있음
    private String msg; // 에러시에 의미 있음 ex) badRequest
    private T data; // 에러시에는 구체적인 에러 내용 ex) username이 입력되지 않았습니다

    public ApiResponseDto(){
        this.status = HttpStatus.OK.value();
        this.msg = "성공";
    }

    public ApiResponseDto(T data){
        this.status = HttpStatus.OK.value();
        this.msg = "성공";
        this.data = data; // 응답할 데이터 바디
    }

    public ApiResponseDto(HttpStatus httpStatus, String msg, T data){
        this.status = httpStatus.value();
        this.msg = msg; // 에러 제목
        this.data = data; // 에러 내용
    }
}
