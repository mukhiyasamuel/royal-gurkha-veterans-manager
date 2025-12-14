package model;

public class UserAccount {
    private String username;
    private String password;
    private String veteranServiceNumber; // link to Veteran

    public UserAccount(String username, String password, String veteranServiceNumber) {
        this.username = username;
        this.password = password;
        this.veteranServiceNumber = veteranServiceNumber;
    }

    // Getters and Setters
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getVeteranServiceNumber() { return veteranServiceNumber; }
    public void setVeteranServiceNumber(String veteranServiceNumber) {
        this.veteranServiceNumber = veteranServiceNumber;
    }
}
