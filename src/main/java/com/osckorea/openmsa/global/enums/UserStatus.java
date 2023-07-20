package com.osckorea.openmsa.global.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 사용자 관리
 */
@Getter
@RequiredArgsConstructor
public enum UserStatus implements EnumType {

	ACTIVE("정상"),
	BAN("정지"),
	LOCKED("휴면"),
	DELETED("탈퇴")
	;

	private final String text;

	@Override
	public String getId() {
		return this.name();
	}

	@Override
	public String getText() {
		return this.text;
	}
}

