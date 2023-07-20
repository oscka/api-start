package com.osckorea.openmsa.global.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum OrderStatus implements EnumType{

	STORE("입고"), 
	RELEASE("출고"), 
	INVENTORY("재고"), 
	PROCESS("가공"), 
	CANCELLED("주문 취소됨") 
;
	private final String text;

	@Override
	public String getId() {
		return this.getId();
	}
}
