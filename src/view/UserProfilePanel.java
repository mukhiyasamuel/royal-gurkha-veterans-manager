package view;

import model.UserAccount;

import javax.swing.*;
import java.awt.*;

public class UserProfilePanel extends JPanel {
    private UserAccount account;

    public UserProfilePanel(UserAccount account) {
        this.account = account;
        setLayout(new BorderLayout());

        // Form
        JPanel formPanel = new JPanel(new GridLayout(4,2,8,8));
        JTextField usernameField = new JTextField(account.getUsername());
        usernameField.setEditable(false); // username should not be changed
        JPasswordField passwordField = new JPasswordField(account.getPassword());
        JTextField serviceNumberField = new JTextField(account.getVeteranServiceNumber());

        JButton updateButton = new JButton("Update Profile");
        JButton changePasswordButton = new JButton("Change Password");

        formPanel.add(new JLabel("Username:"));
        formPanel.add(usernameField);
        formPanel.add(new JLabel("Password:"));
        formPanel.add(passwordField);
        formPanel.add(new JLabel("Service Number:"));
        formPanel.add(serviceNumberField);
        formPanel.add(updateButton);
        formPanel.add(changePasswordButton);

        add(formPanel, BorderLayout.CENTER);

        // Update profile logic
        updateButton.addActionListener(e -> {
            String newServiceNumber = serviceNumberField.getText().trim();
            if (newServiceNumber.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Service number cannot be empty.");
                return;
            }
            account.setVeteranServiceNumber(newServiceNumber);
            JOptionPane.showMessageDialog(this, "Profile updated successfully!");
        });

        // Change password logic
        changePasswordButton.addActionListener(e -> {
            String newPassword = JOptionPane.showInputDialog(this, "Enter new password:");
            if (newPassword != null && !newPassword.trim().isEmpty()) {
                account.setPassword(newPassword.trim());
                JOptionPane.showMessageDialog(this, "Password updated successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "Password cannot be empty.");
            }
        });
    }
}
