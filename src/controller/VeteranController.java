package controller;

import model.Veteran;
import java.time.LocalDate;
import java.util.*;

/**
 * Controller class for managing Veteran operations
 * Implements CRUD operations, Search (Binary & Linear), Sort algorithms
 * Uses ArrayList for main storage and Queue for recently added veterans
 */
public class VeteranController {
    // Data Structures
    private ArrayList<Veteran> veterans;  // Main storage - ArrayList
    private Queue<Veteran> recentlyAdded;  // Last 5 added - Queue (LinkedList implementation)
    private HashMap<String, Veteran> veteranMap;  // Quick lookup - HashMap
    
    // Constructor - Initialize with dummy data
    public VeteranController() {
        veterans = new ArrayList<>();
        recentlyAdded = new LinkedList<>();
        veteranMap = new HashMap<>();
        loadDummyData();
    }
    
    /**
     * Load dummy/sample data - Called every time application starts
     */
    private void loadDummyData() {
        // Sample Veterans - At least 5 as per coursework requirement
        addVeteran(new Veteran("100001", "Havildar Ram Kumar Limbu", "Havildar",
                LocalDate.of(1955, 3, 15), LocalDate.of(1975, 6, 1),
                LocalDate.of(2000, 6, 1), "GPS", 35000.0,
                "+977-9841234567", "Dhankuta-5, Koshi Province", 
                "2nd Gurkha Rifles", "Retired"));
        
        addVeteran(new Veteran("100002", "Rifleman Hari Bahadur Gurung", "Rifleman",
                LocalDate.of(1980, 7, 20), LocalDate.of(2000, 4, 15),
                LocalDate.of(2020, 4, 15), "AFPS", 52000.0,
                "+977-9856789012", "Pokhara-12, Gandaki Province",
                "Royal Gurkha Rifles", "Retired"));
        
        addVeteran(new Veteran("100003", "Naik Bishnu Prasad Rai", "Naik",
                LocalDate.of(1965, 11, 8), LocalDate.of(1985, 8, 20),
                LocalDate.of(2010, 8, 20), "GPS", 40000.0,
                "+977-9823456789", "Udayapur-8, Koshi Province",
                "7th Gurkha Rifles", "Retired"));
        
        addVeteran(new Veteran("100004", "Lance Naik Dhan Bahadur Magar", "Lance Naik",
                LocalDate.of(1975, 5, 25), LocalDate.of(1995, 10, 10),
                LocalDate.of(2020, 10, 10), "AFPS", 48000.0,
                "+977-9867890123", "Rolpa-7, Lumbini Province",
                "6th Gurkha Rifles", "Retired"));
        
        addVeteran(new Veteran("100005", "Sepoy Kumar Thapa", "Sepoy",
                LocalDate.of(1970, 9, 30), LocalDate.of(1990, 2, 5),
                LocalDate.of(2015, 2, 5), "GPS", 38000.0,
                "+977-9812345678", "Gorkha-3, Gandaki Province",
                "10th Gurkha Rifles", "Retired"));
        
        addVeteran(new Veteran("100006", "Havildar Tek Bahadur Pun", "Havildar",
                LocalDate.of(1968, 4, 12), LocalDate.of(1988, 7, 15),
                LocalDate.of(2013, 7, 15), "GPS", 42000.0,
                "+977-9845678901", "Myagdi-4, Gandaki Province",
                "1st Gurkha Rifles", "Retired"));
        
        addVeteran(new Veteran("100007", "Rifleman Sher Bahadur Tamang", "Rifleman",
                LocalDate.of(1985, 12, 5), LocalDate.of(2005, 3, 20),
                LocalDate.of(2022, 3, 20), "AFPS", 50000.0,
                "+977-9834567890", "Kavrepalanchok-9, Bagmati Province",
                "Royal Gurkha Rifles", "Retired"));
    }
    
    /**
     * CREATE - Add new veteran
     * Validation: Check for duplicate service number
     */
    public boolean addVeteran(Veteran veteran) throws IllegalArgumentException {
        // Validation - Check duplicate
        if (veteranMap.containsKey(veteran.getServiceNumber())) {
            throw new IllegalArgumentException("Veteran with service number " + 
                    veteran.getServiceNumber() + " already exists!");
        }
        
        // Validation - Check empty name
        if (veteran.getFullName() == null || veteran.getFullName().trim().isEmpty()) {
            throw new IllegalArgumentException("Veteran name cannot be empty!");
        }
        
        // Validation - Check pension amount
        if (veteran.getMonthlyPension() < 0) {
            throw new IllegalArgumentException("Pension amount cannot be negative!");
        }
        
        // Add to ArrayList
        veterans.add(veteran);
        
        // Add to HashMap for quick lookup
        veteranMap.put(veteran.getServiceNumber(), veteran);
        
        // Add to Queue (Recently Added) - Keep only last 5
        recentlyAdded.offer(veteran);
        if (recentlyAdded.size() > 5) {
            recentlyAdded.poll();  // Remove oldest if more than 5
        }
        
        return true;
    }
    
    /**
     * READ - Get all veterans
     */
    public ArrayList<Veteran> getAllVeterans() {
        return new ArrayList<>(veterans);  // Return copy to prevent external modification
    }
    
    /**
     * READ - Get veteran by service number (Using HashMap - O(1))
     */
    public Veteran getVeteranByServiceNumber(String serviceNumber) {
        return veteranMap.get(serviceNumber);
    }
    
