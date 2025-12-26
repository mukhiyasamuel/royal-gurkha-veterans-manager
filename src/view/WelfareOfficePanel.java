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
        
        // Title
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(ColorScheme.WHITE);
        
        JLabel titleLabel = new JLabel("Welfare Office Locations");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titleLabel.setForeground(ColorScheme.PRIMARY_DARK);
        
        topPanel.add(titleLabel, BorderLayout.WEST);
        add(topPanel, BorderLayout.NORTH);
        
        // Table
        String[] columns = {"Office ID", "Office Name", "City", "Address", 
                           "Contact", "Email", "Services", "Hours"};
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
        
        // Adjust column widths
        officeTable.getColumnModel().getColumn(1).setPreferredWidth(200);
        officeTable.getColumnModel().getColumn(3).setPreferredWidth(200);
        officeTable.getColumnModel().getColumn(6).setPreferredWidth(300);
        
        JScrollPane scrollPane = new JScrollPane(officeTable);
        add(scrollPane, BorderLayout.CENTER);
    }
    
    private void loadOfficesIntoTable() {
        tableModel.setRowCount(0);
        
        LinkedList<WelfareOffice> offices = controller.getAllOffices();
        for (WelfareOffice office : offices) {
            tableModel.addRow(new Object[]{
                office.getOfficeId(),
                office.getOfficeName(),
                office.getCity(),
                office.getFullAddress(),
                office.getContactNumber(),
                office.getEmail(),
                office.getServicesOffered(),
                office.getOperatingHours()
            });
        }
    }
}
