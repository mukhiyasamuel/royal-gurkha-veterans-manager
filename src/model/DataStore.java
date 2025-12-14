
import java.util.*;

public class DataStore {
    private final List<Veteran> veterans = new ArrayList<>();
    private final List<WelfareCenter> centers = new ArrayList<>();
    private final List<Honor> honors = new ArrayList<>();
    private final List<UserAccount> users = new ArrayList<>();

    // Uniqueness registries
    private final Set<String> veteranIds = new HashSet<>();
    private final Set<String> serviceNumbers = new HashSet<>();

    // Recently added queue (last 5)
    private final Deque<Veteran> recentQueue = new ArrayDeque<>(5);

    // Undo stack
    private final Deque<Runnable> undoStack = new ArrayDeque<>();

    public List<Veteran> getVeterans() { return veterans; }
    public List<WelfareCenter> getCenters() { return centers; }
    public List<Honor> getHonors() { return honors; }
    public List<UserAccount> getUsers() { return users; }

    public Deque<Veteran> getRecentQueue() { return recentQueue; }
    public Deque<Runnable> getUndoStack() { return undoStack; }

    public boolean registerVeteran(Veteran v, ValidationResult vr) {
        // Strict ID uniqueness
        if (veteranIds.contains(v.getId())) {
            vr.addError("Internal error: duplicate veteran ID encountered.");
            return false;
        }
        // Service number uniqueness
        if (v.getServiceNumber() == null || v.getServiceNumber().isEmpty()) {
            vr.addError("Service number is required.");
            return false;
        }
        if (serviceNumbers.contains(v.getServiceNumber())) {
            vr.addError("Service number must be unique.");
            return false;
        }
        veterans.add(v);
        veteranIds.add(v.getId());
        serviceNumbers.add(v.getServiceNumber());
        recentQueue.addFirst(v);
        while (recentQueue.size() > 5) recentQueue.removeLast();
        undoStack.push(() -> {
            veterans.remove(v);
            veteranIds.remove(v.getId());
            serviceNumbers.remove(v.getServiceNumber());
        });
        return true;
    }
    
    // Audit log entries
private final java.util.List<String> auditLog = new java.util.ArrayList<>();

public java.util.List<String> getAuditLog() { return auditLog; }

public void addAuditLog(String entry) {
    auditLog.add(entry);
}

}

