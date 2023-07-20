package com.osckorea.openmsa.global.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum TableStatus {

    Y("1", true),
    N("0", false);

    private final String table1Value;
    private final boolean table2Value;

    public String getTable1Value() {
        return this.table1Value;
    }

    public boolean isTable2Value() {
        return this.table2Value;
    }


}
