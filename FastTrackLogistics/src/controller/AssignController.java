package controller;

import dao.DriverAssignmentDAO;
import dao.DriverDAO;
import dao.ShipmentDAO;
import model.Driver;
import model.Shipment;

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

    // Load shipments into JComboBox
    public void loadShipments(JComboBox<Shipment> shipmentComboBox) {
        shipmentComboBox.removeAllItems();
        List<Shipment> shipments = shipmentDAO.getAllShipments();
        for (Shipment s : shipments) {
            shipmentComboBox.addItem(s);
        }
    }

    // Load drivers into JComboBox
    public void loadDrivers(JComboBox<Driver> driverComboBox) {
        driverComboBox.removeAllItems();
        List<Driver> drivers = driverDAO.getAllDriversBasic();
        for (Driver d : drivers) {
            driverComboBox.addItem(d);
        }
    }

    // Assign a driver to a shipment
    public boolean assignDriverToShipment(Shipment shipment, Driver driver) {
        if (shipment == null || driver == null) {
            JOptionPane.showMessageDialog(null, "Please select both a shipment and a driver.", "Input Error", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        boolean success = assignmentDAO.assignDriverToShipment(shipment.getShipmentId(), driver.getDriverId());
        if (success) {
            JOptionPane.showMessageDialog(null, "Driver assigned to shipment successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Failed to assign driver. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        return success;
    }

    public List<Shipment> getShipments() {
        ShipmentDAO shipmentDAO = new ShipmentDAO();
        return shipmentDAO.getAllShipments();
    }
}
