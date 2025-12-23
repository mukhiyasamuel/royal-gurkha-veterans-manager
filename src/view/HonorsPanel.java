package view;

import model.DataStore;
import model.Honor;
import model.HonorType;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class HonorsPanel extends JPanel {
    private DataStore store;
    private DefaultTableModel tableModel;
    private JTable table;

    public HonorsPanel(DataStore store) {
        this.store = store;
        setLayout(new BorderLayout());

        // Table
        tableModel = new DefaultTableModel(new Object[]{"Title","Year","Type","Description"},0);
        table = new JTable(tableModel);
        refreshTable();

        add(new JScrollPane(table), BorderLayout.CENTER);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton addButton = new JButton("Add Honor");
        JButton deleteButton = new JButton("Delete Selected");
        JButton editButton = new JButton("Edit Selected");

        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(editButton);

        add(buttonPanel, BorderLayout.SOUTH);

        // Add button logic
        addButton.addActionListener(e -> {
            JTextField titleField = new JTextField();
            JTextField yearField = new JTextField();
            JComboBox<HonorType> typeBox = new JComboBox<>(HonorType.values());
            JTextField descriptionField = new JTextField();

            JPanel form = new JPanel(new GridLayout(4,2,8,8));
            form.add(new JLabel("Title:")); form.add(titleField);
            form.add(new JLabel("Year:")); form.add(yearField);
            form.add(new JLabel("Type:")); form.add(typeBox);
            form.add(new JLabel("Description:")); form.add(descriptionField);

            int result = JOptionPane.showConfirmDialog(this, form, "Add Honor", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                Honor h = new Honor();
                h.setTitle(titleField.getText().trim());
                h.setAwardYear(Integer.parseInt(yearField.getText().trim()));
                h.setType((HonorType) typeBox.getSelectedItem());
                h.setDescription(descriptionField.getText().trim());
                store.getHonors().add(h);
                JOptionPane.showMessageDialog(this, "Honor added successfully!");
                refreshTable();
            }
        });

        // Delete button logic
        deleteButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow >= 0) {
                store.getHonors().remove(selectedRow);
                JOptionPane.showMessageDialog(this, "Honor deleted successfully!");
                refreshTable();
            } else {
                JOptionPane.showMessageDialog(this, "Please select an honor to delete.");
            }
        });

        // Edit button logic
        editButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow >= 0) {
                Honor h = store.getHonors().get(selectedRow);

                JTextField titleField = new JTextField(h.getTitle());
                JTextField yearField = new JTextField(String.valueOf(h.getAwardYear()));
                JComboBox<HonorType> typeBox = new JComboBox<>(HonorType.values());
                typeBox.setSelectedItem(h.getType());
                JTextField descriptionField = new JTextField(h.getDescription());

                JPanel form = new JPanel(new GridLayout(4,2,8,8));
                form.add(new JLabel("Title:")); form.add(titleField);
                form.add(new JLabel("Year:")); form.add(yearField);
                form.add(new JLabel("Type:")); form.add(typeBox);
                form.add(new JLabel("Description:")); form.add(descriptionField);

                int result = JOptionPane.showConfirmDialog(this, form, "Edit Honor", JOptionPane.OK_CANCEL_OPTION);
                if (result == JOptionPane.OK_OPTION) {
                    h.setTitle(titleField.getText().trim());
                    h.setAwardYear(Integer.parseInt(yearField.getText().trim()));
                    h.setType((HonorType) typeBox.getSelectedItem());
                    h.setDescription(descriptionField.getText().trim());
                    JOptionPane.showMessageDialog(this, "Honor updated successfully!");
                    refreshTable();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select an honor to edit.");
            }
        });
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        for(Honor h : store.getHonors()) {
            tableModel.addRow(new Object[]{h.getTitle(), h.getAwardYear(), h.getType(), h.getDescription()});
        }
    }
}
