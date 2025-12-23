package view;

import model.DataStore;
import model.WelfareCenter;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class WelfareCentersPanel extends JPanel {
    private DataStore store;
    private DefaultTableModel tableModel;
    private JTable table;

    public WelfareCentersPanel(DataStore store) {
        this.store = store;
        setLayout(new BorderLayout());

        // Table
        tableModel = new DefaultTableModel(new Object[]{"Name","District","Province","Contact"},0);
        table = new JTable(tableModel);
        refreshTable();

        add(new JScrollPane(table), BorderLayout.CENTER);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton addButton = new JButton("Add Center");
        JButton deleteButton = new JButton("Delete Selected");
        JButton editButton = new JButton("Edit Selected");

        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(editButton);

        add(buttonPanel, BorderLayout.SOUTH);

        // Add button logic
        addButton.addActionListener(e -> {
            JTextField nameField = new JTextField();
            JTextField districtField = new JTextField();
            JTextField provinceField = new JTextField();
            JTextField contactField = new JTextField();

            JPanel form = new JPanel(new GridLayout(4,2,8,8));
            form.add(new JLabel("Name:")); form.add(nameField);
            form.add(new JLabel("District:")); form.add(districtField);
            form.add(new JLabel("Province:")); form.add(provinceField);
            form.add(new JLabel("Contact:")); form.add(contactField);

            int result = JOptionPane.showConfirmDialog(this, form, "Add Welfare Center", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                WelfareCenter wc = new WelfareCenter();
                wc.setName(nameField.getText().trim());
                wc.setDistrict(districtField.getText().trim());
                wc.setProvince(provinceField.getText().trim());
                wc.setContact(contactField.getText().trim());
                store.getCenters().add(wc);
                JOptionPane.showMessageDialog(this, "Center added successfully!");
                refreshTable();
            }
        });

        // Delete button logic
        deleteButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow >= 0) {
                store.getCenters().remove(selectedRow);
                JOptionPane.showMessageDialog(this, "Center deleted successfully!");
                refreshTable();
            } else {
                JOptionPane.showMessageDialog(this, "Please select a center to delete.");
            }
        });

        // Edit button logic
        editButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow >= 0) {
                WelfareCenter wc = store.getCenters().get(selectedRow);

                JTextField nameField = new JTextField(wc.getName());
                JTextField districtField = new JTextField(wc.getDistrict());
                JTextField provinceField = new JTextField(wc.getProvince());
                JTextField contactField = new JTextField(wc.getContact());

                JPanel form = new JPanel(new GridLayout(4,2,8,8));
                form.add(new JLabel("Name:")); form.add(nameField);
                form.add(new JLabel("District:")); form.add(districtField);
                form.add(new JLabel("Province:")); form.add(provinceField);
                form.add(new JLabel("Contact:")); form.add(contactField);

                int result = JOptionPane.showConfirmDialog(this, form, "Edit Welfare Center", JOptionPane.OK_CANCEL_OPTION);
                if (result == JOptionPane.OK_OPTION) {
                    wc.setName(nameField.getText().trim());
                    wc.setDistrict(districtField.getText().trim());
                    wc.setProvince(provinceField.getText().trim());
                    wc.setContact(contactField.getText().trim());
                    JOptionPane.showMessageDialog(this, "Center updated successfully!");
                    refreshTable();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select a center to edit.");
            }
        });
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        for(WelfareCenter wc : store.getCenters()) {
            tableModel.addRow(new Object[]{wc.getName(), wc.getDistrict(), wc.getProvince(), wc.getContact()});
        }
    }
}
