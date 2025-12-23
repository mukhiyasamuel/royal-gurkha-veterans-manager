package model;

public class Honor {
    private String title;
    private int awardYear;
    private HonorType type;
    private String description;

    // --- Getters and Setters ---
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public int getAwardYear() { return awardYear; }
    public void setAwardYear(int awardYear) { this.awardYear = awardYear; }

    public HonorType getType() { return type; }
    public void setType(HonorType type) { this.type = type; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}

