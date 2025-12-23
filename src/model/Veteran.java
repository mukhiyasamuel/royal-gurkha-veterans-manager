package model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Veteran {
    private final String id; // immutable UUID
    private String serviceNumber;
    private String fullName;
    private String rank;
    private int enlistYear;
    private int retirementYear;
    private boolean post1997;
    private PensionStatus pensionStatus;
    private String district;
    private String contact;
    private String nextOfKin;
    private List<Honor> honors = new ArrayList<>();

    public Veteran() {
        this.id = UUID.randomUUID().toString();
        this.pensionStatus = PensionStatus.PENDING;
    }

    public String getId() { return id; }
    public String getServiceNumber() { return serviceNumber; }
    public void setServiceNumber(String serviceNumber) { this.serviceNumber = serviceNumber; }
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public String getRank() { return rank; }
    public void setRank(String rank) { this.rank = rank; }
    public int getEnlistYear() { return enlistYear; }
    public void setEnlistYear(int enlistYear) { this.enlistYear = enlistYear; }
    public int getRetirementYear() { return retirementYear; }
    public void setRetirementYear(int retirementYear) { this.retirementYear = retirementYear; }
    public boolean isPost1997() {
    return retirementYear >= 1997;
}
    public void setPost1997(boolean post1997) { this.post1997 = post1997; }
    public PensionStatus getPensionStatus() { return pensionStatus; }
    public void setPensionStatus(PensionStatus pensionStatus) { this.pensionStatus = pensionStatus; }
    public String getDistrict() { return district; }
    public void setDistrict(String district) { this.district = district; }
    public String getContact() { return contact; }
    public void setContact(String contact) { this.contact = contact; }
    public String getNextOfKin() { return nextOfKin; }
    public void setNextOfKin(String nextOfKin) { this.nextOfKin = nextOfKin; }
    public List<Honor> getHonors() { return honors; }
}
