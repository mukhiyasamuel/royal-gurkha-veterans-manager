package view;

import model.Veteran;

import javax.swing.*;
import java.awt.*;

public class UserProfilePanel extends JPanel {
    private Veteran veteran;
    private JTextField contactField;
    private JTextField nextOfKinField;

    public UserProfilePanel(Veteran veteran) {
        this.veteran = veteran;
        setLayout(new GridLayout(3,2,8,8));

        add(new JLabel("Contact:"));
        contactField = new JTextField(veteran.getContact());
        add(contactField);

        add(new JLabel("Next of Kin:"));
        nextOfKinField = new JTextField(veteran.getNextOfKin());
        add(nextOfKinField);

        JButton updateButton = new JButton("Update");
        add(new JLabel()); // spacer
        add(updateButton);

        updateButton.addActionListener(e -> {
            veteran.setContact(contactField.getText().trim());
            veteran.setNextOfKin(nextOfKinField.getText().trim());
            JOptionPane.showMessageDialog(this, "Profile updated successfully!");
        });
    }
}

