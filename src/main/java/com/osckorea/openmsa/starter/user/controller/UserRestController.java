package com.osckorea.openmsa.starter.user.controller;

import java.util.UUID;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.osckorea.openmsa.global.api.ApiResponseDto;
import com.osckorea.openmsa.starter.user.dto.UserDto;
import com.osckorea.openmsa.starter.user.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Tag(name = "사용자 API",description = "JWT 토큰 발급용 사용자")
@RequestMapping("/v1/users")
@RequiredArgsConstructor
@RestController
public class UserRestController {

	private final UserService userService;

	@Operation(summary = "회원가입", description = "User 정보 Insert")
	@PostMapping("/register")
	public ResponseEntity<Void> register(@RequestBody @Valid UserDto.SaveRequest save){
		userService.register(save);
		return ResponseEntity.ok().build();
	}

	@Operation(summary = "로그인 아이디로 사용자 단건 조회", description = "로그인 아이디로 사용자 단건 조회")
	@GetMapping("/{loginId}")
	public ApiResponseDto findByLoginId(@PathVariable String loginId){
		log.info("사용자 단건조회:{}"+loginId);
		return new ApiResponseDto<>(userService.findByLoginId(loginId));
	}

	@Operation(summary = "사용자 정보 수정",description = "user upate")
	@PutMapping("/{id}")
	public ResponseEntity<Void> update(@NotBlank @Pattern(regexp = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$") @PathVariable UUID id,
									   @Valid @RequestBody UserDto.UpdateRequest update){
		userService.update(id,update);
		return ResponseEntity.ok().build();
	}

	@Operation(summary = "회원 탈퇴",description = "Soft Delete > USER STATUS 만 DELETED 로 update")
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable UUID id){
		userService.delete(id);
		return ResponseEntity.ok().build();
	}

	@Operation(summary = "로그인",description = "Auth Service 에 JWT 토큰 요청")
	@PostMapping("/login")
	public ApiResponseDto login(@Valid @RequestBody UserDto.LoginRequest request){
		return ApiResponseDto.createOK(userService.login(request));
	}
}
