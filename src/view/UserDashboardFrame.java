package view;

import controller.EligibilityController;
import model.DataStore;
import model.EligibilityRule;
import model.Veteran;

import javax.swing.*;
import java.awt.*;

public class UserDashboardFrame extends JFrame {
    private Veteran loggedInVeteran;
    private DataStore store;

    public UserDashboardFrame(Veteran veteran, DataStore store) {
        this.loggedInVeteran = veteran;
        this.store = store;

        setTitle("Welcome, " + veteran.getFullName());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(6,1,8,8));
        panel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        panel.add(new JLabel("Service Number: " + veteran.getServiceNumber()));
        panel.add(new JLabel("Rank: " + veteran.getRank()));
        panel.add(new JLabel("Enlist Year: " + veteran.getEnlistYear()));
        panel.add(new JLabel("Retirement Year: " + veteran.getRetirementYear()));

        EligibilityController controller = new EligibilityController(new EligibilityRule());
        String eligibility = controller.evaluateSettlement(veteran);
        panel.add(new JLabel("Eligibility: " + eligibility));

        long honorsCount = store.getHonors().stream()
            .filter(h -> h.getAwardYear() <= veteran.getRetirementYear())
            .count();
        panel.add(new JLabel("Honors received: " + honorsCount));

        add(panel);
    }

    public static void main(String[] args) {
        DataStore store = new DataStore();

        // Simulate login with Krishna Rai
        Veteran v = new Veteran();
        v.setFullName("Krishna Rai");
        v.setServiceNumber("RGR003");
        v.setRank("Major");
        v.setEnlistYear(1988);
        v.setRetirementYear(2012);

        store.getVeterans().add(v);

        SwingUtilities.invokeLater(() -> {
            UserDashboardFrame frame = new UserDashboardFrame(v, store);
            frame.setVisible(true);
        });
    }
}
