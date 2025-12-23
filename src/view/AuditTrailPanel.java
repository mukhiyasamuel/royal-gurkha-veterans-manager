package view;

import model.DataStore;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;

public class AuditTrailPanel extends JPanel {
    private DataStore store;
    private DefaultTableModel tableModel;
    private JTable table;

    public AuditTrailPanel(DataStore store) {
        this.store = store;
        setLayout(new BorderLayout());

        // Table
        tableModel = new DefaultTableModel(new Object[]{"Audit Log Entries"},0);
        table = new JTable(tableModel);
        refreshTable();

        add(new JScrollPane(table), BorderLayout.CENTER);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton exportButton = new JButton("Export Logs");

        buttonPanel.add(exportButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Export logic
        exportButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Save Audit Logs");
            int userSelection = fileChooser.showSaveDialog(this);

            if (userSelection == JFileChooser.APPROVE_OPTION) {
                try (FileWriter writer = new FileWriter(fileChooser.getSelectedFile())) {
                    for (String log : store.getAuditLogs()) {
                        writer.write(log + System.lineSeparator());
                    }
                    JOptionPane.showMessageDialog(this, "Audit logs exported successfully!");
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(this, "Error exporting logs: " + ex.getMessage());
                }
            }
        });
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        for (String log : store.getAuditLogs()) {
            tableModel.addRow(new Object[]{log});
        }
    }
}
