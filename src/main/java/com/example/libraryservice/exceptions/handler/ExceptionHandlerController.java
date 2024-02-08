package com.example.libraryservice.exceptions.handler;


import com.example.libraryservice.dto.ErrorResponseDto;
import com.example.libraryservice.dto.PayloadResponseDto;
import com.example.libraryservice.enums.ExceptionCodes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;

@Slf4j
@RestControllerAdvice
public class ExceptionHandlerController {


    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = IllegalArgumentException.class)
    public PayloadResponseDto<?> handleExpoServiceException(IllegalArgumentException ex) {
        log.error(ex.getMessage());
        return new PayloadResponseDto<>(new ErrorResponseDto(ExceptionCodes.GIVEN_PARAMETER_IS_NOT_VALID.getCode(), "One of the given parameters is not valid! " + ex.getMessage()), new ArrayList<>());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = MissingServletRequestParameterException.class)
    public PayloadResponseDto<?> handleExpoServiceException(MissingServletRequestParameterException ex) {
        log.error(ex.getMessage());
        return new PayloadResponseDto<>(new ErrorResponseDto(ExceptionCodes.REQUIRED_COLUMN_IS_EMPTY.getCode(), "There is no required parameter presented: " + ex.getParameterName()+"!"), new ArrayList<>());

    }
}
