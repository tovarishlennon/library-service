package com.example.libraryservice.exceptions;


import lombok.Getter;
import com.example.libraryservice.enums.ExceptionCodes;

@Getter
public class ApiException extends RuntimeException {
    private final ExceptionCodes code;

    public ApiException(ExceptionCodes code, String message) {
        super(String.format("Failed. %s", message));
        this.code = code;
    }

}
