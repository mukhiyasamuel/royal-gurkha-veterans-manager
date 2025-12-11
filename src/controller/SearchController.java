/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

/**
 *
 * @author princeysunar
 */
public class SearchController {
    public Veteran binarySearchByServiceNumber(List<Veteran> sortedByServiceNumber, String target) {
        int lo = 0, hi = sortedByServiceNumber.size() - 1;
        while (lo <= hi) {
            int mid = (lo + hi) / 2;
            int cmp = sortedByServiceNumber.get(mid).getServiceNumber().compareTo(target);
            if (cmp == 0) return sortedByServiceNumber.get(mid);
            if (cmp < 0) lo = mid + 1;
            else hi = mid - 1;
        }
        return null;
    }

    public List<Veteran> linearSearchByName(List<Veteran> veterans, String partial) {
        List<Veteran> results = new ArrayList<>();
        String needle = partial.toLowerCase();
        for (Veteran v : veterans) {
            if (v.getFullName().toLowerCase().contains(needle)) {
                results.add(v);
            }
        }
        return results;
    }
}
