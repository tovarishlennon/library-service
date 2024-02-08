package com.example.libraryservice.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@AllArgsConstructor
@NoArgsConstructor
public class PayloadResponseDto<T> {
    private ErrorResponseDto error = new ErrorResponseDto(0, "Success");
    private List<T> payload = new ArrayList<>();

    public PayloadResponseDto(List<T> payload) {
        this.payload = payload;
    }
}
