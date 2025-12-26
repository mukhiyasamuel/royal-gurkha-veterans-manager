package view;

import controller.VeteranController;
import controller.WelfareOfficeController;
import java.awt.*;
import java.util.LinkedList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import model.Veteran;
import model.WelfareOffice;

/**
 * Veteran Dashboard - User panel for veteran to view their information
 */
public class VeteranDashboardView extends JFrame {
    private HomePage homePage;
    private Veteran veteran;
    private VeteranController veteranController;
    private WelfareOfficeController welfareController;
    
    private CardLayout contentCardLayout;
    private JPanel contentPanel;
    
    public VeteranDashboardView(HomePage homePage, Veteran veteran, 
                               VeteranController veteranController) {
        this.homePage = homePage;
        this.veteran = veteran;
        this.veteranController = veteranController;
        this.welfareController = new WelfareOfficeController();
        initComponents();
        setTitle("Veteran Dashboard - Royal Gurkha VMS");
        setSize(1200, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }
    
    private void initComponents() {
        setLayout(new BorderLayout());
        getContentPane().setBackground(ColorScheme.WHITE);
        
        // Header
        add(createHeader(), BorderLayout.NORTH);
        
        // Sidebar
        add(createSidebar(), BorderLayout.WEST);
        
        // Content with CardLayout
        contentCardLayout = new CardLayout();
        contentPanel = new JPanel(contentCardLayout);
        
        contentPanel.add(createProfileTab(), "PROFILE");
        contentPanel.add(createPensionTab(), "PENSION");
        contentPanel.add(createWelfareTab(), "WELFARE");
        
        add(contentPanel, BorderLayout.CENTER);
        
        showTab("PROFILE");
    }
    
    private JPanel createHeader() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(ColorScheme.SECONDARY);
        panel.setPreferredSize(new Dimension(0, 70));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 30, 15, 30));
        
        JLabel titleLabel = new JLabel("VETERAN PORTAL");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(ColorScheme.ACCENT_GOLD);
        
        JLabel welcomeLabel = new JLabel("Welcome, " + veteran.getRank() + " " + 
                veteran.getFullName());
        welcomeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        welcomeLabel.setForeground(ColorScheme.WHITE);
        
        JButton logoutBtn = new JButton("Logout");
        logoutBtn.setBackground(ColorScheme.BUTTON_GOLD);
        logoutBtn.setForeground(ColorScheme.WHITE);
        logoutBtn.setFocusPainted(false);
        logoutBtn.setBorderPainted(false);
        logoutBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        logoutBtn.addActionListener(e -> {
            this.dispose();
            homePage.setVisible(true);
        });
        
        JPanel leftPanel = new JPanel(new GridLayout(2, 1));
        leftPanel.setOpaque(false);
        leftPanel.add(titleLabel);
        leftPanel.add(welcomeLabel);
        
        panel.add(leftPanel, BorderLayout.WEST);
        panel.add(logoutBtn, BorderLayout.EAST);
        
        return panel;
    }
    
    private JPanel createSidebar() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(ColorScheme.SECONDARY_LIGHT);
        panel.setPreferredSize(new Dimension(200, 0));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        
        JButton profileBtn = createNavButton("👤 My Profile", "PROFILE");
        JButton pensionBtn = createNavButton("💰 Pension Details", "PENSION");
        JButton welfareBtn = createNavButton("🏢 Welfare Offices", "WELFARE");
        
        panel.add(profileBtn);
        panel.add(Box.createVerticalStrut(5));
        panel.add(pensionBtn);
        panel.add(Box.createVerticalStrut(5));
        panel.add(welfareBtn);
        panel.add(Box.createVerticalGlue());
        
        return panel;
    }
    
    private JButton createNavButton(String text, String tabName) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        button.setBackground(ColorScheme.SECONDARY_LIGHT);
        button.setForeground(ColorScheme.WHITE);
        button.setMaximumSize(new Dimension(200, 45));
        button.setAlignmentX(Component.LEFT_ALIGNMENT);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 10));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.addActionListener(e -> showTab(tabName));
        
        return button;
    }
    
    private void showTab(String tabName) {
        contentCardLayout.show(contentPanel, tabName);
    }
    
    private JPanel createProfileTab() {
        JPanel panel = new JPanel(new BorderLayout(20, 20));
        panel.setBackground(ColorScheme.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        
        JLabel titleLabel = new JLabel("My Profile");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titleLabel.setForeground(ColorScheme.PRIMARY_DARK);
        panel.add(titleLabel, BorderLayout.NORTH);
        
        // Profile information
        JPanel infoPanel = new JPanel(new GridLayout(0, 2, 20, 15));
        infoPanel.setBackground(ColorScheme.WHITE);
        infoPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(ColorScheme.SECONDARY, 2),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)));
        
        addInfoRow(infoPanel, "Service Number:", veteran.getServiceNumber());
        addInfoRow(infoPanel, "Full Name:", veteran.getFullName());
        addInfoRow(infoPanel, "Rank:", veteran.getRank());
        addInfoRow(infoPanel, "Regiment:", veteran.getRegiment());
        addInfoRow(infoPanel, "Date of Birth:", veteran.getDateOfBirth().toString());
        addInfoRow(infoPanel, "Age:", veteran.getAge() + " years");
        addInfoRow(infoPanel, "Service Start:", veteran.getServiceStartDate().toString());
        addInfoRow(infoPanel, "Service End:", veteran.getServiceEndDate().toString());
        addInfoRow(infoPanel, "Total Service:", veteran.getServiceYears() + " years");
        addInfoRow(infoPanel, "Status:", veteran.getStatus());
        addInfoRow(infoPanel, "Contact:", veteran.getContactNumber());
        addInfoRow(infoPanel, "Address:", veteran.getAddress());
        
        panel.add(infoPanel, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel createPensionTab() {
        JPanel panel = new JPanel(new BorderLayout(20, 20));
        panel.setBackground(ColorScheme.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        
        JLabel titleLabel = new JLabel("Pension Details");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titleLabel.setForeground(ColorScheme.PRIMARY_DARK);
        panel.add(titleLabel, BorderLayout.NORTH);
        
        // Pension info
        JPanel pensionPanel = new JPanel(new GridLayout(0, 2, 20, 15));
        pensionPanel.setBackground(ColorScheme.WHITE);
        pensionPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(ColorScheme.ACCENT_GOLD, 3),
                BorderFactory.createEmptyBorder(30, 30, 30, 30)));
        
        String scheme = veteran.getPensionScheme();
        String schemeFullName = scheme.equals("GPS") ? 
                "Gurkha Pension Scheme (GPS)" : 
                "Armed Forces Pension Scheme (AFPS)";
        
        addInfoRow(pensionPanel, "Pension Scheme:", schemeFullName);
        addInfoRow(pensionPanel, "Monthly Amount:", 
                "NPR " + String.format("%,.2f", veteran.getMonthlyPension()));
        addInfoRow(pensionPanel, "Payment Frequency:", "Monthly (1st of each month)");
        addInfoRow(pensionPanel, "Service Years:", veteran.getServiceYears() + " years");
        addInfoRow(pensionPanel, "Eligibility Status:", "Confirmed");
        
        JLabel noteLabel = new JLabel("<html><i>Note: Pension is calculated based on " +
                "rank, service years, and scheme type.</i></html>");
        noteLabel.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        noteLabel.setForeground(ColorScheme.DARK_GRAY);
        
        JPanel centerPanel = new JPanel(new BorderLayout(0, 20));
        centerPanel.setBackground(ColorScheme.WHITE);
        centerPanel.add(pensionPanel, BorderLayout.CENTER);
        centerPanel.add(noteLabel, BorderLayout.SOUTH);
        
        panel.add(centerPanel, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel createWelfareTab() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(ColorScheme.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        
        JLabel titleLabel = new JLabel("Welfare Office Locations");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titleLabel.setForeground(ColorScheme.PRIMARY_DARK);
        panel.add(titleLabel, BorderLayout.NORTH);
        
        // Table of welfare offices
        String[] columns = {"Office Name", "City", "Contact", "Services"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        LinkedList<WelfareOffice> offices = welfareController.getAllOffices();
        for (WelfareOffice office : offices) {
            model.addRow(new Object[]{
                office.getOfficeName(),
                office.getCity(),
                office.getContactNumber(),
                office.getServicesOffered()
            });
        }
        
        JTable table = new JTable(model);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.setRowHeight(35);
        table.getTableHeader().setBackground(ColorScheme.TABLE_HEADER);
        table.getTableHeader().setForeground(ColorScheme.WHITE);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        
        table.getColumnModel().getColumn(0).setPreferredWidth(250);
        table.getColumnModel().getColumn(3).setPreferredWidth(300);
        
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    private void addInfoRow(JPanel panel, String label, String value) {
        JLabel lblLabel = new JLabel(label);
        lblLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblLabel.setForeground(ColorScheme.PRIMARY_DARK);
        
        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        valueLabel.setForeground(ColorScheme.TEXT_DARK);
        
        panel.add(lblLabel);
        panel.add(valueLabel);
    }
}
