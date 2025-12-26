/*
 * Admin Dashboard View
 * Created using NetBeans GUI Builder
 */
package view;

import controller.AuthController;
import controller.VeteranController;
import controller.WelfareOfficeController;
import model.Veteran;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Queue;

/**
 * Admin Dashboard - Main admin panel with CardLayout for tab switching
 * @author Princey Sunar
 */
public class AdminDashboardView extends javax.swing.JFrame {
    private HomePage homePage;
    private VeteranController veteranController;
    private WelfareOfficeController welfareController;
    private AuthController authController;
    
    private CardLayout contentCardLayout;
    
    /**
     * Creates new form AdminDashboardView
     */
    public AdminDashboardView(HomePage homePage, VeteranController veteranController, 
                             AuthController authController) {
        this.homePage = homePage;
        this.veteranController = veteranController;
        this.authController = authController;
        this.welfareController = new WelfareOfficeController();
        
        initComponents();
        setupContentCards();
    }
    
    /**
     * Setup the CardLayout with different content panels
     */
    private void setupContentCards() {
        contentCardLayout = new CardLayout();
        contentPanel.setLayout(contentCardLayout);
        
        // Add different tabs as cards
        contentPanel.add(createDashboardTab(), "DASHBOARD");
        contentPanel.add(new VeteranManagementPanel(veteranController), "VETERANS");
        contentPanel.add(new WelfareOfficePanel(welfareController), "OFFICES");
        
        // Show dashboard by default
        showTab("DASHBOARD");
    }
    
    /**
     * Show a specific tab
     */
    private void showTab(String tabName) {
        // Reset all button backgrounds
        dashboardButton.setBackground(new Color(42, 87, 42));
        veteransButton.setBackground(new Color(42, 87, 42));
        officesButton.setBackground(new Color(42, 87, 42));
        
        // Highlight active button
        Color activeColor = new Color(76, 139, 76);
        switch (tabName) {
            case "DASHBOARD":
                dashboardButton.setBackground(activeColor);
                break;
            case "VETERANS":
                veteransButton.setBackground(activeColor);
                break;
            case "OFFICES":
                officesButton.setBackground(activeColor);
                break;
        }
        
        contentCardLayout.show(contentPanel, tabName);
    }
    
    
    /**
     * Create the Dashboard Tab with statistics and recent veterans
     */
    private JPanel createDashboardTab() {
        JPanel panel = new JPanel(new BorderLayout(20, 20));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Title
        JLabel titleLabel = new JLabel("Dashboard Overview");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titleLabel.setForeground(new Color(20, 50, 20));
        panel.add(titleLabel, BorderLayout.NORTH);
        
        // Statistics Cards
        JPanel statsPanel = new JPanel(new GridLayout(2, 4, 15, 15));
        statsPanel.setBackground(Color.WHITE);
        
        // Stat cards
        int totalVeterans = veteranController.getTotalCount();
        int gpsCount = veteranController.getCountByScheme("GPS");
        int afpsCount = veteranController.getCountByScheme("AFPS");
        double avgYears = veteranController.getAverageServiceYears();
        int totalOffices = welfareController.getTotalCount();
        
        statsPanel.add(createStatCard("Total Veterans", String.valueOf(totalVeterans), 
                new Color(42, 87, 42)));
        statsPanel.add(createStatCard("GPS Scheme", String.valueOf(gpsCount), 
                new Color(178, 34, 34)));
        statsPanel.add(createStatCard("AFPS Scheme", String.valueOf(afpsCount), 
                new Color(23, 162, 184)));
        statsPanel.add(createStatCard("Avg Service Years", 
                String.format("%.1f", avgYears), new Color(218, 165, 32)));
        statsPanel.add(createStatCard("Welfare Offices", String.valueOf(totalOffices), 
                new Color(40, 167, 69)));
        statsPanel.add(createStatCard("Active Status", String.valueOf(totalVeterans), 
                new Color(255, 193, 7)));
        statsPanel.add(createStatCard("Regions Served", "5", new Color(76, 139, 76)));
        statsPanel.add(createStatCard("System Status", "Online", new Color(40, 167, 69)));
        
        JPanel centerPanel = new JPanel(new BorderLayout(0, 20));
        centerPanel.setBackground(Color.WHITE);
        centerPanel.add(statsPanel, BorderLayout.NORTH);
        
        // Recently Added Veterans (Using Queue)
        JPanel recentPanel = createRecentVeteransPanel();
        centerPanel.add(recentPanel, BorderLayout.CENTER);
        
        panel.add(centerPanel, BorderLayout.CENTER);
        
        return panel;
    }
    
