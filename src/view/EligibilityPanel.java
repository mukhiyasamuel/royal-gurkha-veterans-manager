package view;

import controller.EligibilityController;
import model.EligibilityRule;
import model.Veteran;

import javax.swing.*;
import java.awt.*;

public class EligibilityPanel extends JPanel {
    private JTextField enlistYearField, retirementYearField;
    private JButton checkButton;
    private JLabel resultLabel;

    public EligibilityPanel() {
        setLayout(new GridLayout(4,2,8,8));

        enlistYearField = new JTextField();
        retirementYearField = new JTextField();
        checkButton = new JButton("Check Eligibility");
        resultLabel = new JLabel(" ", SwingConstants.CENTER);

        add(new JLabel("Enlist Year:")); add(enlistYearField);
        add(new JLabel("Retirement Year:")); add(retirementYearField);
        add(checkButton); add(resultLabel);

        EligibilityRule rule = new EligibilityRule();
        EligibilityController controller = new EligibilityController(rule);

        checkButton.addActionListener(e -> {
            try {
                int enlist = Integer.parseInt(enlistYearField.getText());
                int retire = Integer.parseInt(retirementYearField.getText());
                Veteran v = new Veteran();
                v.setEnlistYear(enlist);
                v.setRetirementYear(retire);
                String result = controller.evaluateSettlement(v);
                resultLabel.setText(result);
            } catch (NumberFormatException ex) {
                resultLabel.setText("Years must be numbers.");
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Eligibility Checker");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(400,200);
            frame.add(new EligibilityPanel());
            frame.setVisible(true);
        });
    }
}

