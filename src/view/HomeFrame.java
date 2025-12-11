package view;

import javax.swing.*;
import java.awt.*;

public class HomeFrame extends JFrame {
    public HomeFrame() {
        setTitle("RGR Support System - Home");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);

        JPanel statsPanel = new JPanel(new GridLayout(2,2,8,8));
        statsPanel.add(new JLabel("Total veterans:"));
        statsPanel.add(new JLabel("Post-1997:"));
        statsPanel.add(new JLabel("Pending appeals:"));
        statsPanel.add(new JLabel("Welfare centers:"));

        JPanel recentPanel = new JPanel();
        recentPanel.add(new JLabel("Recently added (Queue):"));

        add(statsPanel, BorderLayout.WEST);
        add(recentPanel, BorderLayout.NORTH);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new HomeFrame().setVisible(true));
    }
}
