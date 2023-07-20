package com.osckorea.openmsa.starter.user.domain;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import com.osckorea.openmsa.global.enums.UserRole;
import com.osckorea.openmsa.global.enums.UserStatus;
import com.osckorea.openmsa.starter.user.dto.UserDto;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

@EqualsAndHashCode(of = "id")
@Builder
@Getter
@ToString
@Table("user_tb")
public class User implements Serializable {

	@Id
	private UUID id;

	private String loginId;

	@Column("user_name")
	private String name;

	private UserStatus userStatus;

	@Column("user_role")
	private UserRole role;

	private String email;

	@Column("user_password")
	private String password;

	private String phone;

	@Builder.Default
	@CreatedDate
	private Instant createdAt = Instant.now();

	@Builder.Default
	@LastModifiedDate
	private Instant updatedAt = Instant.now();

	// ---------------- 비지니스 로직 --------------- //
	public void lock() {
		this.userStatus = UserStatus.LOCKED;
	}

	public void ban() {
		this.userStatus = UserStatus.BAN;
	}

	public void delete() {
		this.userStatus = UserStatus.DELETED;
	}

	public UserDto.UserInfoResponse toDto(){
		return UserDto.UserInfoResponse.builder()
				.id(this.id)
				.createdAt(this.createdAt)
				.name(this.name)
				.email(this.email)
				.loginId(this.loginId)
				.userStatus(this.userStatus)
				.role(this.role)
				.phone(this.phone)
				.build();
	}

}
