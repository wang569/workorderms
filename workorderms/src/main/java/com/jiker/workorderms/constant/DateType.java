package com.jiker.workorderms.constant;

public enum DateType {
    YEAR(1), MONTH(2), DAY(5), HOUR(11);

    private int index;

    DateType( int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }
}
