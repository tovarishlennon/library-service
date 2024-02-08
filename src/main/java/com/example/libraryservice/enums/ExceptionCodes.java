package com.example.libraryservice.enums;

import lombok.Getter;

@Getter
public enum ExceptionCodes {

    SYSTEM_FAILURE(-100),
    VALIDATION_EXCEPTION(-101),
    REQUIRED_COLUMN_IS_EMPTY(-101),
    GIVEN_PARAMETER_IS_NOT_VALID(-102);

    private final Integer code;

    ExceptionCodes(Integer code) {
        this.code = code;
    }

}
