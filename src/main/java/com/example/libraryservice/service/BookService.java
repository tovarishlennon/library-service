package com.example.libraryservice.service;

import com.example.libraryservice.enums.EColumns;
import com.example.libraryservice.enums.ESortingValue;
import org.springframework.http.ResponseEntity;

public interface BookService {
    ResponseEntity<?> getFilteredBooks(Integer year, EColumns column, ESortingValue sorting);
}
