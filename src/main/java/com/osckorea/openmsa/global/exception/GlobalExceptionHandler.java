package com.osckorea.openmsa.global.exception;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.MDC;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.osckorea.openmsa.global.annotation.CustomErrorLog;
import com.osckorea.openmsa.global.api.ApiResponseDto;

@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice
public class GlobalExceptionHandler {

	private final MessageSource messageSource;

	@CustomErrorLog
	@ExceptionHandler(Exception400.class)
	public ResponseEntity<?> badRequest(Exception400 e){
		return new ResponseEntity<>(e.body(), e.status());
	}

	@CustomErrorLog
	@ExceptionHandler(Exception401.class)
	public ResponseEntity<?> unAuthorized(Exception401 e){
		return new ResponseEntity<>(e.body(), e.status());
	}

	@CustomErrorLog
	@ExceptionHandler(Exception403.class)
	public ResponseEntity<?> forbidden(Exception403 e){
		return new ResponseEntity<>(e.body(), e.status());
	}

	@CustomErrorLog
	@ExceptionHandler(Exception404.class)
	public ResponseEntity<?> notFound(Exception404 e){
		return new ResponseEntity<>(e.body(), e.status());
	}

	@CustomErrorLog
	@ExceptionHandler(Exception500.class)
	public ResponseEntity<?> serverError(Exception500 e){
		return new ResponseEntity<>(e.body(), e.status());
	}

	@CustomErrorLog
	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> unknownServerError(Exception e){

		// MDC
		MDC.put("message", "API_REQUEST_FAILED");
		MDC.put("error_code", HttpStatus.INTERNAL_SERVER_ERROR.name());
		MDC.put("error_exception", e.getClass().getName());
		MDC.put("error_message", e.getMessage());
		MDC.put("error_trace", ExceptionUtils.getStackTrace(e));

		ApiResponseDto<String> apiResponseDto = new ApiResponseDto<>(HttpStatus.INTERNAL_SERVER_ERROR, "unknownServerError", e.getMessage());
		log.error(e.getMessage());
		return new ResponseEntity<>(apiResponseDto, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	

//	public String getMessage(String code){
//		return getMessage(code,null);
//	}

//	public String getMessage(String code,Object[] args){
//		return messageSource.getMessage(code ,args, LocaleContextHolder.getLocale());
//	}

}