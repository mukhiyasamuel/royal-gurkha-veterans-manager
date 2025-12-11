package controller;

import model.DataStore;
import model.ValidationResult;
import model.Veteran;

public class VeteranController {
    private final DataStore store;

    public VeteranController(DataStore store) {
        this.store = store;
    }

    public ValidationResult addVeteran(Veteran v) {
        ValidationResult vr = ValidationUtil.validateVeteran(v, java.time.Year.now().getValue());
        if (!vr.isValid()) return vr;
        v.setPost1997(v.getRetirementYear() >= 1997);
        store.registerVeteran(v, vr);
        return vr;
    }

    public boolean undoLast() {
        Runnable r = store.getUndoStack().poll();
        if (r == null) return false;
        r.run();
        return true;
    }
}
