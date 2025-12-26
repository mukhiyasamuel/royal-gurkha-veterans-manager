package view;

import controller.VeteranController;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import model.Veteran;

/**
 * Search and Sort Panel - Implements search and sort algorithms
 */
public class SearchSortPanel extends JPanel {
    private VeteranController controller;
    private JTable resultTable;
    private DefaultTableModel tableModel;
    private JTextField searchField;
    private JComboBox<String> searchCriteriaCombo;
    private JComboBox<String> sortCombo;
    
    public SearchSortPanel(VeteranController controller) {
        this.controller = controller;
        initComponents();
        loadAllVeterans();
    }
    
    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        setBackground(ColorScheme.WHITE);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Title
        JLabel titleLabel = new JLabel("Search & Sort Veterans");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titleLabel.setForeground(ColorScheme.PRIMARY_DARK);
        
        // Search Panel
        JPanel searchPanel = createSearchPanel();
        
        // Sort Panel
        JPanel sortPanel = createSortPanel();
        
        // Top panel with title, search, and sort
        JPanel topPanel = new JPanel(new BorderLayout(10, 10));
        topPanel.setBackground(ColorScheme.WHITE);
        topPanel.add(titleLabel, BorderLayout.NORTH);
        
        JPanel controlsPanel = new JPanel(new GridLayout(2, 1, 10, 10));
        controlsPanel.setBackground(ColorScheme.WHITE);
        controlsPanel.add(searchPanel);
        controlsPanel.add(sortPanel);
        topPanel.add(controlsPanel, BorderLayout.CENTER);
        
        add(topPanel, BorderLayout.NORTH);
        
        // Results Table
        String[] columns = {"Service No.", "Name", "Rank", "Scheme", "Pension", "Service Years"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        resultTable = new JTable(tableModel);
        resultTable.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        resultTable.setRowHeight(30);
        resultTable.getTableHeader().setBackground(ColorScheme.TABLE_HEADER);
        resultTable.getTableHeader().setForeground(ColorScheme.WHITE);
        resultTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        
        JScrollPane scrollPane = new JScrollPane(resultTable);
        add(scrollPane, BorderLayout.CENTER);
    }
    
    private JPanel createSearchPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        panel.setBackground(ColorScheme.WHITE);
        panel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(ColorScheme.INFO, 2),
                "Search",
                0, 0, new Font("Segoe UI", Font.BOLD, 14), ColorScheme.PRIMARY_DARK));
        
        JLabel criteriaLabel = new JLabel("Search By:");
        criteriaLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        
        searchCriteriaCombo = new JComboBox<>(new String[]{
            "Name (Linear Search)", "Service Number (Binary Search)", "Pension Scheme"
        });
        searchCriteriaCombo.setPreferredSize(new Dimension(220, 30));
        
        JLabel searchLabel = new JLabel("Search Term:");
        searchLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        
        searchField = new JTextField(20);
        searchField.setPreferredSize(new Dimension(250, 30));
        searchField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(ColorScheme.GRAY, 1),
                BorderFactory.createEmptyBorder(3, 8, 3, 8)));
        
        JButton searchBtn = new JButton("🔍 Search");
        searchBtn.setBackground(ColorScheme.BUTTON_PRIMARY);
        searchBtn.setForeground(ColorScheme.WHITE);
        searchBtn.setFocusPainted(false);
        searchBtn.setBorderPainted(false);
        searchBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        searchBtn.addActionListener(e -> performSearch());
        
        JButton resetBtn = new JButton("Reset");
        resetBtn.setBackground(ColorScheme.GRAY);
        resetBtn.setForeground(ColorScheme.TEXT_DARK);
        resetBtn.setFocusPainted(false);
        resetBtn.setBorderPainted(false);
        resetBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        resetBtn.addActionListener(e -> loadAllVeterans());
        
        panel.add(criteriaLabel);
        panel.add(searchCriteriaCombo);
        panel.add(searchLabel);
        panel.add(searchField);
        panel.add(searchBtn);
        panel.add(resetBtn);
        
        return panel;
    }
    
    private JPanel createSortPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        panel.setBackground(ColorScheme.WHITE);
        panel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(ColorScheme.SUCCESS, 2),
                "Sort",
                0, 0, new Font("Segoe UI", Font.BOLD, 14), ColorScheme.PRIMARY_DARK));
        
        JLabel sortLabel = new JLabel("Sort By:");
        sortLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        
        sortCombo = new JComboBox<>(new String[]{
            "Name (A-Z)", "Name (Z-A)", 
            "Service Years (Low to High)", "Service Years (High to Low)",
            "Pension (Low to High)", "Pension (High to Low)"
        });
        sortCombo.setPreferredSize(new Dimension(250, 30));
        
        JButton sortBtn = new JButton("↕ Apply Sort");
        sortBtn.setBackground(ColorScheme.SUCCESS);
        sortBtn.setForeground(ColorScheme.WHITE);
        sortBtn.setFocusPainted(false);
        sortBtn.setBorderPainted(false);
        sortBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        sortBtn.addActionListener(e -> performSort());
        
        panel.add(sortLabel);
        panel.add(sortCombo);
        panel.add(sortBtn);
        
        return panel;
    }
    
    private void performSearch() {
        String searchTerm = searchField.getText().trim();
        if (searchTerm.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a search term!", 
                    "Input Required", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int criteriaIndex = searchCriteriaCombo.getSelectedIndex();
        ArrayList<Veteran> results = new ArrayList<>();
        
        try {
            switch (criteriaIndex) {
                case 0: // Name - Linear Search
                    results = controller.linearSearchByName(searchTerm);
                    break;
                case 1: // Service Number - Binary Search
                    Veteran veteran = controller.binarySearchByServiceNumber(searchTerm);
                    if (veteran != null) {
                        results.add(veteran);
                    }
                    break;
                case 2: // Pension Scheme
                    results = controller.searchByCriteria(null, searchTerm, null);
                    break;
            }
            
            displayResults(results);
            
            if (results.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No results found!", 
                        "Search Results", JOptionPane.INFORMATION_MESSAGE);
            }
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error during search: " + ex.getMessage(), 
                    "Search Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void performSort() {
        int sortIndex = sortCombo.getSelectedIndex();
        ArrayList<Veteran> sorted = new ArrayList<>();
        
        switch (sortIndex) {
            case 0: // Name A-Z
                sorted = controller.sortByName(true);
                break;
            case 1: // Name Z-A
                sorted = controller.sortByName(false);
                break;
            case 2: // Service Years Low to High
                sorted = controller.sortByServiceYears(true);
                break;
            case 3: // Service Years High to Low
                sorted = controller.sortByServiceYears(false);
                break;
            case 4: // Pension Low to High
                sorted = controller.sortByPension(true);
                break;
            case 5: // Pension High to Low
                sorted = controller.sortByPension(false);
                break;
        }
        
        displayResults(sorted);
    }
    
    private void loadAllVeterans() {
        displayResults(controller.getAllVeterans());
        searchField.setText("");
    }
    
    private void displayResults(ArrayList<Veteran> veterans) {
        tableModel.setRowCount(0);
        
        for (Veteran v : veterans) {
            tableModel.addRow(new Object[]{
                v.getServiceNumber(),
                v.getFullName(),
                v.getRank(),
                v.getPensionScheme(),
                String.format("NPR %,.2f", v.getMonthlyPension()),
                v.getServiceYears()
            });
        }
    }
}
