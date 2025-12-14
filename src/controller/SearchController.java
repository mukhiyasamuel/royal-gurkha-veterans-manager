package controller;

import model.Veteran;
import java.util.ArrayList;
import java.util.List;

public class SearchController {
    public Veteran binarySearchByServiceNumber(List<Veteran> sorted, String target) {
        int lo = 0, hi = sorted.size() - 1;
        while (lo <= hi) {
            int mid = (lo + hi) / 2;
            int cmp = sorted.get(mid).getServiceNumber().compareTo(target);
            if (cmp == 0) return sorted.get(mid);
            if (cmp < 0) lo = mid + 1; else hi = mid - 1;
        }
        return null;
    }

    public List<Veteran> linearSearchByName(List<Veteran> veterans, String partial) {
        List<Veteran> results = new ArrayList<>();
        String needle = partial.toLowerCase();
        for (Veteran v : veterans) {
            if (v.getFullName() != null && v.getFullName().toLowerCase().contains(needle)) {
                results.add(v);
            }
        }
        return results;
    }
}
