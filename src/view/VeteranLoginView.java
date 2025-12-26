package view;

import controller.AuthController;
import controller.VeteranController;
import java.awt.*;
import javax.swing.*;
import model.Veteran;

/**
 * Veteran Login View
 */
public class VeteranLoginView extends JFrame {
    private HomePage homePage;
    private JTextField serviceNumberField;
    private JPasswordField passwordField;
    
    public VeteranLoginView(HomePage homePage) {
        this.homePage = homePage;
        initComponents();
        setTitle("Veteran Login - Royal Gurkha VMS");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
    }
    
    private void initComponents() {
        setLayout(new BorderLayout());
        getContentPane().setBackground(ColorScheme.LIGHT_GRAY);
        
        // Header
        JPanel headerPanel = createHeader();
        add(headerPanel, BorderLayout.NORTH);
        
        // Login Form
        JPanel formPanel = createLoginForm();
        add(formPanel, BorderLayout.CENTER);
    }
    
    private JPanel createHeader() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(ColorScheme.SECONDARY);
        panel.setPreferredSize(new Dimension(0, 80));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        
        JLabel titleLabel = new JLabel("VETERAN LOGIN");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(ColorScheme.ACCENT_GOLD);
        
        // Back button
        JButton backButton = new JButton("← Back to Home");
        backButton.setBackground(ColorScheme.PRIMARY_DARK);
        backButton.setForeground(ColorScheme.WHITE);
        backButton.setFocusPainted(false);
        backButton.setBorderPainted(false);
        backButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backButton.addActionListener(e -> {
            this.dispose();
            homePage.setVisible(true);
        });
        
        panel.add(titleLabel, BorderLayout.WEST);
        panel.add(backButton, BorderLayout.EAST);
        
        return panel;
    }
    
    private JPanel createLoginForm() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(ColorScheme.LIGHT_GRAY);
        
        // Form container
        JPanel formContainer = new JPanel(new GridBagLayout());
        formContainer.setBackground(ColorScheme.WHITE);
        formContainer.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(ColorScheme.SECONDARY, 2),
                BorderFactory.createEmptyBorder(40, 40, 40, 40)));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Service Number
        JLabel serviceLabel = new JLabel("Service Number:");
        serviceLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 0;
        formContainer.add(serviceLabel, gbc);
        
        serviceNumberField = new JTextField(20);
        serviceNumberField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        serviceNumberField.setPreferredSize(new Dimension(300, 35));
        serviceNumberField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(ColorScheme.GRAY, 1),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        gbc.gridy = 1;
        formContainer.add(serviceNumberField, gbc);
        
        // Password
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        gbc.gridy = 2;
        formContainer.add(passwordLabel, gbc);
        
        passwordField = new JPasswordField(20);
        passwordField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        passwordField.setPreferredSize(new Dimension(300, 35));
        passwordField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(ColorScheme.GRAY, 1),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        gbc.gridy = 3;
        formContainer.add(passwordField, gbc);
        
        // Login Button
        JButton loginButton = new JButton("LOGIN");
        loginButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        loginButton.setBackground(ColorScheme.BUTTON_SECONDARY);
        loginButton.setForeground(ColorScheme.WHITE);
        loginButton.setPreferredSize(new Dimension(300, 45));
        loginButton.setFocusPainted(false);
        loginButton.setBorderPainted(false);
        loginButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        loginButton.addActionListener(e -> handleLogin());
        gbc.gridy = 4;
        gbc.insets = new Insets(20, 10, 10, 10);
        formContainer.add(loginButton, gbc);
        
        // Info label
        JLabel infoLabel = new JLabel("<html><center>Use your Service Number to login<br/>" +
                "Password: password123</center></html>");
        infoLabel.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        infoLabel.setForeground(ColorScheme.DARK_GRAY);
        gbc.gridy = 5;
        gbc.insets = new Insets(10, 10, 10, 10);
        formContainer.add(infoLabel, gbc);
        
        panel.add(formContainer);
        
        return panel;
    }
    
    private void handleLogin() {
        String serviceNumber = serviceNumberField.getText().trim();
        String password = new String(passwordField.getPassword());
        
        if (serviceNumber.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                    "Please enter both service number and password!", 
                    "Login Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Create controllers
        VeteranController veteranController = new VeteranController();
        AuthController authController = new AuthController(veteranController);
        
        if (authController.veteranLogin(serviceNumber, password)) {
            // Successful login - Open Veteran Dashboard
            this.dispose(); // Close login window
            Veteran loggedInVeteran = authController.getCurrentVeteran();
            VeteranDashboardView dashboard = new VeteranDashboardView(homePage, 
                    loggedInVeteran, veteranController);
            dashboard.setVisible(true);
            
            // Clear fields
            serviceNumberField.setText("");
            passwordField.setText("");
        } else {
            JOptionPane.showMessageDialog(this, 
                    "Invalid service number or password!", 
                    "Login Failed", JOptionPane.ERROR_MESSAGE);
        }
    }
}
