package ui;

import controller.AssignController;
import model.Driver;
import model.Shipment;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AssignPanel extends JPanel {
    private JPanel rootPanel;
    private JComboBox<Shipment> shipmentComboBox;
    private JComboBox<Driver> driverComboBox;
    private JButton assignButton;
    private AssignController assignController;

    public AssignPanel() {
        // Initialize controller
        assignController = new AssignController();

        // The form designer will handle the layout creation
        // Note: We don't set the layout here as it's defined in the form

        // Load data into combo boxes
        assignController.loadShipments(shipmentComboBox);
        assignController.loadDrivers(driverComboBox);

        // Set up assign action
        assignButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Shipment selectedShipment = (Shipment) shipmentComboBox.getSelectedItem();
                Driver selectedDriver = (Driver) driverComboBox.getSelectedItem();

                if (selectedShipment != null && selectedDriver != null) {
                    assignController.assignDriverToShipment(selectedShipment, selectedDriver);
                    JOptionPane.showMessageDialog(rootPanel,
                            "Successfully assigned driver to shipment!",
                            "Assignment Complete",
                            JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(rootPanel,
                            "Please select both a shipment and a driver.",
                            "Selection Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Set visual properties for the assign button
        assignButton.setBackground(new Color(46, 139, 87)); // SeaGreen color
        assignButton.setForeground(Color.WHITE);
        assignButton.setFont(new Font("Arial", Font.BOLD, 14));
        assignButton.setFocusPainted(false);
    }

    public JPanel getRootPanel() {
        return rootPanel;
    }
}