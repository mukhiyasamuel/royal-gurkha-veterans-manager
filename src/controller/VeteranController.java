/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

/**
 *
 * @author princeysunar
 */
public class VeteranController {
  public class VeteranController {
    private final List<Veteran> veterans;           // ArrayList
    private final Deque<Veteran> recentQueue;       // Queue (ArrayDeque)
    private final Deque<Runnable> undoStack;        // Stack (ArrayDeque as stack)

    public boolean addVeteran(Veteran v, List<String> errors) {
        if (!ValidationUtil.validateVeteran(v, errors)) return false;
        if (isDuplicateServiceNumber(v.getServiceNumber())) {
            errors.add("Service number must be unique.");
            return false;
        }
        veterans.add(v);
        recentQueue.addFirst(v);
        trimQueueTo5();
        undoStack.push(() -> veterans.remove(v));
        return true;
    }
    // edit, delete with undo pushes...
}  
}
