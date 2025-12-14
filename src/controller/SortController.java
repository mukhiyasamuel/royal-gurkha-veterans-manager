package controller;

import model.Veteran;
import java.util.List;

public class SortController {
    public void bubbleSortByRetirementYear(List<Veteran> veterans, boolean ascending) {
        int n = veterans.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                int y1 = veterans.get(j).getRetirementYear();
                int y2 = veterans.get(j + 1).getRetirementYear();
                boolean swap = ascending ? (y1 > y2) : (y1 < y2);
                if (swap) {
                    Veteran tmp = veterans.get(j);
                    veterans.set(j, veterans.get(j + 1));
                    veterans.set(j + 1, tmp);
                }
            }
        }
    }

    public void insertionSortByName(List<Veteran> veterans, boolean ascending) {
        for (int i = 1; i < veterans.size(); i++) {
            Veteran key = veterans.get(i);
            int j = i - 1;
            while (j >= 0 && compareNames(veterans.get(j), key, ascending) > 0) {
                veterans.set(j + 1, veterans.get(j));
                j--;
            }
            veterans.set(j + 1, key);
        }
    }

    private int compareNames(Veteran a, Veteran b, boolean ascending) {
        String na = a.getFullName() == null ? "" : a.getFullName();
        String nb = b.getFullName() == null ? "" : b.getFullName();
        int cmp = na.compareToIgnoreCase(nb);
        return ascending ? cmp : -cmp;
    }
}
