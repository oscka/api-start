package com.hanex.fms.starter.member.domain;

import com.hanex.fms.global.enums.MemberType;
import com.hanex.fms.global.enums.UserStatus;
import com.hanex.fms.starter.customer.domain.Customer;
import com.hanex.fms.starter.member.dto.MemberDto;
import com.hanex.fms.starter.user.domain.BaseUser;

import lombok.*;
import org.springframework.data.annotation.*;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.Instant;

@EqualsAndHashCode(of = "id")
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table("member_tb")
public class Member {

	@Id
	private String id;

	private AggregateReference<Customer, @NotNull String> customerId;

	@Valid
	@Column("user_id")
	private BaseUser baseUser;

	// TODO :: BaseUser 로 옮기는게 더 나을지 고민..
	private UserStatus memberStatus;

	private MemberType memberType;

	private String memberCode;

	private String managerName;

	private String ceoName;

	private String registrationNumber;

	private String phone;

	private String memo;
	private String email;

	@Builder.Default
	@CreatedDate
	private Instant createdAt = Instant.now();

	@Builder.Default
	@LastModifiedDate
	private Instant updatedAt = Instant.now();

	@CreatedBy
	private AggregateReference<Customer, @NotNull String> createdBy;

	@LastModifiedBy
	private AggregateReference<Customer, @NotNull String> updatedBy;


	// ---------------- 비지니스 로직 --------------- //
	public MemberDto.MemberResponse toDto(){
		return MemberDto.MemberResponse.builder()
				.id(this.id)
				.customerId(String.valueOf(this.customerId.getId()))
				.email(this.email)
				.memberType(this.memberType)
				.memberCode(this.memberCode)
				.memberStatus(this.memberStatus)
				.managerName(this.managerName)
				.loginId(this.baseUser.getLoginId())
				.role(this.baseUser.getUserRole())
				.ceoName(this.ceoName)
				.registrationNumber(this.registrationNumber)
				.phone(this.phone)
				.memo(this.memo)
				.createdAt(this.createdAt)
				.createdBy(String.valueOf(this.createdBy.getId()))
				.updatedAt(this.updatedAt)
				.updatedBy(String.valueOf(this.updatedBy.getId()))
				.build();
	}

	public MemberDto.MemberResponse listToDto(){
		return MemberDto.MemberResponse.builder()
				.id(this.id)
				.customerId(String.valueOf(this.customerId.getId()))
				.email(this.email)
				.memberType(this.memberType)
				.memberCode(this.memberCode)
				.memberStatus(this.memberStatus)
				.managerName(this.managerName)
				.ceoName(this.ceoName)
				.registrationNumber(this.registrationNumber)
				.phone(this.phone)
				.memo(this.memo)
				.createdAt(this.createdAt)
				.createdBy(String.valueOf(this.createdBy.getId()))
				.updatedAt(this.updatedAt)
				.updatedBy(String.valueOf(this.updatedBy.getId()))
				.build();
	}

}
