package view;

import controller.ValidationUtil;
import controller.VeteranController;
import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import model.Veteran;

/**
 * Veteran Management Panel - CRUD operations for veterans
 */
public class VeteranManagementPanel extends JPanel {
    private VeteranController controller;
    private JTable veteranTable;
    private DefaultTableModel tableModel;
    
    public VeteranManagementPanel(VeteranController controller) {
        this.controller = controller;
        initComponents();
        loadVeteransIntoTable();
    }
    
    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        setBackground(ColorScheme.WHITE);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Top Panel with Title and Search/Sort controls
        JPanel topPanel = new JPanel(new BorderLayout(10, 10));
        topPanel.setBackground(ColorScheme.WHITE);
        
        // Title
        JLabel titleLabel = new JLabel("Manage Veterans");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titleLabel.setForeground(ColorScheme.PRIMARY_DARK);
        topPanel.add(titleLabel, BorderLayout.NORTH);
        
        // Search and Sort Panel
        JPanel searchSortPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        searchSortPanel.setBackground(ColorScheme.WHITE);
        
        // Search field
        JTextField searchField = new JTextField(30);
        searchField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        searchField.setPreferredSize(new Dimension(300, 35));
        searchField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(ColorScheme.GRAY, 1),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        
        // Search button
        JButton searchBtn = new JButton("Search");
        searchBtn.setBackground(ColorScheme.PRIMARY);
        searchBtn.setForeground(ColorScheme.WHITE);
        searchBtn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        searchBtn.setFocusPainted(false);
        searchBtn.setBorderPainted(false);
        searchBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        searchBtn.setPreferredSize(new Dimension(100, 35));
        searchBtn.addActionListener(e -> searchVeteran(searchField.getText()));
        
        // Sort by Name button
        JButton sortNameBtn = new JButton("Sort by Name");
        sortNameBtn.setBackground(ColorScheme.INFO);
        sortNameBtn.setForeground(ColorScheme.WHITE);
        sortNameBtn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        sortNameBtn.setFocusPainted(false);
        sortNameBtn.setBorderPainted(false);
        sortNameBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        sortNameBtn.setPreferredSize(new Dimension(130, 35));
        sortNameBtn.addActionListener(e -> sortByName());
        
        // Sort by Rank button
        JButton sortRankBtn = new JButton("Sort By Rank");
        sortRankBtn.setBackground(ColorScheme.INFO);
        sortRankBtn.setForeground(ColorScheme.WHITE);
        sortRankBtn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        sortRankBtn.setFocusPainted(false);
        sortRankBtn.setBorderPainted(false);
        sortRankBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        sortRankBtn.setPreferredSize(new Dimension(130, 35));
        sortRankBtn.addActionListener(e -> sortByRank());
        
        // Sort by Age button
        JButton sortAgeBtn = new JButton("Sort by Age");
        sortAgeBtn.setBackground(ColorScheme.INFO);
        sortAgeBtn.setForeground(ColorScheme.WHITE);
        sortAgeBtn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        sortAgeBtn.setFocusPainted(false);
        sortAgeBtn.setBorderPainted(false);
        sortAgeBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        sortAgeBtn.setPreferredSize(new Dimension(120, 35));
        sortAgeBtn.addActionListener(e -> sortByAge());
        
        searchSortPanel.add(searchField);
        searchSortPanel.add(searchBtn);
        searchSortPanel.add(sortNameBtn);
        searchSortPanel.add(sortRankBtn);
        searchSortPanel.add(sortAgeBtn);
        
        topPanel.add(searchSortPanel, BorderLayout.CENTER);
        add(topPanel, BorderLayout.NORTH);
        
