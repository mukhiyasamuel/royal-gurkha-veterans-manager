package view;

import model.DataStore;
import model.UserAccount;
import model.Veteran;

import javax.swing.*;
import java.awt.*;

public class UserLoginFrame extends JFrame {
    private DataStore store;

    public UserLoginFrame(DataStore store) {
        this.store = store;

        setTitle("Veteran Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 200);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(3,2,8,8));
        panel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        JButton loginButton = new JButton("Login");

        panel.add(new JLabel("Username:"));
        panel.add(usernameField);
        panel.add(new JLabel("Password:"));
        panel.add(passwordField);
        panel.add(new JLabel()); // spacer
        panel.add(loginButton);

        add(panel);

        loginButton.addActionListener(e -> {
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword());

            UserAccount account = store.getUsers().stream()
                .filter(u -> u.getUsername().equalsIgnoreCase(username)
                          && u.getPassword().equals(password))
                .findFirst()
                .orElse(null);

            if (account != null) {
                Veteran found = store.getVeterans().stream()
                    .filter(v -> v.getServiceNumber().equalsIgnoreCase(account.getVeteranServiceNumber()))
                    .findFirst()
                    .orElse(null);

                if (found != null) {
                    JOptionPane.showMessageDialog(this, "Login successful! Welcome " + found.getFullName());
                    new UserDashboardFrame(found, store).setVisible(true);
                    dispose();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Invalid username or password.");
            }
        });
    }

    public static void main(String[] args) {
        DataStore store = new DataStore();

        // Seed sample veterans
        Veteran v1 = new Veteran();
        v1.setFullName("Hari Thapa");
        v1.setServiceNumber("RGR001");
        v1.setRank("Sergeant");
        v1.setEnlistYear(1985);
        v1.setRetirementYear(2005);
        store.getVeterans().add(v1);

        Veteran v2 = new Veteran();
        v2.setFullName("Ram Gurung");
        v2.setServiceNumber("RGR002");
        v2.setRank("Captain");
        v2.setEnlistYear(1990);
        v2.setRetirementYear(2010);
        store.getVeterans().add(v2);

        Veteran v3 = new Veteran();
        v3.setFullName("Krishna Rai");
        v3.setServiceNumber("RGR003");
        v3.setRank("Major");
        v3.setEnlistYear(1988);
        v3.setRetirementYear(2012);
        store.getVeterans().add(v3);

        // Seed sample accounts
        store.getUsers().add(new UserAccount("hari", "hari123", "RGR001"));
        store.getUsers().add(new UserAccount("ram", "ram123", "RGR002"));
        store.getUsers().add(new UserAccount("krishna", "krishna123", "RGR003"));

        SwingUtilities.invokeLater(() -> {
            UserLoginFrame frame = new UserLoginFrame(store);
            frame.setVisible(true);
        });
    }
}
