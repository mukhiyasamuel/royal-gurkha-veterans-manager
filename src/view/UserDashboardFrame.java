package view;

import controller.EligibilityController;
import model.DataStore;
import model.EligibilityRule;
import model.Veteran;
import model.Honor;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

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

        JPanel panel = new JPanel(new BorderLayout(10,10));
        panel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        // Info tab
        JPanel infoPanel = new JPanel(new GridLayout(6,1,8,8));
        infoPanel.add(new JLabel("Service Number: " + veteran.getServiceNumber()));
        infoPanel.add(new JLabel("Rank: " + veteran.getRank()));
        infoPanel.add(new JLabel("Enlist Year: " + veteran.getEnlistYear()));
        infoPanel.add(new JLabel("Retirement Year: " + veteran.getRetirementYear()));

        EligibilityController controller = new EligibilityController(new EligibilityRule());
        String eligibility = controller.evaluateSettlement(veteran);
        infoPanel.add(new JLabel("Eligibility: " + eligibility));

        // Honors tab
        List<Honor> honors = store.getHonors().stream()
            .filter(h -> h.getAwardYear() <= veteran.getRetirementYear())
            .collect(Collectors.toList());

        DefaultListModel<String> honorsModel = new DefaultListModel<>();
        for (Honor h : honors) {
            honorsModel.addElement(h.getTitle() + " (" + h.getAwardYear() + ")");
        }
        JList<String> honorsList = new JList<>(honorsModel);

        // Profile tab
        UserProfilePanel profilePanel = new UserProfilePanel(veteran);

        // Tabs
        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Info", infoPanel);
        tabs.addTab("Honors", new JScrollPane(honorsList));
        tabs.addTab("Profile", profilePanel);

        panel.add(tabs, BorderLayout.CENTER);

        // Buttons
        JPanel buttonPanel = new JPanel();
        JButton logoutButton = new JButton("Logout");
        buttonPanel.add(logoutButton);

        logoutButton.addActionListener(e -> {
            new UserLoginFrame(store).setVisible(true);
            dispose();
        });

        panel.add(buttonPanel, BorderLayout.SOUTH);

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
        v.setContact("9800000003");
        v.setNextOfKin("Laxmi Rai");

        store.getVeterans().add(v);

        SwingUtilities.invokeLater(() -> {
            UserDashboardFrame frame = new UserDashboardFrame(v, store);
            frame.setVisible(true);
        });
    }
}
