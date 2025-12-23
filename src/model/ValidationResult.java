/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author princeysunar
 */
// model/ValidationResult.java
import java.util.ArrayList;
import java.util.List;

public class ValidationResult {
    private boolean valid = true;
    private List<String> messages = new ArrayList<>();

    public void addError(String msg) { valid = false; messages.add(msg); }
    public boolean isValid() { return valid; }
    public List<String> getMessages() { return messages; }
}

