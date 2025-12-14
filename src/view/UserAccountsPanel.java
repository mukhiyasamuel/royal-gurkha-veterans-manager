package view;

import model.DataStore;
import model.UserAccount;

import javax.swing.*;
import java.awt.*;

public class UserAccountsPanel extends JPanel {
    private DataStore store;
    private DefaultTableModel tableModel;

    public UserAccountsPanel(DataStore store) {
        this.store = store;
        setLayout(new BorderLayout());

        // Table
        tableModel = new DefaultTableModel(new Object[]{"Username","Password","Service Number"},0);
        JTable table = new JTable(tableModel);
        refreshTable();

        add(new JScrollPane(table), BorderLayout.CENTER);

        // Form
        JPanel formPanel = new JPanel(new GridLayout(4,2,8,8));
        JTextField usernameField = new JTextField();
        JTextField passwordField = new JTextField();
        JTextField serviceNumberField = new JTextField();
        JButton addButton = new JButton("Add Account");

        formPanel.add(new JLabel("Username:"));
        formPanel.add(usernameField);
        formPanel.add(new JLabel("Password:"));
        formPanel.add(passwordField);
        formPanel.add(new JLabel("Service Number:"));
        formPanel.add(serviceNumberField);
        formPanel.add(new JLabel()); // spacer
        formPanel.add(addButton);

        add(formPanel, BorderLayout.SOUTH);

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
        });
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        for(UserAccount u : store.getUsers()) {
            tableModel.addRow(new Object[]{u.getUsername(), u.getPassword(), u.getVeteranServiceNumber()});
        }
    }
}
