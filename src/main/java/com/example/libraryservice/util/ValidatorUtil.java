package com.example.libraryservice.util;

public class ValidatorUtil {

    public static Double checkNumber(String str) {
        try {
            return Double.parseDouble(str);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
