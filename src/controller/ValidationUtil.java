package controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Validation utility class
 * Contains all validation methods used across the application
 */
public class ValidationUtil {
    
    /**
     * Validate service number format (6 digits)
     */
    public static boolean isValidServiceNumber(String serviceNumber) {
        if (serviceNumber == null || serviceNumber.trim().isEmpty()) {
            return false;
        }
        return serviceNumber.matches("\\d{6}");
    }
    
    /**
     * Validate name - not empty and only letters and spaces
     */
    public static boolean isValidName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return false;
        }
        return name.matches("[a-zA-Z\\s]+");
    }
    
    /**
     * Validate phone number - Nepal format
     */
    public static boolean isValidPhone(String phone) {
        if (phone == null || phone.trim().isEmpty()) {
            return false;
        }
        // Nepal phone: +977-98XXXXXXXX or 98XXXXXXXX
        return phone.matches("(\\+977-)?9[78]\\d{8}");
    }
    
    /**
     * Validate email
     */
    public static boolean isValidEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        return email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }
    
    /**
     * Validate pension amount - must be positive
     */
    public static boolean isValidPensionAmount(double amount) {
        return amount > 0;
    }
    
    /**
     * Validate date string
     */
    public static boolean isValidDate(String dateStr) {
        try {
            LocalDate.parse(dateStr);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }
    
    /**
     * Validate year - between 1900 and current year
     */
    public static boolean isValidYear(int year) {
        int currentYear = LocalDate.now().getYear();
        return year >= 1900 && year <= currentYear;
    }
    
    /**
     * Parse date string to LocalDate
     */
    public static LocalDate parseDate(String dateStr) throws DateTimeParseException {
        return LocalDate.parse(dateStr);
    }
    
    /**
     * Format LocalDate to string
     */
    public static String formatDate(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return date.format(formatter);
    }
    
    /**
     * Check if string is empty or null
     */
    public static boolean isEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }
}
