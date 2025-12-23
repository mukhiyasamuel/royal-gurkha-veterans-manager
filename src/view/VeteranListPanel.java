package view;

import controller.VeteranController;
import model.DataStore;
import model.Veteran;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class VeteranListPanel extends JPanel {
    private DataStore store;
    private VeteranController veteranController;
    private DefaultTableModel tableModel;
    private JTable table;

    public VeteranListPanel(DataStore store, VeteranController veteranController, JFrame parent) {
        this.store = store;
        this.veteranController = veteranController;
        setLayout(new BorderLayout());

        // Table
        tableModel = new DefaultTableModel(new Object[]{"Name","Service Number","Rank","Contact","Next of Kin"},0);
        table = new JTable(tableModel);
        refreshTable();

        add(new JScrollPane(table), BorderLayout.CENTER);

        // --- Button panel ---
        JPanel buttonPanel = new JPanel(new FlowLayout());

        JButton addButton = new JButton("Add Veteran");
        JButton deleteButton = new JButton("Delete Selected");
        JButton editButton = new JButton("Edit Selected");

        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(editButton);

        add(buttonPanel, BorderLayout.SOUTH);

        // Add button logic (simplified)
        addButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Add veteran logic here.");
        });

        // Delete button logic
        deleteButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow >= 0) {
                store.getVeterans().remove(selectedRow);
                JOptionPane.showMessageDialog(this, "Veteran deleted successfully!");
                refreshTable();
            } else {
                JOptionPane.showMessageDialog(this, "Please select a veteran to delete.");
            }
        });

        // Edit button logic
        editButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow >= 0) {
                Veteran v = store.getVeterans().get(selectedRow);

                JTextField contactField = new JTextField(v.getContact());
                JTextField nextOfKinField = new JTextField(v.getNextOfKin());

                JPanel editPanel = new JPanel(new GridLayout(2,2,8,8));
                editPanel.add(new JLabel("Contact:"));
                editPanel.add(contactField);
                editPanel.add(new JLabel("Next of Kin:"));
                editPanel.add(nextOfKinField);

                int result = JOptionPane.showConfirmDialog(this, editPanel,
                        "Edit Veteran Profile", JOptionPane.OK_CANCEL_OPTION);

                if (result == JOptionPane.OK_OPTION) {
                    v.setContact(contactField.getText().trim());
                    v.setNextOfKin(nextOfKinField.getText().trim());
                    JOptionPane.showMessageDialog(this, "Veteran updated successfully!");
                    refreshTable();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select a veteran to edit.");
            }
        });
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        for (Veteran v : store.getVeterans()) {
            tableModel.addRow(new Object[]{
                    v.getFullName(),
                    v.getServiceNumber(),
                    v.getRank(),
                    v.getContact(),
                    v.getNextOfKin()
            });
        }
    }
}
