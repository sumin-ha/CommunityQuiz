package com.callbuslab.community.constraint;

public enum ResultCode {

    SUCCESS(1),
    ERROR(0);

    private int value;

    ResultCode(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
