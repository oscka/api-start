package com.hanex.fms.starter.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.annotation.Nullable;

import com.hanex.fms.global.enums.MemberType;
import com.hanex.fms.global.enums.UserStatus;

@ToString
@AllArgsConstructor
@Builder
@Schema(description = "사용자 (거래처,실행사) 검색 요청 DTO")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberSearchCondition {


    @Nullable
    @Schema(description = "고객사 고유번호 > 고객사가 사용자를 추가", example = "7de5eb3f-fe59-453e-a0c1-f1f497ceee5c")
    private String customerId;

    @Nullable
    @Schema(description = "관리자 이름", example = "홍길동")
    private String managerName;

    @Nullable
    @Schema(description = "사용자 분류", example = "CLIENT")
    private MemberType memberType;

    @Nullable
    @Schema(description = "사용자 상태", example = "ACTIVE")
    private UserStatus memberStatus;



}