        // Table (without Actions column)
        String[] columns = {"Service No.", "Name", "Rank", "Scheme", "Pension (NPR)", 
                           "Service Years", "Status"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        veteranTable = new JTable(tableModel);
        veteranTable.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        veteranTable.setRowHeight(35);
        veteranTable.getTableHeader().setBackground(ColorScheme.TABLE_HEADER);
        veteranTable.getTableHeader().setForeground(ColorScheme.WHITE);
        veteranTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        veteranTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        JScrollPane scrollPane = new JScrollPane(veteranTable);
        add(scrollPane, BorderLayout.CENTER);
        
        // Bottom panel with Add, Delete, Update buttons
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        bottomPanel.setBackground(ColorScheme.WHITE);
        
        JButton addBtn = new JButton("Add");
        addBtn.setBackground(ColorScheme.SUCCESS);
        addBtn.setForeground(ColorScheme.WHITE);
        addBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        addBtn.setFocusPainted(false);
        addBtn.setBorderPainted(false);
        addBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        addBtn.setPreferredSize(new Dimension(100, 35));
        addBtn.addActionListener(e -> showAddVeteranDialog());
        
        JButton deleteBtn = new JButton("Delete");
        deleteBtn.setBackground(ColorScheme.SECONDARY);
        deleteBtn.setForeground(ColorScheme.WHITE);
        deleteBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        deleteBtn.setFocusPainted(false);
        deleteBtn.setBorderPainted(false);
        deleteBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        deleteBtn.setPreferredSize(new Dimension(100, 35));
        deleteBtn.addActionListener(e -> deleteSelectedVeteran());
        
        JButton updateBtn = new JButton("Update");
        updateBtn.setBackground(ColorScheme.INFO);
        updateBtn.setForeground(ColorScheme.WHITE);
        updateBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        updateBtn.setFocusPainted(false);
        updateBtn.setBorderPainted(false);
        updateBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        updateBtn.setPreferredSize(new Dimension(100, 35));
        updateBtn.addActionListener(e -> updateSelectedVeteran());
        
        bottomPanel.add(addBtn);
        bottomPanel.add(deleteBtn);
        bottomPanel.add(updateBtn);
        add(bottomPanel, BorderLayout.SOUTH);
    }
    
