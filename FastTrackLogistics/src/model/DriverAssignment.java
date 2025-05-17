package model;

import java.sql.Timestamp;

public class DriverAssignment {
    private int assignmentId;
    private int shipmentId;
    private int driverId;
    private Timestamp assignedAt;
    private String status;

    // Getters and setters
    public int getAssignmentId() { return assignmentId; }
    public void setAssignmentId(int assignmentId) { this.assignmentId = assignmentId; }

    public int getShipmentId() { return shipmentId; }
    public void setShipmentId(int shipmentId) { this.shipmentId = shipmentId; }

    public int getDriverId() { return driverId; }
    public void setDriverId(int driverId) { this.driverId = driverId; }

    public Timestamp getAssignedAt() { return assignedAt; }
    public void setAssignedAt(Timestamp assignedAt) { this.assignedAt = assignedAt; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
