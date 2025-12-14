package view;

import controller.VeteranController;
import model.DataStore;
import model.WelfareCenter;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class WelfareCentersPanel extends JPanel {
    private DataStore store;
    private JTable table;
    private DefaultTableModel tableModel;

    public WelfareCentersPanel(DataStore store) {
        this.store = store;
        setLayout(new BorderLayout());

        tableModel = new DefaultTableModel(new Object[]{"Name","District","Province","Contact"},0);
        table = new JTable(tableModel);
        refreshTable();

        JButton addButton = new JButton("Add Center");
        JButton deleteButton = new JButton("Delete Selected");

        JPanel buttons = new JPanel();
        buttons.add(addButton); buttons.add(deleteButton);

        add(new JScrollPane(table), BorderLayout.CENTER);
        add(buttons, BorderLayout.SOUTH);

        addButton.addActionListener(e -> {
            JTextField nameField = new JTextField();
            JTextField districtField = new JTextField();
            JTextField provinceField = new JTextField();
            JTextField contactField = new JTextField();

            JPanel form = new JPanel(new GridLayout(4,2));
            form.add(new JLabel("Name")); form.add(nameField);
            form.add(new JLabel("District")); form.add(districtField);
            form.add(new JLabel("Province")); form.add(provinceField);
            form.add(new JLabel("Contact")); form.add(contactField);

            int result = JOptionPane.showConfirmDialog(this, form, "Add Welfare Center", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                WelfareCenter wc = new WelfareCenter();
                wc.setName(nameField.getText());
                wc.setDistrict(districtField.getText());
                wc.setProvince(provinceField.getText());
                wc.setContact(contactField.getText());
                store.getCenters().add(wc);
                refreshTable();
            }
        });

        deleteButton.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                store.getCenters().remove(row);
                refreshTable();
            }
        });
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        for (WelfareCenter wc : store.getCenters()) {
            tableModel.addRow(new Object[]{wc.getName(), wc.getDistrict(), wc.getProvince(), wc.getContact()});
        }
    }
}
