package com.hanex.fms.global.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Member 분류
 */
@RequiredArgsConstructor
@Getter
public enum MemberType implements EnumType{
    

    CLIENT("거래처"),
    EXECUTOR("실행사")
    ;

    private final String text;

    @Override
    public String getId() {
        return this.name();
    }


}
