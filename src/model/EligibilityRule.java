/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author princeysunar
 */
// model/EligibilityRule.java
import java.time.LocalDate;

public class EligibilityRule {
    private LocalDate cutoffDate = LocalDate.of(1997, 7, 1);
    private int minYearsService = 4;

    public LocalDate getCutoffDate() { return cutoffDate; }
    public int getMinYearsService() { return minYearsService; }
    // setters if needed
}

