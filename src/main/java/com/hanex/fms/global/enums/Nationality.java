package com.hanex.fms.global.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 국적
 */
@Getter
@RequiredArgsConstructor
public enum Nationality implements EnumType {

    LOCAL("내국인"),
    FOREIGNER("외국인");

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
