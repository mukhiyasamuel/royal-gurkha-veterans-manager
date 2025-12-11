/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

/**
 *
 * @author princeysunar
 */
// controller/ValidationUtil.java
public class ValidationUtil {
    public static ValidationResult validateVeteran(Veteran v, int currentYear) {
        ValidationResult vr = new ValidationResult();
        if (v.getFullName() == null || v.getFullName().trim().isEmpty())
            vr.addError("Full name is required.");
        if (v.getServiceNumber() == null || v.getServiceNumber().trim().isEmpty())
            vr.addError("Service number is required.");
        if (v.getEnlistYear() < 1947 || v.getEnlistYear() > currentYear)
            vr.addError("Enlist year must be between 1947 and " + currentYear + ".");
        if (v.getRetirementYear() < v.getEnlistYear() || v.getRetirementYear() > currentYear)
            vr.addError("Retirement year must be >= enlist year and <= " + currentYear + ".");
        if (v.getDistrict() == null || v.getDistrict().trim().isEmpty())
            vr.addError("District is required.");
        return vr;
    }
}

