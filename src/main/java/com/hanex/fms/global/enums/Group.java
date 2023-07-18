package com.hanex.fms.global.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Customer 의 그룹
 * - 재무
 * - CSR
 * - 재고
 * - 기획
 */
@RequiredArgsConstructor
@Getter
public enum Group implements EnumType{


    FINANCE("재무"),
    CSR("CSR"),
    INVENTORY("재고"),
    PROJECT("기획")
    ;

    private final String text;

    @Override
    public String getId() {
        return this.name();
    }
}
