package com.example.libraryservice.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

public class DateTimeUtils {
    public static LocalDate convertStringToLocalDateTime(String inputDate) {
        try {
            DateTimeFormatter inputFormat = DateTimeFormatter.ofPattern("MMMM d, yyyy", Locale.ENGLISH);
            return LocalDate.parse(inputDate, inputFormat);
        } catch (DateTimeParseException e) {
            return null;
        }
    }
}
