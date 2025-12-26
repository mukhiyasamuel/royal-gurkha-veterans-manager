import view.HomePage;
import javax.swing.*;

/**
 * Royal Gurkha Veteran Management System
 * Main Entry Point
 * 
 * @author Princey Sunar
 * @version 1.0
 * @since 2025-12-23
 * 
 * A comprehensive system for managing Royal Gurkha veteran information,
 * pension schemes (GPS/AFPS), and welfare office locations.
 * 
 * Features:
 * - MVC Architecture
 * - Data Structures: ArrayList, LinkedList, Queue, HashMap
 * - Algorithms: Binary Search, Linear Search, Sorting (Quick/Merge)
 * - CRUD Operations
 * - Search & Sort functionality
 * - Admin and Veteran portals
 * - CardLayout for tab navigation
 */
public class RoyalGurkhaVMS {
    
    /**
     * Main method - Entry point of the application
     * @param args command line arguments
     */
    public static void main(String[] args) {
        // Set Look and Feel to System default for better UI
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // Run on Event Dispatch Thread for thread safety
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // Create and display the home page
                HomePage homePage = new HomePage();
                homePage.setVisible(true);
                
                // Welcome message
                System.out.println("========================================");
                System.out.println("Royal Gurkha Veteran Management System");
                System.out.println("========================================");
                System.out.println("System Started Successfully!");
                System.out.println("Dummy data loaded into memory.");
                System.out.println("\nDefault Login Credentials:");
                System.out.println("Admin: username=admin, password=admin123");
                System.out.println("Veteran: serviceNumber=100001-100007, password=password123");
                System.out.println("========================================\n");
            }
        });
    }
}
