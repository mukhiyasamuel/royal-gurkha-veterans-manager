package view;

import model.DataStore;
import model.UserAccount;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class UserAccountsPanel extends JPanel {
    private DataStore store;
    private DefaultTableModel tableModel;
    private JTable table;

    public UserAccountsPanel(DataStore store) {
        this.store = store;
        setLayout(new BorderLayout());

        // Table
        tableModel = new DefaultTableModel(new Object[]{"Username","Password","Service Number"},0);
        table = new JTable(tableModel);
        refreshTable();

        add(new JScrollPane(table), BorderLayout.CENTER);

        // Form
        JPanel formPanel = new JPanel(new GridLayout(5,2,8,8));
        JTextField usernameField = new JTextField();
        JTextField passwordField = new JTextField();
        JTextField serviceNumberField = new JTextField();
        JButton addButton = new JButton("Add Account");
        JButton deleteButton = new JButton("Delete Selected");

        formPanel.add(new JLabel("Username:"));
        formPanel.add(usernameField);
        formPanel.add(new JLabel("Password:"));
        formPanel.add(passwordField);
        formPanel.add(new JLabel("Service Number:"));
        formPanel.add(serviceNumberField);
        formPanel.add(addButton);
        formPanel.add(deleteButton);

        add(formPanel, BorderLayout.SOUTH);

        // Add button logic
        addButton.addActionListener(e -> {
            String username = usernameField.getText().trim();
            String password = passwordField.getText().trim();
            String serviceNumber = serviceNumberField.getText().trim();

            if(username.isEmpty() || password.isEmpty() || serviceNumber.isEmpty()) {
                JOptionPane.showMessageDialog(this, "All fields are required.");
                return;
            }

            UserAccount account = new UserAccount(username, password, serviceNumber);
            store.getUsers().add(account);
            JOptionPane.showMessageDialog(this, "Account added successfully!");
            refreshTable();

            // Clear fields
            usernameField.setText("");
            passwordField.setText("");
            serviceNumberField.setText("");
        });

        // Delete button logic
        deleteButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow >= 0) {
                String username = (String) tableModel.getValueAt(selectedRow, 0);
                store.getUsers().removeIf(u -> u.getUsername().equals(username));
                JOptionPane.showMessageDialog(this, "Account deleted successfully!");
                refreshTable();
            } else {
                JOptionPane.showMessageDialog(this, "Please select an account to delete.");
            }
        });
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        for(UserAccount u : store.getUsers()) {
            tableModel.addRow(new Object[]{u.getUsername(), u.getPassword(), u.getVeteranServiceNumber()});
        }
    }
}
