package view;

import model.DataStore;
import model.Honor;
import model.HonorType;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class HonorsPanel extends JPanel {
    private DataStore store;
    private JTable table;
    private DefaultTableModel tableModel;

    public HonorsPanel(DataStore store) {
        this.store = store;
        setLayout(new BorderLayout());

        tableModel = new DefaultTableModel(new Object[]{"Title","Year","Type","Description"},0);
        table = new JTable(tableModel);
        refreshTable();

        JButton addButton = new JButton("Add Honor");
        JButton deleteButton = new JButton("Delete Selected");

        JPanel buttons = new JPanel();
        buttons.add(addButton); buttons.add(deleteButton);

        add(new JScrollPane(table), BorderLayout.CENTER);
        add(buttons, BorderLayout.SOUTH);

        addButton.addActionListener(e -> {
            JTextField titleField = new JTextField();
            JTextField yearField = new JTextField();
            JComboBox<HonorType> typeBox = new JComboBox<>(HonorType.values());
            JTextField descField = new JTextField();

            JPanel form = new JPanel(new GridLayout(4,2));
            form.add(new JLabel("Title")); form.add(titleField);
            form.add(new JLabel("Year")); form.add(yearField);
            form.add(new JLabel("Type")); form.add(typeBox);
            form.add(new JLabel("Description")); form.add(descField);

            int result = JOptionPane.showConfirmDialog(this, form, "Add Honor", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                try {
                    Honor h = new Honor();
                    h.setTitle(titleField.getText());
                    h.setAwardYear(Integer.parseInt(yearField.getText()));
                    h.setType((HonorType) typeBox.getSelectedItem());
                    h.setDescription(descField.getText());
                    store.getHonors().add(h);
                    refreshTable();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Year must be a number.");
                }
            }
        });

        deleteButton.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                store.getHonors().remove(row);
                refreshTable();
            }
        });
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        for (Honor h : store.getHonors()) {
            tableModel.addRow(new Object[]{h.getTitle(), h.getAwardYear(), h.getType(), h.getDescription()});
        }
    }
}
