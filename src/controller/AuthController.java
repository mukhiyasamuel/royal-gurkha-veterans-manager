package controller;

import model.Admin;
import model.Veteran;
import java.util.HashMap;

/**
 * Controller for Authentication - Login handling
 * Manages admin and veteran login
 */
public class AuthController {
    private HashMap<String, Admin> adminAccounts;
    private VeteranController veteranController;
    
    // Current logged in user
    private Admin currentAdmin;
    private Veteran currentVeteran;
    
    public AuthController(VeteranController veteranController) {
        this.veteranController = veteranController;
        adminAccounts = new HashMap<>();
        loadAdminAccounts();
    }
    
    /**
     * Load default admin accounts
     */
    private void loadAdminAccounts() {
        // Default admin account
        adminAccounts.put("admin", new Admin("admin", "admin123", 
                "System Administrator", "admin@gurkha.org.np"));
    }
    
    /**
     * Admin Login
     */
    public boolean adminLogin(String username, String password) {
        Admin admin = adminAccounts.get(username);
        if (admin != null && admin.getPassword().equals(password)) {
            currentAdmin = admin;
            return true;
        }
        return false;
    }
    
    /**
     * Veteran Login - Using service number as username
     */
    public boolean veteranLogin(String serviceNumber, String password) {
        Veteran veteran = veteranController.getVeteranByServiceNumber(serviceNumber);
        // Simple password check - all veterans use "password123" for demo
        if (veteran != null && password.equals("password123")) {
            currentVeteran = veteran;
            return true;
        }
        return false;
    }
    
    /**
     * Logout
     */
    public void logout() {
        currentAdmin = null;
        currentVeteran = null;
    }
    
    // Getters
    public Admin getCurrentAdmin() {
        return currentAdmin;
    }
    
    public Veteran getCurrentVeteran() {
        return currentVeteran;
    }
    
    public boolean isAdminLoggedIn() {
        return currentAdmin != null;
    }
    
    public boolean isVeteranLoggedIn() {
        return currentVeteran != null;
    }
}
