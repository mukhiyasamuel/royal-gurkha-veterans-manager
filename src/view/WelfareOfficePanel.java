package view;

import controller.WelfareOfficeController;
import java.awt.*;
import java.util.LinkedList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import model.WelfareOffice;

/**
 * Welfare Office Management Panel
 */
public class WelfareOfficePanel extends JPanel {
    private WelfareOfficeController controller;
    private JTable officeTable;
    private DefaultTableModel tableModel;
    
    public WelfareOfficePanel(WelfareOfficeController controller) {
        this.controller = controller;
        initComponents();
        loadOfficesIntoTable();
    }
    
    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        setBackground(ColorScheme.WHITE);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Top Panel with Title and Search
        JPanel topPanel = new JPanel(new BorderLayout(10, 10));
        topPanel.setBackground(ColorScheme.WHITE);
        
        // Title
        JLabel titleLabel = new JLabel("Welfare Office Locations");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titleLabel.setForeground(ColorScheme.PRIMARY_DARK);
        topPanel.add(titleLabel, BorderLayout.NORTH);
        
        // Search Panel
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        searchPanel.setBackground(ColorScheme.WHITE);
        
        JTextField searchField = new JTextField(30);
        searchField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        searchField.setPreferredSize(new Dimension(300, 35));
        searchField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(ColorScheme.GRAY, 1),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        
        JButton searchBtn = new JButton("Search");
        searchBtn.setBackground(ColorScheme.PRIMARY);
        searchBtn.setForeground(ColorScheme.WHITE);
        searchBtn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        searchBtn.setFocusPainted(false);
        searchBtn.setBorderPainted(false);
        searchBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        searchBtn.setPreferredSize(new Dimension(100, 35));
        searchBtn.addActionListener(e -> searchOffice(searchField.getText()));
        
        searchPanel.add(searchField);
        searchPanel.add(searchBtn);
        topPanel.add(searchPanel, BorderLayout.CENTER);
        
        add(topPanel, BorderLayout.NORTH);
        
        // Table - Simplified columns per wireframe
        String[] columns = {"Office Name", "City", "Contact", "Services"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        officeTable = new JTable(tableModel);
        officeTable.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        officeTable.setRowHeight(40);
        officeTable.getTableHeader().setBackground(ColorScheme.TABLE_HEADER);
        officeTable.getTableHeader().setForeground(ColorScheme.WHITE);
        officeTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        officeTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        
        JScrollPane scrollPane = new JScrollPane(officeTable);
        add(scrollPane, BorderLayout.CENTER);
        
        // Bottom panel with Add, Remove, Update buttons
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        bottomPanel.setBackground(ColorScheme.WHITE);
        
        JButton addBtn = new JButton("Add");
        addBtn.setBackground(ColorScheme.SUCCESS);
        addBtn.setForeground(ColorScheme.WHITE);
        addBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        addBtn.setFocusPainted(false);
        addBtn.setBorderPainted(false);
        addBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        addBtn.setPreferredSize(new Dimension(100, 35));
        addBtn.addActionListener(e -> addOffice());
        
        JButton removeBtn = new JButton("Remove");
        removeBtn.setBackground(ColorScheme.SECONDARY);
        removeBtn.setForeground(ColorScheme.WHITE);
        removeBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        removeBtn.setFocusPainted(false);
        removeBtn.setBorderPainted(false);
        removeBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        removeBtn.setPreferredSize(new Dimension(100, 35));
        removeBtn.addActionListener(e -> removeOffice());
        
        JButton updateBtn = new JButton("Update");
        updateBtn.setBackground(ColorScheme.INFO);
        updateBtn.setForeground(ColorScheme.WHITE);
        updateBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        updateBtn.setFocusPainted(false);
        updateBtn.setBorderPainted(false);
        updateBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        updateBtn.setPreferredSize(new Dimension(100, 35));
        updateBtn.addActionListener(e -> updateOffice());
        
        bottomPanel.add(addBtn);
        bottomPanel.add(removeBtn);
        bottomPanel.add(updateBtn);
        add(bottomPanel, BorderLayout.SOUTH);
    }
    
    private void loadOfficesIntoTable() {
        tableModel.setRowCount(0);
        
        LinkedList<WelfareOffice> offices = controller.getAllOffices();
        for (WelfareOffice office : offices) {
            tableModel.addRow(new Object[]{
                office.getOfficeName(),
                office.getCity(),
                office.getContactNumber(),
                office.getServicesOffered()
            });
        }
    }
    
    private void searchOffice(String searchText) {
        if (searchText.trim().isEmpty()) {
            loadOfficesIntoTable();
            return;
        }
        
        tableModel.setRowCount(0);
        LinkedList<WelfareOffice> offices = controller.getAllOffices();
        boolean found = false;
        
        for (WelfareOffice office : offices) {
            if (office.getOfficeName().toLowerCase().contains(searchText.toLowerCase()) ||
                office.getCity().toLowerCase().contains(searchText.toLowerCase())) {
                tableModel.addRow(new Object[]{
                    office.getOfficeName(),
                    office.getCity(),
                    office.getContactNumber(),
                    office.getServicesOffered()
                });
                found = true;
            }
        }
        
        if (!found) {
            javax.swing.JOptionPane.showMessageDialog(this,
                "No offices found matching '" + searchText + "'",
                "Search Results",
                javax.swing.JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void addOffice() {
        javax.swing.JOptionPane.showMessageDialog(this,
            "Add Office functionality - To be implemented",
            "Add Office",
            javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void removeOffice() {
        int selectedRow = officeTable.getSelectedRow();
        if (selectedRow == -1) {
            javax.swing.JOptionPane.showMessageDialog(this,
                "Please select an office to remove!",
                "No Selection",
                javax.swing.JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String officeName = (String) tableModel.getValueAt(selectedRow, 0);
        int confirm = javax.swing.JOptionPane.showConfirmDialog(this,
            "Are you sure you want to remove " + officeName + "?",
            "Confirm Remove",
            javax.swing.JOptionPane.YES_NO_OPTION);
        
        if (confirm == javax.swing.JOptionPane.YES_OPTION) {
            javax.swing.JOptionPane.showMessageDialog(this,
                "Remove functionality - To be implemented",
                "Remove Office",
                javax.swing.JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void updateOffice() {
        int selectedRow = officeTable.getSelectedRow();
        if (selectedRow == -1) {
            javax.swing.JOptionPane.showMessageDialog(this,
                "Please select an office to update!",
                "No Selection",
                javax.swing.JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        javax.swing.JOptionPane.showMessageDialog(this,
            "Update Office functionality - To be implemented",
            "Update Office",
            javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }
}
