package view;

import controller.VeteranController;
import model.DataStore;
import model.Veteran;

import javax.swing.*;
import java.awt.*;

public class AdminDashboardFrame extends JFrame {
    private DataStore store;
    private VeteranController veteranController;

    public AdminDashboardFrame(DataStore store) {
        this.store = store;
        this.veteranController = new VeteranController(store);

        setTitle("RGR Support System - Admin Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);
        
        // Stats panel
        JPanel statsPanel = new JPanel(new GridLayout(2,2,8,8));
        statsPanel.add(new JLabel("Total veterans: " + store.getVeterans().size()));
        long post1997Count = store.getVeterans().stream().filter(Veteran::isPost1997).count();
        statsPanel.add(new JLabel("Post-1997 veterans: " + post1997Count));
        statsPanel.add(new JLabel("Welfare centers: " + store.getCenters().size()));
        statsPanel.add(new JLabel("Honors: " + store.getHonors().size()));

        add(statsPanel, BorderLayout.NORTH);

        // Tabbed pane
        JTabbedPane tabs = new JTabbedPane();

        // Veterans tab
        tabs.addTab("Veterans", new VeteranListPanel(store, veteranController, this));

        // Eligibility tab
        tabs.addTab("Eligibility", new EligibilityPanel());

        // Welfare Centers tab (placeholder for now)
        tabs.addTab("Welfare Centers", new WelfareCentersPanel(store));


        // Honors tab (placeholder for now)
       tabs.addTab("Honors", new HonorsPanel(store));


        // Audit Logs tab (placeholder for now)
        tabs.addTab("Audit Logs", new AuditTrailPanel(store));
        
        // User Accounts tab
        tabs.addTab("User Accounts", new UserAccountsPanel(store));



        add(tabs, BorderLayout.CENTER);
    }

   public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> {
        DataStore store = new DataStore();

        // --- Seed sample veterans ---
        model.Veteran v1 = new model.Veteran();
        v1.setFullName("Hari Thapa");
        v1.setServiceNumber("RGR001");
        v1.setRank("Sergeant");
        v1.setEnlistYear(1985);
        v1.setRetirementYear(2005);
        v1.setDistrict("Pokhara");
        v1.setContact("9800000001");
        v1.setNextOfKin("Sita Thapa");
        store.getVeterans().add(v1);
        store.addAuditLog("Added veteran Hari Thapa");

        model.Veteran v2 = new model.Veteran();
        v2.setFullName("Ram Gurung");
        v2.setServiceNumber("RGR002");
        v2.setRank("Captain");
        v2.setEnlistYear(1990);
        v2.setRetirementYear(2010);
        v2.setDistrict("Lamjung");
        v2.setContact("9800000002");
        v2.setNextOfKin("Maya Gurung");
        store.getVeterans().add(v2);
        store.addAuditLog("Added veteran Ram Gurung");

        model.Veteran v3 = new model.Veteran();
        v3.setFullName("Krishna Rai");
        v3.setServiceNumber("RGR003");
        v3.setRank("Major");
        v3.setEnlistYear(1988);
        v3.setRetirementYear(2012);
        v3.setDistrict("Ilam");
        v3.setContact("9800000003");
        v3.setNextOfKin("Laxmi Rai");
        store.getVeterans().add(v3);
        store.addAuditLog("Added veteran Krishna Rai");
        
        // --- Seed sample welfare centers ---
        model.WelfareCenter wc1 = new model.WelfareCenter();
        wc1.setName("Pokhara Welfare Center");
        wc1.setDistrict("Kaski");
        wc1.setProvince("Gandaki");
        wc1.setContact("061-123456");
        store.getCenters().add(wc1);
        store.addAuditLog("Added welfare center Pokhara Welfare Center");

        model.WelfareCenter wc2 = new model.WelfareCenter();
        wc2.setName("Lamjung Welfare Center");
        wc2.setDistrict("Lamjung");
        wc2.setProvince("Gandaki");
        wc2.setContact("066-987654");
        store.getCenters().add(wc2);
        store.addAuditLog("Added welfare center Lamjung Welfare Center");
        
                // --- Seed sample honors ---
        model.Honor h1 = new model.Honor();
        h1.setTitle("Victoria Cross");
        h1.setAwardYear(1944);
        h1.setType(model.HonorType.VC);
        h1.setDescription("Awarded for extraordinary bravery in WWII.");
        store.getHonors().add(h1);
        store.addAuditLog("Added honor Victoria Cross");

        model.Honor h2 = new model.Honor();
        h2.setTitle("Military Cross");
        h2.setAwardYear(1965);
        h2.setType(model.HonorType.MC);
        h2.setDescription("Awarded for distinguished service in Malaya.");
        store.getHonors().add(h2);
        store.addAuditLog("Added honor Military Cross");


        // --- Launch dashboard ---
        AdminDashboardFrame frame = new AdminDashboardFrame(store);
        frame.setVisible(true);
    });
}

}
