// model/ShipmentAssignmentInfo.java
package model;

public class ShipmentAssignmentInfo {
    private int shipmentId;
    private String senderName;
    private String receiverName;
    private String shipmentStatus;
    private String driverName;
    private String assignmentStatus;

    public ShipmentAssignmentInfo(int shipmentId, String senderName, String receiverName,
                                  String shipmentStatus, String driverName, String assignmentStatus) {
        this.shipmentId = shipmentId;
        this.senderName = senderName;
        this.receiverName = receiverName;
        this.shipmentStatus = shipmentStatus;
        this.driverName = driverName;
        this.assignmentStatus = assignmentStatus;
    }

    public int getShipmentId() { return shipmentId; }
    public String getSenderName() { return senderName; }
    public String getReceiverName() { return receiverName; }
    public String getShipmentStatus() { return shipmentStatus; }
    public String getDriverName() { return driverName; }
    public String getAssignmentStatus() { return assignmentStatus; }
}
