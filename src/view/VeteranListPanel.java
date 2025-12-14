package view;

import controller.VeteranController;
import model.DataStore;
import model.Veteran;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class VeteranListPanel extends JPanel {
    private DataStore store;
    private VeteranController controller;
    private JTable table;
    private DefaultTableModel tableModel;

    public VeteranListPanel(DataStore store, VeteranController controller, JFrame parent) {
        this.store = store;
        this.controller = controller;

        setLayout(new BorderLayout());

        tableModel = new DefaultTableModel(new Object[]{"Name","Service No","Rank","Retirement Year"},0);
        table = new JTable(tableModel);
        refreshTable();

        JButton addButton = new JButton("Add Veteran");
        JButton deleteButton = new JButton("Delete Selected");
        JButton undoButton = new JButton("Undo Last");

        JPanel buttons = new JPanel();
        buttons.add(addButton); buttons.add(deleteButton); buttons.add(undoButton);

        add(new JScrollPane(table), BorderLayout.CENTER);
        add(buttons, BorderLayout.SOUTH);

        addButton.addActionListener(e -> {
            VeteranFormDialog dialog = new VeteranFormDialog(parent, controller);
            dialog.setVisible(true);
            if (dialog.isSaved()) refreshTable();
        });

        deleteButton.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                Veteran v = store.getVeterans().get(row);
                controller.deleteVeteran(v);
                refreshTable();
            }
        });

        undoButton.addActionListener(e -> {
            if (controller.undoLast()) refreshTable();
        });
        
        // Inside VeteranListPanel constructor, after undoButton:
JButton searchButton = new JButton("Search by Name");
JButton sortButton = new JButton("Sort by Retirement Year");

buttons.add(searchButton);
buttons.add(sortButton);

SearchController searchController = new SearchController();
SortController sortController = new SortController();

searchButton.addActionListener(e -> {
    String query = JOptionPane.showInputDialog(parent, "Enter name to search:");
    if (query != null && !query.trim().isEmpty()) {
        var results = searchController.linearSearchByName(store.getVeterans(), query);
        tableModel.setRowCount(0);
        for (Veteran v : results) {
            tableModel.addRow(new Object[]{v.getFullName(), v.getServiceNumber(), v.getRank(), v.getRetirementYear()});
        }
    }
});

sortButton.addActionListener(e -> {
    sortController.bubbleSortByRetirementYear(store.getVeterans(), true);
    refreshTable();
});

    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        for (Veteran v : store.getVeterans()) {
            tableModel.addRow(new Object[]{v.getFullName(), v.getServiceNumber(), v.getRank(), v.getRetirementYear()});
        }
    }
}

