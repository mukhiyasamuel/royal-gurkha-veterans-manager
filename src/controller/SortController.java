/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

/**
 *
 * @author princeysunar
 */
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
}
