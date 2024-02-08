package com.example.libraryservice.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class BookDto {
    private Long id;
    private String book;
    private String series;
    private String releaseNumber;
    private String author;
    private String description;
    private Integer numPages;
    private String format;
    private List<String> genres;
    private LocalDate publicationDate;
    private Double rating;
    private Integer numberOfVoters;
}