    private void searchVeteran(String searchText) {
        if (searchText.trim().isEmpty()) {
            loadVeteransIntoTable();
            return;
        }
        
        tableModel.setRowCount(0);
        
        // Try searching by service number first (binary search)
        Veteran veteran = controller.binarySearchByServiceNumber(searchText.trim());
        if (veteran != null) {
            tableModel.addRow(new Object[]{
                veteran.getServiceNumber(),
                veteran.getFullName(),
                veteran.getRank(),
                veteran.getPensionScheme(),
                String.format("%,.2f", veteran.getMonthlyPension()),
                veteran.getServiceYears(),
                veteran.getStatus()
            });
        } else {
            // Search by name (linear search)
            ArrayList<Veteran> results = controller.linearSearchByName(searchText.trim());
            for (Veteran v : results) {
                tableModel.addRow(new Object[]{
                    v.getServiceNumber(),
                    v.getFullName(),
                    v.getRank(),
                    v.getPensionScheme(),
                    String.format("%,.2f", v.getMonthlyPension()),
                    v.getServiceYears(),
                    v.getStatus()
                });
            }
            
            if (results.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                    "No veterans found matching '" + searchText + "'",
                    "Search Results",
                    JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
    
    private void sortByName() {
        controller.sortByName(true);
        loadVeteransIntoTable();
        JOptionPane.showMessageDialog(this,
            "Veterans sorted by name successfully!",
            "Sort Complete",
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void sortByRank() {
        // Sort using service years as proxy for rank seniority
        controller.sortByServiceYears(false);
        loadVeteransIntoTable();
        JOptionPane.showMessageDialog(this,
            "Veterans sorted by rank (service years) successfully!",
            "Sort Complete",
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void sortByAge() {
        // Sort by service years (older veterans have more service years)
        controller.sortByServiceYears(false);
        loadVeteransIntoTable();
        JOptionPane.showMessageDialog(this,
            "Veterans sorted by age (service years) successfully!",
            "Sort Complete",
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void loadVeteransIntoTable() {
        tableModel.setRowCount(0); // Clear existing rows
        
        ArrayList<Veteran> veterans = controller.getAllVeterans();
        for (Veteran v : veterans) {
            tableModel.addRow(new Object[]{
                v.getServiceNumber(),
                v.getFullName(),
                v.getRank(),
                v.getPensionScheme(),
                String.format("%,.2f", v.getMonthlyPension()),
                v.getServiceYears(),
                v.getStatus()
            });
        }
    }
    
    private void deleteSelectedVeteran() {
        int selectedRow = veteranTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, 
                "Please select a veteran to delete!", 
                "No Selection", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String serviceNo = (String) tableModel.getValueAt(selectedRow, 0);
        int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to delete veteran " + serviceNo + "?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            controller.deleteVeteran(serviceNo);
            loadVeteransIntoTable();
            JOptionPane.showMessageDialog(this, "Veteran deleted successfully!");
        }
    }
    
    private void updateSelectedVeteran() {
        int selectedRow = veteranTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, 
                "Please select a veteran to update!", 
                "No Selection", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String serviceNo = (String) tableModel.getValueAt(selectedRow, 0);
        Veteran veteran = controller.getVeteranByServiceNumber(serviceNo);
        
        if (veteran != null) {
            showEditVeteranDialog(veteran);
        }
    }
    
    private void showAddVeteranDialog() {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), 
                "Add New Veteran", true);
        dialog.setSize(500, 650);
        dialog.setLocationRelativeTo(this);
        
        // Main panel with title
        JPanel mainPanel = new JPanel(new BorderLayout(0, 15));
        mainPanel.setBackground(Color.WHITE);
        
        // Title panel with colored background
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(ColorScheme.PRIMARY);
        titlePanel.setPreferredSize(new Dimension(500, 60));
        titlePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 15));
        
        JLabel titleLabel = new JLabel("Add New Veteran");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);
        titlePanel.add(titleLabel);
        
        mainPanel.add(titlePanel, BorderLayout.NORTH);
        
        // Form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        int row = 0;
        
        // Form fields with improved styling
        JTextField serviceField = createStyledTextField(15);
        JTextField nameField = createStyledTextField(15);
        JTextField rankField = createStyledTextField(15);
        JTextField dobField = createStyledTextField(15);
        JTextField startDateField = createStyledTextField(15);
        JTextField endDateField = createStyledTextField(15);
        JComboBox<String> schemeCombo = new JComboBox<>(new String[]{"GPS", "AFPS"});
        schemeCombo.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        schemeCombo.setPreferredSize(new Dimension(200, 32));
        JTextField pensionField = createStyledTextField(15);
        JTextField contactField = createStyledTextField(15);
        JTextField addressField = createStyledTextField(15);
        JTextField regimentField = createStyledTextField(15);
        
        // Add all fields to panel
        addImprovedFormField(formPanel, gbc, row++, "Service Number:", serviceField, "Format: 6 digits");
        addImprovedFormField(formPanel, gbc, row++, "Full Name:", nameField, "");
        addImprovedFormField(formPanel, gbc, row++, "Rank:", rankField, "e.g., Havildar, Rifleman");
        addImprovedFormField(formPanel, gbc, row++, "Date of Birth:", dobField, "Format: YYYY-MM-DD");
        addImprovedFormField(formPanel, gbc, row++, "Service Start:", startDateField, "Format: YYYY-MM-DD");
        addImprovedFormField(formPanel, gbc, row++, "Service End:", endDateField, "Format: YYYY-MM-DD");
        addImprovedFormField(formPanel, gbc, row++, "Pension Scheme:", schemeCombo, "GPS or AFPS");
        addImprovedFormField(formPanel, gbc, row++, "Monthly Pension:", pensionField, "Amount in NPR");
        addImprovedFormField(formPanel, gbc, row++, "Contact Number:", contactField, "+977-98XXXXXXXX");
        addImprovedFormField(formPanel, gbc, row++, "Address:", addressField, "");
        addImprovedFormField(formPanel, gbc, row++, "Regiment:", regimentField, "");
        
        JScrollPane scrollPane = new JScrollPane(formPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        
        // Buttons panel
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        btnPanel.setBackground(Color.WHITE);
        btnPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(1, 0, 0, 0, ColorScheme.GRAY),
            BorderFactory.createEmptyBorder(10, 0, 10, 0)
        ));
        
        JButton saveBtn = createStyledButton("Save", ColorScheme.SUCCESS);
        saveBtn.addActionListener(e -> {
            try {
                // Validation
                String serviceNo = serviceField.getText().trim();
                if (!ValidationUtil.isValidServiceNumber(serviceNo)) {
                    throw new IllegalArgumentException("Invalid service number! Must be 6 digits.");
                }
                
                String name = nameField.getText().trim();
                if (!ValidationUtil.isValidName(name)) {
                    throw new IllegalArgumentException("Invalid name! Only letters allowed.");
                }
                
                double pension = Double.parseDouble(pensionField.getText().trim());
                if (!ValidationUtil.isValidPensionAmount(pension)) {
                    throw new IllegalArgumentException("Pension amount must be positive!");
                }
                
                // Create veteran
                Veteran veteran = new Veteran(
                        serviceNo,
                        name,
                        rankField.getText().trim(),
                        LocalDate.parse(dobField.getText().trim()),
                        LocalDate.parse(startDateField.getText().trim()),
                        LocalDate.parse(endDateField.getText().trim()),
                        (String) schemeCombo.getSelectedItem(),
                        pension,
                        contactField.getText().trim(),
                        addressField.getText().trim(),
                        regimentField.getText().trim(),
                        "Retired"
                );
                
                controller.addVeteran(veteran);
                loadVeteransIntoTable();
                dialog.dispose();
                JOptionPane.showMessageDialog(this, "Veteran added successfully!", 
                        "Success", JOptionPane.INFORMATION_MESSAGE);
                
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, 
                        "Invalid number format for pension amount!", 
                        "Input Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, ex.getMessage(), 
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        JButton cancelBtn = createStyledButton("Cancel", ColorScheme.GRAY);
        cancelBtn.addActionListener(e -> dialog.dispose());
        
        btnPanel.add(saveBtn);
        btnPanel.add(cancelBtn);
        
        mainPanel.add(btnPanel, BorderLayout.SOUTH);
        
        dialog.add(mainPanel);
        dialog.setVisible(true);
    }
    
    private void showEditVeteranDialog(Veteran veteran) {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), 
                "Update Veteran", true);
        dialog.setSize(500, 650);
        dialog.setLocationRelativeTo(this);
        
        // Main panel with title
        JPanel mainPanel = new JPanel(new BorderLayout(0, 15));
        mainPanel.setBackground(Color.WHITE);
        
        // Title panel with colored background
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(ColorScheme.INFO);
        titlePanel.setPreferredSize(new Dimension(500, 60));
        titlePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 15));
        
        JLabel titleLabel = new JLabel("Update Veteran");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);
        titlePanel.add(titleLabel);
        
        mainPanel.add(titlePanel, BorderLayout.NORTH);
        
        // Form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        int row = 0;
        
        // Pre-fill form fields with existing data
        JTextField serviceField = createStyledTextField(15);
        serviceField.setText(veteran.getServiceNumber());
        serviceField.setEditable(false);
        serviceField.setBackground(new Color(240, 240, 240));
        
        JTextField nameField = createStyledTextField(15);
        nameField.setText(veteran.getFullName());
        
        JTextField rankField = createStyledTextField(15);
        rankField.setText(veteran.getRank());
        
        JTextField dobField = createStyledTextField(15);
        dobField.setText(veteran.getDateOfBirth().toString());
        
        JTextField startDateField = createStyledTextField(15);
        startDateField.setText(veteran.getServiceStartDate().toString());
        
        JTextField endDateField = createStyledTextField(15);
        endDateField.setText(veteran.getServiceEndDate().toString());
        
        JComboBox<String> schemeCombo = new JComboBox<>(new String[]{"GPS", "AFPS"});
        schemeCombo.setSelectedItem(veteran.getPensionScheme());
        schemeCombo.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        schemeCombo.setPreferredSize(new Dimension(200, 32));
        
        JTextField pensionField = createStyledTextField(15);
        pensionField.setText(String.valueOf(veteran.getMonthlyPension()));
        
        JTextField contactField = createStyledTextField(15);
        contactField.setText(veteran.getContactNumber());
        
        JTextField addressField = createStyledTextField(15);
        addressField.setText(veteran.getAddress());
        
        JTextField regimentField = createStyledTextField(15);
        regimentField.setText(veteran.getRegiment());
        
        // Add all fields to panel
        addImprovedFormField(formPanel, gbc, row++, "Service Number:", serviceField, "Cannot be changed");
        addImprovedFormField(formPanel, gbc, row++, "Full Name:", nameField, "");
        addImprovedFormField(formPanel, gbc, row++, "Rank:", rankField, "e.g., Havildar, Rifleman");
        addImprovedFormField(formPanel, gbc, row++, "Date of Birth:", dobField, "Format: YYYY-MM-DD");
        addImprovedFormField(formPanel, gbc, row++, "Service Start:", startDateField, "Format: YYYY-MM-DD");
        addImprovedFormField(formPanel, gbc, row++, "Service End:", endDateField, "Format: YYYY-MM-DD");
        addImprovedFormField(formPanel, gbc, row++, "Pension Scheme:", schemeCombo, "GPS or AFPS");
        addImprovedFormField(formPanel, gbc, row++, "Monthly Pension:", pensionField, "Amount in NPR");
        addImprovedFormField(formPanel, gbc, row++, "Contact Number:", contactField, "+977-98XXXXXXXX");
        addImprovedFormField(formPanel, gbc, row++, "Address:", addressField, "");
        addImprovedFormField(formPanel, gbc, row++, "Regiment:", regimentField, "");
        
        JScrollPane scrollPane = new JScrollPane(formPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        
        // Buttons panel
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        btnPanel.setBackground(Color.WHITE);
        btnPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(1, 0, 0, 0, ColorScheme.GRAY),
            BorderFactory.createEmptyBorder(10, 0, 10, 0)
        ));
        
