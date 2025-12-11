package view;

import controller.VeteranController;
import model.ValidationResult;
import model.Veteran;

import javax.swing.*;
import java.awt.*;

public class VeteranFormDialog extends JDialog {
    private JTextField fullNameField, serviceNumberField, rankField, enlistYearField, retirementYearField, districtField, contactField, nextOfKinField;
    private JButton saveButton, cancelButton;
    private JLabel messageLabel;
    private boolean saved = false;

    public VeteranFormDialog(JFrame parent, VeteranController controller) {
        super(parent, "Add/Edit Veteran", true);
        setSize(400, 400);
        setLocationRelativeTo(parent);

        fullNameField = new JTextField();
        serviceNumberField = new JTextField();
        rankField = new JTextField();
        enlistYearField = new JTextField();
        retirementYearField = new JTextField();
        districtField = new JTextField();
        contactField = new JTextField();
        nextOfKinField = new JTextField();

        saveButton = new JButton("Save");
        cancelButton = new JButton("Cancel");
        messageLabel = new JLabel(" ", SwingConstants.CENTER);

        JPanel form = new JPanel(new GridLayout(9,2,5,5));
        form.add(new JLabel("Full Name")); form.add(fullNameField);
        form.add(new JLabel("Service Number")); form.add(serviceNumberField);
        form.add(new JLabel("Rank")); form.add(rankField);
        form.add(new JLabel("Enlist Year")); form.add(enlistYearField);
        form.add(new JLabel("Retirement Year")); form.add(retirementYearField);
        form.add(new JLabel("District")); form.add(districtField);
        form.add(new JLabel("Contact")); form.add(contactField);
        form.add(new JLabel("Next of Kin")); form.add(nextOfKinField);

        JPanel buttons = new JPanel();
        buttons.add(saveButton); buttons.add(cancelButton);

        setLayout(new BorderLayout());
        add(form, BorderLayout.CENTER);
        add(buttons, BorderLayout.SOUTH);
        add(messageLabel, BorderLayout.NORTH);

        saveButton.addActionListener(e -> {
            Veteran v = new Veteran();
            v.setFullName(fullNameField.getText());
            v.setServiceNumber(serviceNumberField.getText());
            v.setRank(rankField.getText());
            try {
                v.setEnlistYear(Integer.parseInt(enlistYearField.getText()));
                v.setRetirementYear(Integer.parseInt(retirementYearField.getText()));
            } catch (NumberFormatException ex) {
                messageLabel.setText("Year fields must be numbers.");
                return;
            }
            v.setDistrict(districtField.getText());
            v.setContact(contactField.getText());
            v.setNextOfKin(nextOfKinField.getText());

            ValidationResult vr = controller.addVeteran(v);
            if (vr.isValid()) {
                saved = true;
                dispose();
            } else {
                messageLabel.setText(String.join(", ", vr.getMessages()));
            }
        });

        cancelButton.addActionListener(e -> dispose());
    }

    public boolean isSaved() { return saved; }
}
