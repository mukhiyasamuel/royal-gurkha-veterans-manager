package view;

import controller.VeteranController;
import model.DataStore;
import model.Veteran;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

public class VeteranListPanel extends JPanel {
    private DataStore store;
    private VeteranController veteranController;
    private DefaultTableModel tableModel;
    private JTable table;

    public VeteranListPanel(DataStore store, VeteranController veteranController, JFrame parent) {
        this.store = store;
        this.veteranController = veteranController;
        setLayout(new BorderLayout());

        // Search bar
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JTextField searchField = new JTextField(20);
        JButton searchButton = new JButton("Search");
        JButton resetButton = new JButton("Reset");
        searchPanel.add(new JLabel("Search:"));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        searchPanel.add(resetButton);

        add(searchPanel, BorderLayout.NORTH);

        // Table
        tableModel = new DefaultTableModel(new Object[]{"Name","Service Number","Rank","District","Contact","Next of Kin"},0);
        table = new JTable(tableModel);
        refreshTable(store.getVeterans());

        add(new JScrollPane(table), BorderLayout.CENTER);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton addButton = new JButton("Add Veteran");
        JButton deleteButton = new JButton("Delete Selected");
        JButton editButton = new JButton("Edit Selected");

        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(editButton);

        add(buttonPanel, BorderLayout.SOUTH);

        // Search logic
        searchButton.addActionListener(e -> {
            String keyword = searchField.getText().trim().toLowerCase();
            if (!keyword.isEmpty()) {
                List<Veteran> filtered = store.getVeterans().stream()
                        .filter(v -> v.getFullName().toLowerCase().contains(keyword)
                                || v.getServiceNumber().toLowerCase().contains(keyword)
                                || v.getRank().toLowerCase().contains(keyword)
                                || v.getDistrict().toLowerCase().contains(keyword))
                        .collect(Collectors.toList());
                refreshTable(filtered);
            }
        });

        resetButton.addActionListener(e -> {
            searchField.setText("");
            refreshTable(store.getVeterans());
        });

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
                refreshTable(store.getVeterans());
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
                    refreshTable(store.getVeterans());
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select a veteran to edit.");
            }
        });
    }

    private void refreshTable(List<Veteran> veterans) {
        tableModel.setRowCount(0);
        for (Veteran v : veterans) {
            tableModel.addRow(new Object[]{
                    v.getFullName(),
                    v.getServiceNumber(),
                    v.getRank(),
                    v.getDistrict(),
                    v.getContact(),
                    v.getNextOfKin()
            });
        }
    }
}