    /**
     * Create panel showing recently added veterans from Queue
     */
    private JPanel createRecentVeteransPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(
                        BorderFactory.createLineBorder(new Color(42, 87, 42), 2),
                        "Recently Added Veterans (Last 5)",
                        0, 0, new Font("Segoe UI", Font.BOLD, 16), new Color(20, 50, 20)),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        
        // Table
        String[] columns = {"Service No.", "Name", "Rank", "Scheme", "Service Years"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        // Get recently added from Queue
        Queue<Veteran> recent = veteranController.getRecentlyAdded();
        for (Veteran v : recent) {
            model.addRow(new Object[]{
                v.getServiceNumber(),
                v.getFullName(),
                v.getRank(),
                v.getPensionScheme(),
                v.getServiceYears()
            });
        }
        
        JTable table = new JTable(model);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.setRowHeight(30);
        table.getTableHeader().setBackground(new Color(20, 50, 20));
        table.getTableHeader().setForeground(Color.WHITE);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(0, 200));
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    /**
     * Create a statistic card
     */
    private JPanel createStatCard(String label, String value, Color color) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(color, 2),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)));
        
        JLabel valueLabel = new JLabel(value, SwingConstants.CENTER);
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        valueLabel.setForeground(color);
        
        JLabel labelLabel = new JLabel(label, SwingConstants.CENTER);
        labelLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        labelLabel.setForeground(new Color(51, 51, 51));
        
        card.add(valueLabel, BorderLayout.CENTER);
        card.add(labelLabel, BorderLayout.SOUTH);
        
        return card;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        headerPanel = new javax.swing.JPanel();
        dashboardTitleLabel = new javax.swing.JLabel();
        logoutButton = new javax.swing.JButton();
        sidebarPanel = new javax.swing.JPanel();
        dashboardButton = new javax.swing.JButton();
        veteransButton = new javax.swing.JButton();
        officesButton = new javax.swing.JButton();
        contentPanel = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Admin Dashboard - Royal Gurkha VMS");

        headerPanel.setBackground(new java.awt.Color(20, 50, 20));
        headerPanel.setPreferredSize(new java.awt.Dimension(1200, 70));

        dashboardTitleLabel.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        dashboardTitleLabel.setForeground(new java.awt.Color(218, 165, 32));
        dashboardTitleLabel.setText("ADMIN DASHBOARD");

        logoutButton.setBackground(new java.awt.Color(178, 34, 34));
        logoutButton.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        logoutButton.setForeground(new java.awt.Color(255, 255, 255));
        logoutButton.setText("Logout");
        logoutButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        logoutButton.setFocusPainted(false);
        logoutButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logoutButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout headerPanelLayout = new javax.swing.GroupLayout(headerPanel);
        headerPanel.setLayout(headerPanelLayout);
        headerPanelLayout.setHorizontalGroup(
            headerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(headerPanelLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(dashboardTitleLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(logoutButton, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30))
        );
        headerPanelLayout.setVerticalGroup(
            headerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(headerPanelLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(headerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(dashboardTitleLabel)
                    .addComponent(logoutButton, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        sidebarPanel.setBackground(new java.awt.Color(42, 87, 42));
        sidebarPanel.setPreferredSize(new java.awt.Dimension(220, 600));

        dashboardButton.setBackground(new java.awt.Color(42, 87, 42));
        dashboardButton.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        dashboardButton.setForeground(new java.awt.Color(255, 255, 255));
        dashboardButton.setText("📊 Dashboard");
        dashboardButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        dashboardButton.setFocusPainted(false);
        dashboardButton.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        dashboardButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dashboardButtonActionPerformed(evt);
            }
        });

        veteransButton.setBackground(new java.awt.Color(42, 87, 42));
        veteransButton.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        veteransButton.setForeground(new java.awt.Color(255, 255, 255));
        veteransButton.setText("👥 Manage Veterans");
        veteransButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        veteransButton.setFocusPainted(false);
        veteransButton.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        veteransButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                veteransButtonActionPerformed(evt);
            }
        });

        officesButton.setBackground(new java.awt.Color(42, 87, 42));
        officesButton.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        officesButton.setForeground(new java.awt.Color(255, 255, 255));
        officesButton.setText("🏢 Welfare Offices");
        officesButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        officesButton.setFocusPainted(false);
        officesButton.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        officesButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                officesButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout sidebarPanelLayout = new javax.swing.GroupLayout(sidebarPanel);
        sidebarPanel.setLayout(sidebarPanelLayout);
        sidebarPanelLayout.setHorizontalGroup(
            sidebarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(dashboardButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(veteransButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(officesButton, javax.swing.GroupLayout.DEFAULT_SIZE, 220, Short.MAX_VALUE)
        );
        sidebarPanelLayout.setVerticalGroup(
            sidebarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sidebarPanelLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(dashboardButton, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(veteransButton, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(officesButton, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(436, Short.MAX_VALUE))
        );

        contentPanel.setBackground(new java.awt.Color(255, 255, 255));
        contentPanel.setLayout(new java.awt.CardLayout());

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(headerPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(sidebarPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(contentPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(headerPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(sidebarPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(contentPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void logoutButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logoutButtonActionPerformed
        authController.logout();
        this.dispose();
        homePage.setVisible(true);
    }//GEN-LAST:event_logoutButtonActionPerformed

    private void dashboardButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dashboardButtonActionPerformed
        showTab("DASHBOARD");
    }//GEN-LAST:event_dashboardButtonActionPerformed

    private void veteransButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_veteransButtonActionPerformed
        showTab("VETERANS");
    }//GEN-LAST:event_veteransButtonActionPerformed

    private void officesButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_officesButtonActionPerformed
        showTab("OFFICES");
    }//GEN-LAST:event_officesButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel contentPanel;
    private javax.swing.JButton dashboardButton;
    private javax.swing.JLabel dashboardTitleLabel;
    private javax.swing.JPanel headerPanel;
    private javax.swing.JButton logoutButton;
    private javax.swing.JButton officesButton;
    private javax.swing.JPanel sidebarPanel;
    private javax.swing.JButton veteransButton;
    // End of variables declaration//GEN-END:variables
}
