package view;

import java.awt.Color;

/**
 * Color Constants for the application
 * Inspired by Royal Gurkha Rifles branding
 */
public class ColorScheme {
    // Primary Colors - Dark Green (Rifle Regiment) - Based on Gurkha Brigade website
    public static final Color PRIMARY_DARK = new Color(20, 50, 20);       // Very dark forest green
    public static final Color PRIMARY = new Color(42, 87, 42);             // Rich military green
    public static final Color PRIMARY_LIGHT = new Color(76, 139, 76);      // Lighter green
    
    // Secondary Colors - Maroon/Burgundy (Gurkha traditional)
    public static final Color SECONDARY = new Color(128, 0, 0);            // Rich maroon
    public static final Color SECONDARY_LIGHT = new Color(165, 42, 42);    // Brown-maroon
    
    // Accent Colors - Gold/Bronze (Military insignia)
    public static final Color ACCENT_GOLD = new Color(218, 165, 32);       // Goldenrod - more visible
    public static final Color ACCENT_BRONZE = new Color(205, 127, 50);     // Bronze
    
    // Button Colors - High Contrast
    public static final Color BUTTON_PRIMARY = new Color(34, 139, 34);     // Forest green - highly visible
    public static final Color BUTTON_SECONDARY = new Color(178, 34, 34);   // Firebrick red - highly visible
    public static final Color BUTTON_GOLD = new Color(184, 134, 11);       // Dark goldenrod
    
    // Neutral Colors
    public static final Color WHITE = new Color(255, 255, 255);
    public static final Color LIGHT_GRAY = new Color(245, 245, 245);
    public static final Color GRAY = new Color(200, 200, 200);
    public static final Color DARK_GRAY = new Color(100, 100, 100);
    public static final Color TEXT_DARK = new Color(51, 51, 51);
    
    // Status Colors
    public static final Color SUCCESS = new Color(40, 167, 69);
    public static final Color WARNING = new Color(255, 193, 7);
    public static final Color ERROR = new Color(220, 53, 69);
    public static final Color INFO = new Color(23, 162, 184);
    
    // Table Colors
    public static final Color TABLE_HEADER = PRIMARY_DARK;
    public static final Color TABLE_ROW_EVEN = new Color(250, 250, 250);
    public static final Color TABLE_ROW_ODD = WHITE;
    public static final Color TABLE_SELECTION = new Color(180, 210, 180);
}
