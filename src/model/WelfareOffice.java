package model;

/**
 * Model class representing a Welfare Office location
 * Contains office information for veteran support services
 */
public class WelfareOffice {
    private String officeId;
    private String officeName;
    private String city;
    private String fullAddress;
    private String contactNumber;
    private String email;
    private String servicesOffered;
    private String operatingHours;
    
    // Constructor
    public WelfareOffice(String officeId, String officeName, String city, 
                        String fullAddress, String contactNumber, String email, 
                        String servicesOffered, String operatingHours) {
        this.officeId = officeId;
        this.officeName = officeName;
        this.city = city;
        this.fullAddress = fullAddress;
        this.contactNumber = contactNumber;
        this.email = email;
        this.servicesOffered = servicesOffered;
        this.operatingHours = operatingHours;
    }
    
    // Getters and Setters
    public String getOfficeId() {
        return officeId;
    }

    public void setOfficeId(String officeId) {
        this.officeId = officeId;
    }

    public String getOfficeName() {
        return officeName;
    }

    public void setOfficeName(String officeName) {
        this.officeName = officeName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getFullAddress() {
        return fullAddress;
    }

    public void setFullAddress(String fullAddress) {
        this.fullAddress = fullAddress;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getServicesOffered() {
        return servicesOffered;
    }

    public void setServicesOffered(String servicesOffered) {
        this.servicesOffered = servicesOffered;
    }

    public String getOperatingHours() {
        return operatingHours;
    }

    public void setOperatingHours(String operatingHours) {
        this.operatingHours = operatingHours;
    }
    
    @Override
    public String toString() {
        return "WelfareOffice{" +
                "officeName='" + officeName + '\'' +
                ", city='" + city + '\'' +
                ", contactNumber='" + contactNumber + '\'' +
                '}';
    }
}