        JButton updateBtn = createStyledButton("Update", ColorScheme.SUCCESS);
        updateBtn.addActionListener(e -> {
            try {
                // Validation
                String name = nameField.getText().trim();
                if (!ValidationUtil.isValidName(name)) {
                    throw new IllegalArgumentException("Invalid name! Only letters allowed.");
                }
                
                double pension = Double.parseDouble(pensionField.getText().trim());
                if (!ValidationUtil.isValidPensionAmount(pension)) {
                    throw new IllegalArgumentException("Pension amount must be positive!");
                }
                
                // Update veteran
                Veteran updatedVeteran = new Veteran(
                        veteran.getServiceNumber(),
                        name,
                        rankField.getText().trim(),
                        LocalDate.parse(dobField.getText().trim()),
                        LocalDate.parse(startDateField.getText().trim()),
                        LocalDate.parse(endDateField.getText().trim()),
                        (String) schemeCombo.getSelectedItem(),
                        pension,
                        contactField.getText().trim(),
                        addressField.getText().trim(),
                        regimentField.getText().trim(),
                        veteran.getStatus()
                );
                
                controller.updateVeteran(veteran.getServiceNumber(), updatedVeteran);
                loadVeteransIntoTable();
                dialog.dispose();
                JOptionPane.showMessageDialog(this, "Veteran updated successfully!", 
                        "Success", JOptionPane.INFORMATION_MESSAGE);
                
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, 
                        "Invalid number format for pension amount!", 
                        "Input Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, ex.getMessage(), 
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        JButton cancelBtn = createStyledButton("Cancel", ColorScheme.GRAY);
        cancelBtn.addActionListener(e -> dialog.dispose());
        
        btnPanel.add(updateBtn);
        btnPanel.add(cancelBtn);
        
        mainPanel.add(btnPanel, BorderLayout.SOUTH);
        
        dialog.add(mainPanel);
        dialog.setVisible(true);
    }
    
    // Helper method to create styled text fields
    private JTextField createStyledTextField(int columns) {
        JTextField field = new JTextField(columns);
        field.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        field.setPreferredSize(new Dimension(200, 32));
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            BorderFactory.createEmptyBorder(5, 8, 5, 8)
        ));
        return field;
    }
    
    // Helper method to create styled buttons
    private JButton createStyledButton(String text, Color backgroundColor) {
        JButton button = new JButton(text);
        button.setBackground(backgroundColor);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(120, 38));
        return button;
    }
    
    // Improved form field method
    private void addImprovedFormField(JPanel panel, GridBagConstraints gbc, int row, 
                                     String label, JComponent field, String hint) {
        gbc.gridy = row;
        gbc.gridx = 0;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        
        JLabel lbl = new JLabel(label);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lbl.setForeground(ColorScheme.PRIMARY_DARK);
        panel.add(lbl, gbc);
        
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(field, gbc);
        
        if (!hint.isEmpty()) {
            gbc.gridy = row;
            gbc.gridx = 1;
            gbc.anchor = GridBagConstraints.EAST;
            JLabel hintLbl = new JLabel(hint);
            hintLbl.setFont(new Font("Segoe UI", Font.ITALIC, 10));
            hintLbl.setForeground(ColorScheme.DARK_GRAY);
            // Position hint below the field by adding it after
        }
    }
}
