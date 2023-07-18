package com.hanex.fms.starter.customer.dto;

import com.hanex.fms.global.enums.Group;
import com.hanex.fms.global.enums.UserRole;
import com.hanex.fms.global.enums.UserStatus;
import com.hanex.fms.global.util.CommonEncoder;
import com.hanex.fms.global.util.encrypt.EncryptString;
import com.hanex.fms.starter.customer.domain.Customer;
import com.hanex.fms.starter.user.domain.BaseUser;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.UUID;

public class CustomerDto {

    @Schema(description = "고객사 생성 요청 DTO")
    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    public static class SaveRequest {

        @Schema(description = "로그인 ID", nullable = false, example = "customer01")
        @NotBlank(message = "{validation.notBlank.id}")
        private String loginId;

        @Schema(description = "비밀번호", nullable = false, example = "test1234")
        @NotBlank(message = "{validation.notBlank.password}")
        private String password;

        @Schema(description = "고객사 계정 상태", nullable = false, example = "ACTIVE")
        @NotNull
        private UserStatus customerStatus;

        @Schema(description = "고객사 그룹", nullable = false, example = "FINANCE")
        @NotNull
        private Group customerGroup;

        @Schema(description = "고객사명", nullable = false, example = "인삼공사")
        @NotBlank(message = "{validation.notBlank.name}")
        private String name;

        @Schema(description = "휴대폰", example = "01012345678")
        private String phone;

        @Schema(description = "메모" ,example = "고객사 메모")
        private String memo;


        @Schema(description = "이메일", example = "customer01@test.com")
        private String email;

        public Customer toEntity (){
            return Customer.builder()
                    .id(UUID.randomUUID().toString())
                    .version(0L)
                    .customerStatus(this.customerStatus)
                    .customerGroup(this.customerGroup)
                    .baseUser(BaseUser.builder()
                            .loginId(this.loginId)
                            .password(new CommonEncoder().encode(this.password))
                            .userRole(UserRole.ROLE_CUSTOMER)
                            .build())
                    .email(this.email)
                    .memo(this.memo)
                    .name(this.name)
                    .phone(new EncryptString(this.phone))
                    .build();
        }

    }

    @Schema(description = "고객사 조회 응답 DTO")
    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    public static class CustomerInfoResponse {

        @Schema(description = "고객사 고유번호")
        private String id;

        @Schema(description = "로그인 ID", nullable = false, example = "customer01")
        private String loginId;

        @Schema(description = "고객사 계정 상태", nullable = false, example = "ACTIVE")
        private UserStatus customerStatus;

        @Schema(description = "고객사 권한" , example = "ROLE_CUSTOMER")
        private UserRole role;

        @Schema(description = "이메일", example = "customer01@test.com")
        private String email;
        
        @Schema(description = "메모" ,example = "고객사 메모")
        private String memo;

        @Schema(description = "고객사 그룹", nullable = false, example = "FINANCE")
        private Group customerGroup;

        @Schema(description = "고객사명", nullable = false, example = "인삼공사")
        private String name;

        @Schema(description = "휴대폰", example = "01012345678")
        private String phone;

        @Schema(description = "생성일", example = "2023-06-01 11:22:40.109")
        private Instant createdAt;

        @Schema(description = "수정일", example = "2023-06-01 11:22:40.109")
        private Instant updatedAt;
    }


    @Schema(description = "고객사 수정 요청 DTO")
    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    public static class UpdateRequest {

        @Schema(description = "고객사 그룹", nullable = false, example = "FINANCE")
        private Group customerGroup;

        @Schema(description = "고객사 계정 상태", nullable = false, example = "ACTIVE")
        @NotNull
        private UserStatus customerStatus;

        @Schema(description = "고객사명", nullable = false, example = "인삼공사")
        private String name;

        @Schema(description = "이메일", example = "customer01@test.com")
        private String email;

        @Schema(description = "휴대폰", example = "01012345678")
        private String phone;

        @Schema(description = "메모", example = "고객사 메모 업데이트")
        private String memo;

        public Customer toEntity (){
            return Customer.builder()
                    .customerStatus(this.customerStatus)
                    .customerGroup(this.customerGroup)
                    .email(this.email)
                    .memo(this.memo)
                    .name(this.name)
                    .phone(new EncryptString(this.phone))
                    .build();
        }
    }
}
