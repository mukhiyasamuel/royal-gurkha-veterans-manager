package model;

import java.util.ArrayList;
import java.util.List;

public class DataStore {
    private List<Veteran> veterans = new ArrayList<>();
    private List<UserAccount> users = new ArrayList<>();
    private List<WelfareCenter> centers = new ArrayList<>();
    private List<Honor> honors = new ArrayList<>();
    private List<String> auditLogs = new ArrayList<>();

    public List<Veteran> getVeterans() { return veterans; }
    public List<UserAccount> getUsers() { return users; }
    public List<WelfareCenter> getCenters() { return centers; }
    public List<Honor> getHonors() { return honors; }

    // --- Audit logs ---
    public void addAuditLog(String log) {
        auditLogs.add(log);
    }

    public List<String> getAuditLogs() {
        return auditLogs;
    }
}
