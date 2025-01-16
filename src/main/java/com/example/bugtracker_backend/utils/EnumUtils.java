package com.example.bugtracker_backend.utils;

/**
 * Utility class for working with Enums.
 */
public class EnumUtils {
    public static <E extends Enum<E>> boolean isValidEnum(Class<E> enumClass, String value) {
        if (value == null) {
            return false;
        }
        try {
            Enum.valueOf(enumClass, value);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}