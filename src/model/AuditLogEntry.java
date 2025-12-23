/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author princeysunar
 */
// model/AuditLogEntry.java
public class AuditLogEntry {
    public enum Action { CREATE, UPDATE, DELETE }
    private long timestamp;
    private String username;
    private Action action;
    private String entityType;
    private String entityId;
    private String beforeSnapshotJson;
    private String afterSnapshotJson;
    // constructors, getters, setters
}

