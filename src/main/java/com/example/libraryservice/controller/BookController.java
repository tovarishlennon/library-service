package com.example.libraryservice.controller;

import com.example.libraryservice.enums.EColumns;
import com.example.libraryservice.enums.ESortingValue;
import com.example.libraryservice.service.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api")
@Validated
public class BookController {

    //you can use autowired here, as lombok library was injected
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/top10")
    public ResponseEntity<?> getFilteredBooks(
            @RequestParam(required = false, name = "year") Integer year,
            @RequestParam(name = "column") EColumns column,
            @RequestParam(name = "sort") ESortingValue sorting
    ) {
        return bookService.getFilteredBooks(year, column, sorting);
    }
}
