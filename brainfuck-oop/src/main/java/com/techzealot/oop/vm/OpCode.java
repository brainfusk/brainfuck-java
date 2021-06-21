package com.techzealot.oop.vm;

import lombok.Getter;

/**
 * token same as OpCode
 */
public enum OpCode {
    SHR('>'),
    SHL('<'),
    ADD('+'),
    SUB('-'),
    PUT_CHAR('.'),
    GET_CHAR(','),
    LB('['),
    RB(']');
    @Getter
    private final int code;

    OpCode(int code) {
        this.code = code;
    }

    public static OpCode from(int token) {
        for (OpCode value : OpCode.values()) {
            if (value.code == token) {
                return value;
            }
        }
        return null;
    }
}
