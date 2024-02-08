package com.example.libraryservice.util;

public class ConversionUtil {
    public static Integer convertStringToInteger(String str) {
        try {
            return (Integer) (int) Double.parseDouble(str);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
