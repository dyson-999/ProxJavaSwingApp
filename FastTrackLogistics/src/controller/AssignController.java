package controller;

import dao.DriverAssignmentDAO;
import dao.DriverDAO;
import dao.ShipmentDAO;
import model.Driver;
import model.Shipment;
import model.ShipmentAssignmentInfo;

import javax.swing.*;
import java.util.List;

public class AssignController {

    private ShipmentDAO shipmentDAO;
    private DriverDAO driverDAO;
    private DriverAssignmentDAO assignmentDAO;

    public AssignController() {
        shipmentDAO = new ShipmentDAO();
        driverDAO = new DriverDAO();
        assignmentDAO = new DriverAssignmentDAO();
    }

    // Load drivers into JComboBox
    public void loadDrivers(JComboBox<Driver> driverComboBox) {
        driverComboBox.removeAllItems();
        List<Driver> drivers = driverDAO.getAllDriversBasic();
        for (Driver d : drivers) {
            driverComboBox.addItem(d);
        }
    }

    // Assign or update the driver assignment for a shipment
    public boolean assignOrUpdateDriverAssignment(Shipment shipment, Driver driver) {
        if (shipment == null || driver == null) {
            JOptionPane.showMessageDialog(null, "Please select both a shipment and a driver.", "Input Error", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        boolean success = assignmentDAO.upsertAssignment(shipment.getShipmentId(), driver.getDriverId());
        if (success) {
            JOptionPane.showMessageDialog(null, "Driver assignment updated successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Failed to update assignment. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        return success;
    }

    // Update the status of the driver assignment (e.g., Assigned, Completed, Canceled)
    public boolean updateAssignmentStatus(int shipmentId, String newStatus) {
        boolean success = assignmentDAO.updateAssignmentStatus(shipmentId, newStatus);
        if (!success) {
            JOptionPane.showMessageDialog(null, "Failed to update assignment status.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        return success;
    }

    // Get list of shipments with their assignment info for displaying in table
    public List<ShipmentAssignmentInfo> getShipmentsWithAssignments() {
        return shipmentDAO.getShipmentsWithAssignments();
    }
}
