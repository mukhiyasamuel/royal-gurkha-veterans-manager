package model;

import java.time.LocalDate;
import java.time.Period;

/**
 * Model class representing a Gurkha Veteran
 * Contains all veteran information including service details and pension scheme
 */
public class Veteran {
    private String serviceNumber;
    private String fullName;
    private String rank;
    private LocalDate dateOfBirth;
    private LocalDate serviceStartDate;
    private LocalDate serviceEndDate;
    private String pensionScheme; // GPS or AFPS
    private double monthlyPension;
    private String contactNumber;
    private String address;
    private String regiment;
    private String status; // Active, Retired, Deceased
    
    // Constructor
    public Veteran(String serviceNumber, String fullName, String rank, 
                   LocalDate dateOfBirth, LocalDate serviceStartDate, 
                   LocalDate serviceEndDate, String pensionScheme, 
                   double monthlyPension, String contactNumber, 
                   String address, String regiment, String status) {
        this.serviceNumber = serviceNumber;
        this.fullName = fullName;
        this.rank = rank;
        this.dateOfBirth = dateOfBirth;
        this.serviceStartDate = serviceStartDate;
        this.serviceEndDate = serviceEndDate;
        this.pensionScheme = pensionScheme;
        this.monthlyPension = monthlyPension;
        this.contactNumber = contactNumber;
        this.address = address;
        this.regiment = regiment;
        this.status = status;
    }
    
    // Calculate total service years
    public int getServiceYears() {
        if (serviceStartDate == null || serviceEndDate == null) {
            return 0;
        }
        return Period.between(serviceStartDate, serviceEndDate).getYears();
    }
    
    // Calculate age
    public int getAge() {
        if (dateOfBirth == null) {
            return 0;
        }
        return Period.between(dateOfBirth, LocalDate.now()).getYears();
    }
    
    // Getters and Setters
    public String getServiceNumber() {
        return serviceNumber;
    }

    public void setServiceNumber(String serviceNumber) {
        this.serviceNumber = serviceNumber;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public LocalDate getServiceStartDate() {
        return serviceStartDate;
    }

    public void setServiceStartDate(LocalDate serviceStartDate) {
        this.serviceStartDate = serviceStartDate;
    }

    public LocalDate getServiceEndDate() {
        return serviceEndDate;
    }

    public void setServiceEndDate(LocalDate serviceEndDate) {
        this.serviceEndDate = serviceEndDate;
    }

    public String getPensionScheme() {
        return pensionScheme;
    }

    public void setPensionScheme(String pensionScheme) {
        this.pensionScheme = pensionScheme;
    }

    public double getMonthlyPension() {
        return monthlyPension;
    }

    public void setMonthlyPension(double monthlyPension) {
        this.monthlyPension = monthlyPension;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRegiment() {
        return regiment;
    }

    public void setRegiment(String regiment) {
        this.regiment = regiment;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    @Override
    public String toString() {
        return "Veteran{" +
                "serviceNumber='" + serviceNumber + '\'' +
                ", fullName='" + fullName + '\'' +
                ", rank='" + rank + '\'' +
                ", pensionScheme='" + pensionScheme + '\'' +
                '}';
    }
}
