package view;

import model.DataStore;

import javax.swing.*;
import java.awt.*;

public class AuditTrailPanel extends JPanel {
    private DataStore store;
    private DefaultListModel<String> listModel;
    private JList<String> logList;
    private JButton refreshButton;

    public AuditTrailPanel(DataStore store) {
        this.store = store;
        setLayout(new BorderLayout());

        listModel = new DefaultListModel<>();
        logList = new JList<>(listModel);
        refreshButton = new JButton("Refresh Logs");

        add(new JScrollPane(logList), BorderLayout.CENTER);
        add(refreshButton, BorderLayout.SOUTH);

        refreshButton.addActionListener(e -> refreshLogs());
        refreshLogs();
    }

    private void refreshLogs() {
        listModel.clear();
        for (String entry : store.getAuditLog()) {
            listModel.addElement(entry);
        }
    }
}
