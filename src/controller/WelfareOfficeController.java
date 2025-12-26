package controller;

import model.WelfareOffice;
import java.util.*;

/**
 * Controller class for managing Welfare Office operations
 * Uses LinkedList for storing office locations
 */
public class WelfareOfficeController {
    // Data Structure - LinkedList for welfare offices
    private LinkedList<WelfareOffice> offices;
    
    // Constructor - Load dummy data
    public WelfareOfficeController() {
        offices = new LinkedList<>();
        loadDummyData();
    }
    
    /**
     * Load dummy welfare office data
     */
    private void loadDummyData() {
        offices.add(new WelfareOffice("WO001", "British Gurkhas Nepal (BGN) Headquarters",
                "Kathmandu", "Jawalakhel, Lalitpur, Kathmandu",
                "+977-1-5555555", "bgn.ktm@gurkha.org.np",
                "Pension Support, Medical Aid, Legal Assistance, Emergency Relief",
                "Sun-Fri: 10:00 AM - 5:00 PM"));
        
        offices.add(new WelfareOffice("WO002", "Gurkha Welfare Trust Pokhara Center",
                "Pokhara", "Lakeside-6, Pokhara, Gandaki Province",
                "+977-61-999999", "gwt.pokhara@gurkha.org.np",
                "Healthcare Services, Pension Queries, Community Support",
                "Sun-Fri: 9:00 AM - 4:30 PM"));
        
        offices.add(new WelfareOffice("WO003", "Dharan Gurkha Welfare Office",
                "Dharan", "Dharan-8, Sunsari District, Koshi Province",
                "+977-25-888888", "welfare.dharan@gurkha.org.np",
                "Medical Checkups, Pension Collection, Welfare Programs",
                "Sun-Fri: 9:30 AM - 4:00 PM"));
        
        offices.add(new WelfareOffice("WO004", "Butwal Regional Office",
                "Butwal", "Traffic Chowk, Butwal-5, Lumbini Province",
                "+977-71-777777", "office.butwal@gurkha.org.np",
                "Pension Distribution, Health Support, Legal Aid",
                "Sun-Fri: 10:00 AM - 4:30 PM"));
        
        offices.add(new WelfareOffice("WO005", "Gorkha District Welfare Center",
                "Gorkha", "Gorkha Bazar, Gorkha-2, Gandaki Province",
                "+977-64-666666", "center.gorkha@gurkha.org.np",
                "Community Services, Pension Assistance, Emergency Support",
                "Sun-Fri: 10:00 AM - 4:00 PM"));
    }
    
    /**
     * CREATE - Add new welfare office
     */
    public boolean addOffice(WelfareOffice office) {
        return offices.add(office);
    }
    
    /**
     * READ - Get all offices
     */
    public LinkedList<WelfareOffice> getAllOffices() {
        return new LinkedList<>(offices);
    }
    
    /**
     * READ - Get office by ID
     */
    public WelfareOffice getOfficeById(String officeId) {
        for (WelfareOffice office : offices) {
            if (office.getOfficeId().equals(officeId)) {
                return office;
            }
        }
        return null;
    }
    
    /**
     * UPDATE - Update office information
     */
    public boolean updateOffice(String officeId, WelfareOffice updatedOffice) {
        for (int i = 0; i < offices.size(); i++) {
            if (offices.get(i).getOfficeId().equals(officeId)) {
                offices.set(i, updatedOffice);
                return true;
            }
        }
        return false;
    }
    
    /**
     * DELETE - Remove office
     */
    public boolean deleteOffice(String officeId) {
        return offices.removeIf(office -> office.getOfficeId().equals(officeId));
    }
    
    /**
     * SEARCH - Search offices by city
     */
    public LinkedList<WelfareOffice> searchByCity(String city) {
        LinkedList<WelfareOffice> results = new LinkedList<>();
        for (WelfareOffice office : offices) {
            if (office.getCity().toLowerCase().contains(city.toLowerCase())) {
                results.add(office);
            }
        }
        return results;
    }
    
    /**
     * Get total office count
     */
    public int getTotalCount() {
        return offices.size();
    }
}
