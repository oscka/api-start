package com.hanex.fms.starter.member.dto;

import com.hanex.fms.global.enums.MemberType;
import com.hanex.fms.global.enums.UserRole;
import com.hanex.fms.global.enums.UserStatus;
import com.hanex.fms.global.security.CustomUser;
import com.hanex.fms.global.util.CommonEncoder;
import com.hanex.fms.starter.member.domain.Member;
import com.hanex.fms.starter.user.domain.BaseUser;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.data.jdbc.core.mapping.AggregateReference;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.UUID;

public class MemberDto {

	@Schema(description = "사용자 (거래처,실행사) 등록 요청 DTO")
	@Getter
	@Builder
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	@AllArgsConstructor
	public static class SaveRequest {

		@Schema(description = "고객사 고유번호 > 고객사가 사용자를 추가", nullable = false, example = "7de5eb3f-fe59-453e-a0c1-f1f497ceee5c")
		private String customerId;

		@Schema(description = "로그인 ID", nullable = false, example = "member_01")
		@NotBlank(message = "{validation.notBlank.id}")
		private String loginId;

		@Schema(description = "비밀번호", nullable = false, example = "test1234")
		@NotBlank(message = "{validation.notBlank.password}")
		private String password;

		@Schema(description = "사용자 상태", nullable = false, example = "ACTIVE")
		private UserStatus memberStatus;

		@Schema(description = "사용자 분류", nullable = false, example = "CLIENT")
		@NotNull
		private MemberType memberType;

		@Schema(description = "관리자 이름", nullable = false, example = "홍길동")
		private String managerName;

		@Schema(description = "CEO 이름", nullable = false, example = "홍길동")
		private String ceoName;

		@Schema(description = "사업자 번호", nullable = false, example = "130-81-16025")
		private String registrationNumber;

		@Schema(description = "전화 번호", nullable = false, example = "02-3400-5474")
		private String phone;

		@Schema(description = "메모", nullable = false, example = "고객메모")
		private String memo;

		@Schema(description = "이메일",  example = "member_01@test.com")
		private String email;


		public Member toEntity(CustomUser requestUser){
			return Member.builder()
					.id(UUID.randomUUID().toString())
					.customerId(AggregateReference.to(customerId))
					.baseUser(BaseUser.builder()
							.userRole(UserRole.ROLE_MEMBER)
							.loginId(this.loginId)
							.password(new CommonEncoder().encode(this.password))
							.build())
					// TODO memberCode 만드는 로직 필요
					.memberCode(UUID.randomUUID().toString().replace("-",""))
					.memberStatus(this.memberStatus)
					.memberType(this.memberType)
					.managerName(this.managerName)
					.ceoName(this.ceoName)
					.registrationNumber(this.registrationNumber)
					.phone(this.phone)
					.memo(this.memo)
					.email(this.email)
					.createdBy(AggregateReference.to(String.valueOf(requestUser.getUserId())))
					.updatedBy(AggregateReference.to(String.valueOf(requestUser.getUserId())))
					.build();
		}
	}

	@Schema(description = "사용자 (거래처,실행사) 조회 응답 DTO")
	@Getter
	@Builder
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	@AllArgsConstructor
	public static class MemberResponse {

		@Schema(description = "사용자 고유번호", nullable = false, example = "e2524fbe-9166-47fc-b63e-761bb4ab7c4f")
		private String id;

		@Schema(description = "고객사 고유번호", nullable = false, example = "7de5eb3f-fe59-453e-a0c1-f1f497ceee5c")
		private String customerId;

		@Schema(description = "로그인 ID", nullable = false, example = "user1")
		@NotBlank(message = "{validation.notBlank.id}")
		private String loginId;

		@Schema(description = "사용자 권한" , example = "ROLE_CLIENT")
		private UserRole role;
		
		@Schema(description = "사용자 코드", nullable = false, example = "0f038eee22f84026b71919d1a9dbc4e5")
		private String memberCode; // memberCode > 자동생성

		@Schema(description = "사용자 분류", nullable = false, example = "CLIENT")
		private MemberType memberType;

		@Schema(description = "사용자 상태", nullable = false, example = "ACTIVE")
		private UserStatus memberStatus;

		@Schema(description = "관리자 이름", nullable = false, example = "홍길동")
		private String managerName;

		@Schema(description = "CEO 이름", nullable = false, example = "홍길동")
		private String ceoName;

		@Schema(description = "사업자 번호", nullable = false, example = "130-81-16025")
		private String registrationNumber;

		@Schema(description = "전화 번호", nullable = false, example = "02-3400-5474")
		private String phone;

		@Schema(description = "메모", nullable = false, example = "고객메모")
		private String memo;

		@Schema(description = "이메일",  example = "member_01@test.com")
		private String email;

		@Schema(description = "생성일",  example = "2023-06-02T01:07:14.356666Z")
		private Instant createdAt;

		@Schema(description = "수정일",  example = "2023-06-02T01:07:14.356666Z")
		private Instant updatedAt;

		@Schema(description = "생성자 ID",  example = "7de5eb3f-fe59-453e-a0c1-f1f497ceee5c")
		private String createdBy;

		@Schema(description = "수정자 ID",  example = "7de5eb3f-fe59-453e-a0c1-f1f497ceee5c")
		private String updatedBy;
	}

	@Schema(description = "사용자 (거래처,실행사) 수정 요청 DTO")
	@Getter
	@Builder
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	@AllArgsConstructor
	public static class UpdateRequest {

		@Schema(description = "고객사 고유번호", nullable = false, example = "7de5eb3f-fe59-453e-a0c1-f1f497ceee5c")
		private String customerId;

		@Schema(description = "사용자 분류", nullable = false, example = "EXECUTOR")
		private MemberType memberType;

		@Schema(description = "관리자 이름", nullable = false, example = "홍길동")
		private String managerName;

		@Schema(description = "CEO 이름", nullable = false, example = "홍길동")
		private String ceoName;

		@Schema(description = "사업자 번호", nullable = false, example = "130-81-16025")
		private String registrationNumber;

		@Schema(description = "전화 번호", nullable = false, example = "02-3400-5474")
		private String phone;

		@Schema(description = "메모", nullable = false, example = "고객메모")
		private String memo;

		@Schema(description = "이메일",  example = "member_01@test.com")
		private String email;

		public Member toEntity(CustomUser requestUser){
			return Member.builder()
					.memberType(this.memberType)
					.managerName(this.managerName)
					.ceoName(this.ceoName)
					.registrationNumber(this.registrationNumber)
					.phone(this.phone)
					.memo(this.memo)
					.email(this.email)
					.updatedBy(AggregateReference.to(String.valueOf(requestUser.getUserId())))
					.build();
		}
	}
}