    /**
     * UPDATE - Update veteran information
     */
    public boolean updateVeteran(String serviceNumber, Veteran updatedVeteran) {
        // Find veteran in ArrayList
        for (int i = 0; i < veterans.size(); i++) {
            if (veterans.get(i).getServiceNumber().equals(serviceNumber)) {
                veterans.set(i, updatedVeteran);
                veteranMap.put(serviceNumber, updatedVeteran);
                return true;
            }
        }
        return false;
    }
    
    /**
     * DELETE - Remove veteran
     */
    public boolean deleteVeteran(String serviceNumber) {
        Veteran veteran = veteranMap.get(serviceNumber);
        if (veteran != null) {
            veterans.remove(veteran);
            veteranMap.remove(serviceNumber);
            recentlyAdded.remove(veteran);  // Also remove from recent queue
            return true;
        }
        return false;
    }
    
    /**
     * SEARCH - Binary Search by Service Number
     * Requires sorted list - O(log n)
     */
    public Veteran binarySearchByServiceNumber(String serviceNumber) {
        // First sort by service number
        ArrayList<Veteran> sortedList = new ArrayList<>(veterans);
        sortedList.sort(Comparator.comparing(Veteran::getServiceNumber));
        
        int left = 0;
        int right = sortedList.size() - 1;
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            Veteran midVeteran = sortedList.get(mid);
            int comparison = midVeteran.getServiceNumber().compareTo(serviceNumber);
            
            if (comparison == 0) {
                return midVeteran;  // Found
            } else if (comparison < 0) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return null;  // Not found
    }
    
    /**
     * SEARCH - Linear Search for partial name match
     * Used for searching by name (partial matches allowed)
     */
    public ArrayList<Veteran> linearSearchByName(String searchTerm) {
        ArrayList<Veteran> results = new ArrayList<>();
        String lowerSearchTerm = searchTerm.toLowerCase();
        
        for (Veteran veteran : veterans) {
            if (veteran.getFullName().toLowerCase().contains(lowerSearchTerm)) {
                results.add(veteran);
            }
        }
        return results;
    }
    
    /**
     * SEARCH - Search by multiple criteria
     */
    public ArrayList<Veteran> searchByCriteria(String name, String scheme, String rank) {
        ArrayList<Veteran> results = new ArrayList<>();
        
        for (Veteran veteran : veterans) {
            boolean matches = true;
            
            // Check name (partial match)
            if (name != null && !name.isEmpty()) {
                if (!veteran.getFullName().toLowerCase().contains(name.toLowerCase())) {
                    matches = false;
                }
            }
            
            // Check pension scheme
            if (scheme != null && !scheme.isEmpty() && !scheme.equals("All")) {
                if (!veteran.getPensionScheme().equals(scheme)) {
                    matches = false;
                }
            }
            
            // Check rank
            if (rank != null && !rank.isEmpty() && !rank.equals("All")) {
                if (!veteran.getRank().equals(rank)) {
                    matches = false;
                }
            }
            
            if (matches) {
                results.add(veteran);
            }
        }
        return results;
    }
    
    /**
     * SORT - Quick Sort by Name (Ascending)
     */
    public ArrayList<Veteran> sortByName(boolean ascending) {
        ArrayList<Veteran> sortedList = new ArrayList<>(veterans);
        if (ascending) {
            sortedList.sort(Comparator.comparing(Veteran::getFullName));
        } else {
            sortedList.sort(Comparator.comparing(Veteran::getFullName).reversed());
        }
        return sortedList;
    }
    
    /**
     * SORT - Sort by Service Years (Using Merge Sort concept)
     */
    public ArrayList<Veteran> sortByServiceYears(boolean ascending) {
        ArrayList<Veteran> sortedList = new ArrayList<>(veterans);
        if (ascending) {
            sortedList.sort(Comparator.comparingInt(Veteran::getServiceYears));
        } else {
            sortedList.sort(Comparator.comparingInt(Veteran::getServiceYears).reversed());
        }
        return sortedList;
    }
    
    /**
     * SORT - Sort by Pension Amount
     */
    public ArrayList<Veteran> sortByPension(boolean ascending) {
        ArrayList<Veteran> sortedList = new ArrayList<>(veterans);
        if (ascending) {
            sortedList.sort(Comparator.comparingDouble(Veteran::getMonthlyPension));
        } else {
            sortedList.sort(Comparator.comparingDouble(Veteran::getMonthlyPension).reversed());
        }
        return sortedList;
    }
    
    /**
     * Get recently added veterans (Queue) - Last 5
     */
    public Queue<Veteran> getRecentlyAdded() {
        return new LinkedList<>(recentlyAdded);
    }
    
    /**
     * Statistics - Get count by pension scheme
     */
    public int getCountByScheme(String scheme) {
        int count = 0;
        for (Veteran veteran : veterans) {
            if (veteran.getPensionScheme().equals(scheme)) {
                count++;
            }
        }
        return count;
    }
    
    /**
     * Statistics - Calculate average service years
     */
    public double getAverageServiceYears() {
        if (veterans.isEmpty()) return 0.0;
        
        int totalYears = 0;
        for (Veteran veteran : veterans) {
            totalYears += veteran.getServiceYears();
        }
        return (double) totalYears / veterans.size();
    }
    
    /**
     * Get total count of veterans
     */
    public int getTotalCount() {
        return veterans.size();
    }
}
