package com.hanex.fms.starter.user.dto;

import java.util.UUID;

import com.hanex.fms.global.enums.UserRole;
import com.hanex.fms.global.enums.UserStatus;
import com.hanex.fms.global.util.CommonEncoder;
import com.hanex.fms.starter.user.domain.User;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.Instant;

public class UserDto{

	@Schema(description = "회원가입(User 정보 저장) 요청 DTO")
	@Getter
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	public static class SaveRequest {

		@Schema(description = "사용자 ID", nullable = false, example = "user1")
		@NotBlank(message = "{validation.notBlank.id}")
		private String loginId;

		@Schema(description = "사용자 이름", nullable = false, example = "홍길동")
		@NotBlank(message = "{validation.notBlank.name}")
		private String name;

		@Schema(description = "사용자 상태", nullable = false,  example = "ACTIVE")
		@NotNull
		private UserStatus userStatus;

		@Schema(description = "사용자 권한", nullable = false,  example = "ROLE_ADMIN")
		@NotNull
		private UserRole role;

		@Schema(description = "이메일", nullable = false,  example = "test@test.com")
		@Pattern(regexp = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$")
		@NotBlank(message = "{validation.notBlank.email}")
		private String email;

		@Schema(description = "비밀번호", nullable = false,  example = "test1234")
		@NotBlank(message = "{validation.notBlank.password}")
		private String password;

		@Schema(description = "휴대폰", example = "01012345678")
		private String phone;

		public User toEntity(){
			return User.builder()
					.id(UUID.randomUUID())
					.loginId(this.loginId)
					.name(this.name)
					.userStatus(this.userStatus)
					.phone(this.phone)
					.role(this.role)
					.password(new CommonEncoder().encode(this.password))
					//.email(new EncryptString(this.email))
					.email(this.email)
					.build();
		}

	}

	@Schema(description = "User 조회 응답 DTO")
	@Getter
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	public static class UserInfoResponse {

		@Schema(description = "사용자 고유 ID", nullable = false, example = "user1")
		private UUID id;

		@Schema(description = "사용자 로그인 ID", nullable = false, example = "user1")
		private String loginId;

		@Schema(description = "사용자 이름", nullable = false, example = "홍길동")
		private String name;

		@Schema(description = "사용자 상태", nullable = false,  example = "ACTIVE")
		private UserStatus userStatus;

		@Schema(description = "사용자 권한", nullable = false,  example = "ROLE_ADMIN")
		private UserRole role;

		@Schema(description = "이메일", nullable = false,  example = "test@test.com")
		private String email;

		@Schema(description = "휴대폰", example = "01012345678")
		private String phone;

		@Schema(description = "생성일", example = "2023-06-01 11:22:40.109")
		private Instant createdAt;

	}


	@Schema(description = "User 수정 요청 DTO")
	@Getter
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	public static class UpdateRequest {

		@Schema(description = "이름", nullable = false,  example = "홍길동")
		@NotBlank(message = "{validation.notBlank.name}")
		private String name;

		@Schema(description = "이메일", nullable = false,  example = "test@test.com")
		@Pattern(regexp = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$")
		@NotBlank(message = "{validation.notBlank.email}")
		private String email;

		@Schema(description = "휴대폰", nullable = false,  example = "01012345678")
		@NotBlank(message = "{validation.notBlank.password}")
		private String phone;

		public User toEntity(){
			return User.builder()
					.name(this.name)
					.email(this.email)
					.phone(this.phone)
					.build();
		}

	}

	@Schema(description = "로그인 요청 DTO")
	@Getter
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	public static class LoginRequest {

		@Schema(description = "사용자 ID", nullable = false, example = "user1")
		@NotBlank(message = "{validation.notBlank.id}")
		private String loginId;

		@Schema(description = "비밀번호", nullable = false,  example = "test1234")
		@NotBlank(message = "{validation.notBlank.password}")
		private String password;
	}

}