package view;

import controller.VeteranController;
import model.DataStore;

import javax.swing.*;
import java.awt.*;

public class AdminDashboardFrame extends JFrame {
    private DataStore store;
    private VeteranController veteranController;

    public AdminDashboardFrame(DataStore store) {
        this.store = store;
        this.veteranController = new VeteranController(store);

        setTitle("RGR Support System - Admin Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);

        // Tabbed pane
        JTabbedPane tabs = new JTabbedPane();

        // Veterans tab
        tabs.addTab("Veterans", new VeteranListPanel(store, veteranController, this));

        // Eligibility tab
        tabs.addTab("Eligibility", new EligibilityPanel());

        // Welfare Centers tab (placeholder for now)
        tabs.addTab("Welfare Centers", new WelfareCentersPanel(store));


        // Honors tab (placeholder for now)
        tabs.addTab("Honors", new JPanel(new BorderLayout()) {{
            add(new JLabel("Honors list coming soon..."), BorderLayout.CENTER);
        }});

        // Audit Logs tab (placeholder for now)
        tabs.addTab("Audit Logs", new JPanel(new BorderLayout()) {{
            add(new JLabel("Audit logs coming soon..."), BorderLayout.CENTER);
        }});

        add(tabs, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            DataStore store = new DataStore();
            AdminDashboardFrame frame = new AdminDashboardFrame(store);
            frame.setVisible(true);
        });
    }
}
