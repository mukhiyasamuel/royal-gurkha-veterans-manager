package view;

import controller.VeteranController;
import model.DataStore;

import javax.swing.*;

public class TestVeteranList {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            DataStore store = new DataStore();
            VeteranController controller = new VeteranController(store);
            JFrame frame = new JFrame("Veteran List Test");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(600,400);
            frame.add(new VeteranListPanel(store, controller, frame));
            frame.setVisible(true);
        });
    }
}

